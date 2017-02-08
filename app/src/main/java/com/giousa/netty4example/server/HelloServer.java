package com.giousa.netty4example.server;

import com.giousa.netty4example.initializer.HelloServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class HelloServer {

    /**
     * 服务端监听的端口地址
     */
    private static final int portNumber = 7878;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new HelloServerInitializer());

//            b.childHandler(new ChannelInitializer<Channel>() {
//                @Override
//                protected void initChannel(Channel ch) throws Exception {
//
//                    ch.pipeline().addLast(new StringDecoder());
//                    ch.pipeline().addLast(new StringEncoder());
//                    ch.pipeline().addLast(new ServerHandler());
//                }
//            });

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();

            System.out.println("Server Start");
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            System.out.println("------");

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
