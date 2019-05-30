package fluentchat.network;

import io.netty.buffer.ByteBuf;

public interface MessageSerializer<T extends Message> {

    T read(ByteBuf buf);

    void write(ByteBuf buf, T message);
}
