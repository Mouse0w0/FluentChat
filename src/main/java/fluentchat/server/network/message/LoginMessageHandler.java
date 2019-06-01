package fluentchat.server.network.message;

import fluentchat.network.Message;
import fluentchat.network.MessageHandler;
import fluentchat.network.message.LoginMessage;
import io.netty.channel.ChannelHandlerContext;

public class LoginMessageHandler implements MessageHandler<LoginMessage> {
    @Override
    public Message handle(ChannelHandlerContext ctx, LoginMessage message) {
        System.out.println(message.getUsername() + "," + message.getPassword());
        return null;
    }
}
