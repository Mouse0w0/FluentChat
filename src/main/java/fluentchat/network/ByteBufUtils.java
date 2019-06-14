package fluentchat.network;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class ByteBufUtils {

    public static String readString(ByteBuf buf) {
        byte[] bytes = new byte[readVarInt(buf)];
        buf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void writeString(ByteBuf buf, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeVarInt(buf, bytes.length);
        buf.writeBytes(bytes);
    }

    public static int readVarInt(ByteBuf buf) {
        int result = 0;
        for (int i = 0; i < 5; i++) {
            byte b = buf.readByte();
            result |= (b & 0b0111_1111) << i * 7;
            if ((b & 0b1000_0000) != 0b1000_0000) {
                return result;
            }
        }
        throw new IllegalStateException("Variable integer too big");
    }

    public static void writeVarInt(ByteBuf buf, int value) {
        int i = value & 0b0111_1111;
        value >>>= 7;
        while (value != 0) {
            buf.writeByte(i | 0b1000_0000);
            i = value & 0b0111_1111;
            value >>>= 7;
        }
        buf.writeByte(i);
    }
}
