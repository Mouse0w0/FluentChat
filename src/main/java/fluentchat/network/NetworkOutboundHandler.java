package fluentchat.network;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class NetworkOutboundHandler extends ChannelOutboundHandlerAdapter {

    private final NetworkManager network;

    public NetworkOutboundHandler(NetworkManager network) {
        this.network = network;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Message message = (Message) msg;
        var registeredMessage = network.getRegisteredMessage(message.getClass());
        var buf = Unpooled.buffer();
        buf.writeInt(registeredMessage.getId());
        registeredMessage.write(buf, message);
        ctx.write(buf, promise);
    }
}
