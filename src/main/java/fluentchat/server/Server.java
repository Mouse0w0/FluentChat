package fluentchat.server;

import fluentchat.network.NetworkManager;
import fluentchat.server.network.ServerConnection;

public class Server {

    private static NetworkManager network;
    private static ServerConnection connection;

    public static void main(String[] args) throws Exception {
        network = new NetworkManager();
        connection = new ServerConnection(network);
        connection.listen(8080);
    }

    public static NetworkManager getNetwork() {
        return network;
    }

    public static ServerConnection getConnection() {
        return connection;
    }
}
