package fluentchat.network.message;

import fluentchat.network.Message;
import fluentchat.network.MessageSerializer;
import io.netty.buffer.ByteBuf;

import static fluentchat.network.ByteBufUtils.readString;
import static fluentchat.network.ByteBufUtils.writeString;

public class LoginMessage implements Message {

    private String username;
    private String password;

    private LoginMessage() {
    }

    public LoginMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class Serializer implements MessageSerializer<LoginMessage> {

        @Override
        public LoginMessage read(ByteBuf buf) {
            LoginMessage message = new LoginMessage();
            message.username = readString(buf);
            message.password = readString(buf);
            return message;
        }

        @Override
        public void write(ByteBuf buf, LoginMessage message) {
            writeString(buf, message.username);
            writeString(buf, message.password);
        }
    }
}
