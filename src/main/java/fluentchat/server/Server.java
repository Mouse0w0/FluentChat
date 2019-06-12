package fluentchat.server;

import fluentchat.network.NetworkManager;
import fluentchat.network.message.LoginMessage;
import fluentchat.network.message.LoginResponseMessage;
import fluentchat.server.command.CommandManager;
import fluentchat.server.command.buildin.UserCommand;
import fluentchat.server.database.Database;
import fluentchat.server.network.ServerConnection;
import fluentchat.server.network.message.LoginMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {

    public final static Logger LOGGER = LoggerFactory.getLogger("Server");

    private static CommandManager commandManager;
    private static NetworkManager network;
    private static ServerConnection connection;
    private static ScheduledExecutorService executor;
    private static Database database;

    private volatile static boolean stopped = false;

    public static void main(String[] args) throws Exception {
        LOGGER.info("Launching server!");
        long startTime = System.currentTimeMillis();

        executor = Executors.newSingleThreadScheduledExecutor();

        commandManager = new CommandManager();
        commandManager.register("stop", $ -> {
            System.out.println("Stopping...");
            exit();
            System.out.println("Stopped!");
        });
        commandManager.register("user", new UserCommand());

        network = new NetworkManager();
        network.register(LoginMessage.class, new LoginMessage.Serializer(), new LoginMessageHandler());
        network.register(LoginResponseMessage.class, new LoginResponseMessage.Serializer());

        database = new Database();
        database.init();

        connection = new ServerConnection(network);
        connection.listen(8080);

        LOGGER.info("Server ready! (Loading time: {}ms)", System.currentTimeMillis() - startTime);

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

    public static ScheduledExecutorService getExecutor() {
        return executor;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static Database getDatabase() {
        return database;
    }

    public static void exit() {
        stopped = true;
        executor.shutdown();
        while (true) {
            try {
                if (executor.awaitTermination(1, TimeUnit.SECONDS))
                    break;
            } catch (InterruptedException e) {
                break;
            }
        }

        connection.close();
        database.close();
    }
}
