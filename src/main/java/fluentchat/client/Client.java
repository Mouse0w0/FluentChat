package fluentchat.client;

import fluentchat.client.network.ClientNetwork;
import fluentchat.client.ui.UIBootstrap;
import javafx.application.Application;

public class Client {

    private static ClientNetwork network;

    public static void main(String[] args) {
        network = new ClientNetwork();
        network.connect("127.0.0.1", 1080);
        Application.launch(UIBootstrap.class, args);
    }

    public static ClientNetwork getNetwork() {
        return network;
    }

    public static void exit() {
        network.disconnect();
        UIBootstrap.getMainStage().close();
    }
}
