package fluentchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class RegisteredMessage<T extends Message> {
    private final int id;
    private final Class<T> clazz;
    private final MessageSerializer<T> serializer;
    private final MessageHandler<T> handler;

    public RegisteredMessage(int id, Class<T> clazz, MessageSerializer<T> serializer, MessageHandler<T> handler) {
        this.id = id;
        this.clazz = clazz;
        this.serializer = serializer;
        this.handler = handler;
    }

    public int getId() {
        return id;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public Message handle(ChannelHandlerContext ctx, Message message) {
        if (handler == null)
            throw new UnsupportedOperationException("Cannot handle this message");
        return handler.handle(ctx, (T) message);
    }

    public T read(ByteBuf buf) {
        return serializer.read(buf);
    }

    public void write(ByteBuf buf, Message message) {
        serializer.write(buf, (T) message);
    }
}
