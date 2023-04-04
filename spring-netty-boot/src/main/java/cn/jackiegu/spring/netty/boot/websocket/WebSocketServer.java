package cn.jackiegu.spring.netty.boot.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * websocket服务类
 *
 * @author JackieGu
 * @date 2021/9/8
 */
@Slf4j
public class WebSocketServer {

    private final WebSocketProperties webSocketProperties;

    private Channel channel;

    /**
     * 主线程池
     */
    private final EventLoopGroup boss = new NioEventLoopGroup(new DefaultThreadFactory("netty-websocket-boss", true));

    /**
     * 从线程池
     */
    private final EventLoopGroup worker = new NioEventLoopGroup(new DefaultThreadFactory("netty-websocket-worker", true));

    /**
     * 通道处理器上下文缓存
     */
    private final List<ChannelHandlerContext> contexts = new ArrayList<>();

    public WebSocketServer(WebSocketProperties webSocketProperties) {
        this.webSocketProperties = webSocketProperties;
    }

    public void init() throws Exception {
        // netty服务引导实例
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 指定线程池
        bootstrap.group(boss, worker);
        // 指定通道类型
        bootstrap.channel(NioServerSocketChannel.class);
        // 指定主线程池处理器
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        // 指定从线程池处理器
        bootstrap.childHandler(new WebSocketChannelInitializer(webSocketProperties, this));
        // 绑定主机与端口, 以同步方式启动
        channel = bootstrap.bind(webSocketProperties.getHost(), webSocketProperties.getPort()).sync().channel();
        log.info("websocket start at {}:{}", webSocketProperties.getHost(), webSocketProperties.getPort());

        worker.scheduleWithFixedDelay(() -> {
            log.info("worker fixed delay task running");
        }, 20, 20, TimeUnit.SECONDS);
    }

    public void destroy() {
        if (channel != null) {
            // 关闭通道
            channel.close();
        }
        // 销毁主线程池
        boss.shutdownGracefully();
        // 销毁从线程池
        worker.shutdownGracefully();
    }

    public List<ChannelHandlerContext> getContexts() {
        return contexts;
    }
}
