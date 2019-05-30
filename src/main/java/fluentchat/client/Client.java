package fluentchat.client;

import fluentchat.client.network.ClientConnection;
import fluentchat.client.ui.UIBootstrap;
import fluentchat.network.NetworkManager;
import javafx.application.Application;

public class Client {

    private static NetworkManager network;
    private static ClientConnection connection;

    public static void main(String[] args) {
        network = new NetworkManager();
        connection = new ClientConnection(network);
        Application.launch(UIBootstrap.class, args);
    }

    public static NetworkManager getNetwork() {
        return network;
    }

    public static ClientConnection getConnection() {
        return connection;
    }

    public static void exit() {
        connection.disconnect();
        UIBootstrap.getMainStage().close();
    }
}
