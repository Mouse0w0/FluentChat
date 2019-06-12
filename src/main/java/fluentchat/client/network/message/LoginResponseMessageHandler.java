package fluentchat.client.network.message;

import fluentchat.network.Message;
import fluentchat.network.MessageHandler;
import fluentchat.network.message.LoginResponseMessage;
import io.netty.channel.ChannelHandlerContext;

public class LoginResponseMessageHandler implements MessageHandler<LoginResponseMessage> {
    @Override
    public Message handle(ChannelHandlerContext ctx, LoginResponseMessage message) {
        System.out.println(message.isSuccess());
        System.out.println(message.getMessage());
        return null;
    }
}
