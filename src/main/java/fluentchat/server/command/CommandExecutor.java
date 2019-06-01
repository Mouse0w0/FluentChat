package fluentchat.server.command;

public interface CommandExecutor {

    void onCommand(String[] args);
}
