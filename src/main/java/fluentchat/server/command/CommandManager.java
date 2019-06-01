package fluentchat.server.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, CommandExecutor> commands = new HashMap<>();

    public void register(String command, CommandExecutor executor) {
        commands.put(command, executor);
    }

    public void executeCommand(String command) {
        CommandParser.Result result = CommandParser.parse(command);
        CommandExecutor executor = commands.get(result.label);
        if (executor == null) {
            return;
        }

        executor.onCommand(result.args);
    }
}
