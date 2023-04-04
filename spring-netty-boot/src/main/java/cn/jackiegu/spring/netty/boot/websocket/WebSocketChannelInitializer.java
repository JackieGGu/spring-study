package cn.jackiegu.spring.netty.boot.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * websocket通道初始化器
 *
 * @author JackieGu
 * @date 2021/9/8
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final WebSocketProperties webSocketProperties;

    private final WebSocketServer webSocketServer;

    public WebSocketChannelInitializer(WebSocketProperties webSocketProperties, WebSocketServer webSocketServer) {
        this.webSocketProperties = webSocketProperties;
        this.webSocketServer = webSocketServer;
    }

    /**
     * 初始化通道
     *
     * @param socketChannel 字节通道
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // 对通道添加HTTP编解码器
        socketChannel.pipeline().addLast(new HttpServerCodec());
        // 对通道添加聚合器, 将HTTP请求中的request line、header、message body聚合到一起
        socketChannel.pipeline().addLast(new HttpObjectAggregator(1024 * 32));
        // 对通道指定context-path
        WebSocketServerProtocolHandler webSocketServerProtocolHandler =
            new WebSocketServerProtocolHandler(webSocketProperties.getPath());
        socketChannel.pipeline().addLast(webSocketServerProtocolHandler);
        // 对通道添加自定义处理器
        socketChannel.pipeline().addLast(new WebSocketChannelInboundHandler(webSocketServer));
    }
}
