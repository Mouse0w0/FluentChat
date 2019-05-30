package fluentchat.server;

import fluentchat.server.network.ServerNetwork;

public class Server {

    private static ServerNetwork network;

    public static void main(String[] args) throws Exception {
        network = new ServerNetwork();
        network.listen(8080);
    }
}
