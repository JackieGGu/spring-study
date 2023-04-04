package cn.jackiegu.spring.netty.boot.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * websocket消息处理器
 *
 * @author JackieGu
 * @date 2021/9/8
 */
@Slf4j
public class WebSocketChannelInboundHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private final WebSocketServer webSocketServer;

    public WebSocketChannelInboundHandler(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    /**
     * 用户事件触发, 在TCP连接和握手完成时将触发
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("用户事件触发, 事件: {}", evt);
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            webSocketServer.getContexts().add(ctx);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 通道读取数据触发
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) throws Exception {
        String channelId = channelHandlerContext.channel().id().asLongText();
        log.info("channel id: {}", channelId);
        String message = webSocketFrame.content().toString(StandardCharsets.UTF_8);
        log.info("message: {}", message);
        List<ChannelHandlerContext> contexts = webSocketServer.getContexts();
        Random random = new SecureRandom();
        ChannelHandlerContext sendClient;
        do {
            int i = random.nextInt(contexts.size());
            sendClient = contexts.get(i);
        } while (sendClient == null || channelId.equals(sendClient.channel().id().asLongText()));
        sendClient.writeAndFlush(new TextWebSocketFrame(message)).sync();
    }

    /**
     * 通道断开触发
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        boolean result = webSocketServer.getContexts().remove(ctx);
        log.info("remove channel handler context: {}", result);
    }
}
