package fluentchat.client.network;

import fluentchat.network.Message;
import fluentchat.network.NetworkInboundHandler;
import fluentchat.network.NetworkManager;
import fluentchat.network.NetworkOutboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import javax.net.ssl.SSLContext;

public class ClientConnection {

    private final NetworkManager networkManager;

    private EventLoopGroup eventLoopGroup;
    private ChannelFuture channelFuture;
    private Channel channel;

    public ClientConnection(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public Channel getChannel() {
        return channel;
    }

    public ChannelFuture connect(String address, int port) {
        if (channel != null) {
            disconnect();
        }

        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ClientChannelInitializer());
        channelFuture = bootstrap.connect(address, port);
        channel = channelFuture.channel();
        return channelFuture;
    }

    public void disconnect() {
        if (channel == null)
            return;

        try {
            channel.close().sync();
        } catch (InterruptedException ignored) {
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public void send(Message message) {
        getChannel().writeAndFlush(message);
    }

    private class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            var sslEngine = SSLContext.getDefault().createSSLEngine();
            sslEngine.setUseClientMode(true);
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
