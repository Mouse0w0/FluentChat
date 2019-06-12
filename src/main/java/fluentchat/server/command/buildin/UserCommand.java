package fluentchat.server.command.buildin;

import fluentchat.server.Server;
import fluentchat.server.command.CommandExecutor;
import fluentchat.server.database.UserAuthentication;

import java.sql.SQLException;

import static fluentchat.util.PasswordUtils.encode;
import static java.lang.String.format;

public class UserCommand implements CommandExecutor {
    @Override
    public void onCommand(String[] args) {
        switch (args[0]) {
            case "create": {
                var userAuth = new UserAuthentication();
                userAuth.username = args[1];
                userAuth.password = encode(encode(args[2]));
                try {
                    Server.getDatabase().getUserAuthDao().create(userAuth);
                } catch (SQLException e) {
                    Server.LOGGER.warn(format("Cannot create user %s.", args[1]), e);
                }
                Server.LOGGER.info("Create user {} successfully.", args[1]);
            }
            break;
        }
    }
}
