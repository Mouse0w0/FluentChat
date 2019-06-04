package fluentchat.client;

import fluentchat.client.network.ClientConnection;
import fluentchat.client.ui.UIBootstrap;
import fluentchat.network.NetworkManager;
import fluentchat.network.message.LoginMessage;
import javafx.application.Application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Client {

    private static NetworkManager network;
    private static ClientConnection connection;
    private static ScheduledExecutorService executor;

    public static void main(String[] args) {
        executor = Executors.newSingleThreadScheduledExecutor();
        network = new NetworkManager();
        network.register(LoginMessage.class, new LoginMessage.Serializer());
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
