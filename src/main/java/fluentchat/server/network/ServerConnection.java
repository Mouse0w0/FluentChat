package fluentchat.server.network;

import fluentchat.network.NetworkInboundHandler;
import fluentchat.network.NetworkManager;
import fluentchat.network.NetworkOutboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ServerConnection {

    private final NetworkManager networkManager;

    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    private ChannelFuture channelFuture;

    public ServerConnection(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public ChannelFuture listen(int port) {
        parentGroup = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ServerChannelInitializer());
        channelFuture = bootstrap.bind(port);
        return channelFuture;
    }

    public void close() throws InterruptedException {
        try {
            channelFuture.channel().close().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    private class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
            ch.pipeline().addLast(new LengthFieldPrepender(4));
            ch.pipeline().addLast(new ReadTimeoutHandler(50));
            ch.pipeline().addLast(new NetworkInboundHandler(networkManager));
            ch.pipeline().addLast(new NetworkOutboundHandler(networkManager));
        }
    }
}
