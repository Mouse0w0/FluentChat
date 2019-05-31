package fluentchat.server;

import fluentchat.network.NetworkManager;
import fluentchat.network.message.LoginMessage;
import fluentchat.server.network.ServerConnection;
import fluentchat.server.network.message.LoginMessageHandler;

public class Server {

    private static NetworkManager network;
    private static ServerConnection connection;

    public static void main(String[] args) throws Exception {
        network = new NetworkManager();
        network.register(LoginMessage.class, new LoginMessage.Serializer(), new LoginMessageHandler());
        connection = new ServerConnection(network);
        connection.listen(8080).sync();
    }

    public static NetworkManager getNetwork() {
        return network;
    }

    public static ServerConnection getConnection() {
        return connection;
    }
}
