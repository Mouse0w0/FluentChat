package fluentchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NetworkOutboundHandler extends MessageToByteEncoder<Message> {

    private final NetworkManager network;

    public NetworkOutboundHandler(NetworkManager network) {
        this.network = network;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        var registeredMessage = network.getRegisteredMessage(msg.getClass());
        out.writeInt(registeredMessage.getId());
        registeredMessage.write(out, msg);
    }
}
