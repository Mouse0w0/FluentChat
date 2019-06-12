package fluentchat.client;

import fluentchat.client.network.ClientConnection;
import fluentchat.client.network.message.LoginResponseMessageHandler;
import fluentchat.client.ui.UIBootstrap;
import fluentchat.network.NetworkManager;
import fluentchat.network.message.LoginMessage;
import fluentchat.network.message.LoginResponseMessage;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Client {

    public final static Logger LOGGER = LoggerFactory.getLogger("Client");

    private static NetworkManager network;
    private static ClientConnection connection;
    private static ScheduledExecutorService executor;

    public static void main(String[] args) {
        LOGGER.info("Launching client!");

        executor = Executors.newSingleThreadScheduledExecutor();

        network = new NetworkManager();
        network.register(LoginMessage.class, new LoginMessage.Serializer());
        network.register(LoginResponseMessage.class, new LoginResponseMessage.Serializer(), new LoginResponseMessageHandler());

        connection = new ClientConnection(network);
        Application.launch(UIBootstrap.class, args);
    }

    public static NetworkManager getNetwork() {
        return network;
    }

    public static ClientConnection getConnection() {
        return connection;
    }

    public static ScheduledExecutorService getExecutor() {
        return executor;
    }

    public static void exit() {
        executor.shutdown();
        connection.disconnect();
        UIBootstrap.getMainStage().close();
    }
}
