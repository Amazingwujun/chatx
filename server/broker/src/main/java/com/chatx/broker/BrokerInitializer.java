package com.chatx.broker;

import com.chatx.broker.config.BizProperties;
import com.chatx.commons.exception.ChatxException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 基于 netty 的 tcp server
 *
 * @author Jun
 * @since 1.0.0
 */
@Component
public class BrokerInitializer implements DisposableBean {

    private final EventLoopGroup boss, work;
    private final int port, soBacklog;

    public BrokerInitializer(BizProperties bizProperties) {
        this.port = bizProperties.getPort();
        this.soBacklog = bizProperties.getSoBacklog();

        if (Epoll.isAvailable()) {
            boss = new EpollEventLoopGroup(1);
            work = new EpollEventLoopGroup();
        } else {
            boss = new NioEventLoopGroup(1);
            work = new NioEventLoopGroup();
        }
    }

    @PostConstruct
    public void launch() throws InterruptedException {
        var b = new ServerBootstrap();

        if (Epoll.isAvailable()) {
            b.channel(EpollServerSocketChannel.class);
        } else {
            b.channel(NioServerSocketChannel.class);
        }

        b
                .group(boss, work)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_BACKLOG, soBacklog)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        var p = socketChannel.pipeline();
                    }
                });
        b.bind(port).sync();
    }

    @Override
    public void destroy() {
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (work != null) {
            work.shutdownGracefully();
        }
    }
}
