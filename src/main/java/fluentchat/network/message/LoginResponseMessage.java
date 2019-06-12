package fluentchat.network.message;

import fluentchat.network.ByteBufUtils;
import fluentchat.network.Message;
import fluentchat.network.MessageSerializer;
import io.netty.buffer.ByteBuf;

public class LoginResponseMessage implements Message {

    private boolean success;
    private String message;

    private LoginResponseMessage() {

    }

    public LoginResponseMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public static class Serializer implements MessageSerializer<LoginResponseMessage> {

        @Override
        public LoginResponseMessage read(ByteBuf buf) {
            LoginResponseMessage message = new LoginResponseMessage();
            message.success = buf.readBoolean();
            message.message = ByteBufUtils.readString(buf);
            return message;
        }

        @Override
        public void write(ByteBuf buf, LoginResponseMessage message) {
            buf.writeBoolean(message.success);
            ByteBufUtils.writeString(buf, message.message);
        }
    }
}
