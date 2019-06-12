package fluentchat.server.network;

import fluentchat.network.NetworkInboundHandler;
import fluentchat.network.NetworkManager;
import fluentchat.network.NetworkOutboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import javax.net.ssl.SSLContext;

public class ServerConnection {

    private final NetworkManager networkManager;

    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    private Channel channel;

    public ServerConnection(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public Channel getChannel() {
        return channel;
    }

    public ChannelFuture listen(int port) {
        parentGroup = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();
        var bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ServerChannelInitializer());
        var channelFuture = bootstrap.bind(port);
        channel = channelFuture.channel();
        return channelFuture;
    }

    public void close() {
        if (channel == null)
            return;
        try {
            channel.close().sync();
        } catch (InterruptedException ignored) {
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    private class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            var sslEngine = SSLContext.getDefault().createSSLEngine();
            sslEngine.setUseClientMode(false);
            sslEngine.setNeedClientAuth(true);
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
            ch.pipeline().addLast(new LengthFieldPrepender(4));
            // TODO: SSL
//            ch.pipeline().addLast("SSL", new SslHandler(sslEngine));
//            ch.pipeline().addLast(new ReadTimeoutHandler(50));
            ch.pipeline().addLast(new NetworkOutboundHandler(networkManager));
            ch.pipeline().addLast(new NetworkInboundHandler(networkManager));
        }
    }
}
