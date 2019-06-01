package fluentchat.server;

import fluentchat.network.NetworkManager;
import fluentchat.network.message.LoginMessage;
import fluentchat.server.command.CommandManager;
import fluentchat.server.network.ServerConnection;
import fluentchat.server.network.message.LoginMessageHandler;

import java.util.Scanner;

public class Server {

    private static CommandManager commandManager;
    private static NetworkManager network;
    private static ServerConnection connection;

    private volatile static boolean stopped = false;

    public static void main(String[] args) throws Exception {
        commandManager = new CommandManager();
        commandManager.register("stop", $ -> {
            System.out.println("Stopping...");
            exit();
            System.out.println("Stopped!");
        });

        network = new NetworkManager();
        network.register(LoginMessage.class, new LoginMessage.Serializer(), new LoginMessageHandler());

        connection = new ServerConnection(network);
        connection.listen(8080);
        Scanner scanner = new Scanner(System.in);
        while (!stopped) {
            commandManager.executeCommand(scanner.nextLine());
        }
    }

    public static NetworkManager getNetwork() {
        return network;
    }

    public static ServerConnection getConnection() {
        return connection;
    }

    public static void exit() {
        stopped = true;
        connection.close();
    }
}
