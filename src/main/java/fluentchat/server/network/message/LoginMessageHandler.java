package fluentchat.server.network.message;

import fluentchat.network.Message;
import fluentchat.network.MessageHandler;
import fluentchat.network.message.LoginMessage;
import fluentchat.network.message.LoginResponseMessage;
import fluentchat.server.Server;
import fluentchat.server.database.UserAuthentication;
import io.netty.channel.ChannelHandlerContext;

import java.sql.SQLException;

import static fluentchat.util.PasswordUtils.encode;

public class LoginMessageHandler implements MessageHandler<LoginMessage> {
    @Override
    public Message handle(ChannelHandlerContext ctx, LoginMessage message) {
        try {
            Server.LOGGER.info("User {} (address: {}) try to login.", message.getUsername(), ctx.channel().remoteAddress());
            UserAuthentication userAuth = Server.getDatabase().getUserAuthDao().queryForId(message.getUsername());
            if (userAuth == null || !userAuth.password.equals(encode(message.getPassword()))) {
                return new LoginResponseMessage(false, "Invalid username or password");
            }
        } catch (SQLException e) {
            return new LoginResponseMessage(false, "Server error");
        }
        return new LoginResponseMessage(true, "Success");
    }
}
