package fluentchat.network;

import io.netty.channel.ChannelHandlerContext;

public interface MessageHandler<T extends Message> {

    Message handle(ChannelHandlerContext ctx, T message);
}
