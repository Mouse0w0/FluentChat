package fluentchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class NetworkInboundHandler extends ChannelInboundHandlerAdapter {

    private final NetworkManager network;

    public NetworkInboundHandler(NetworkManager network) {
        this.network = network;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;
            var messageId = buf.readInt();
            var registeredMessage = network.getRegisteredMessage(messageId);
            var message = registeredMessage.read(buf);
            Message response = registeredMessage.handle(ctx, message);
            if (response != null) {
                ctx.writeAndFlush(message);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
