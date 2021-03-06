package fluentchat.client.ui;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UIHelper {

    public static void load(Object root, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(root);
        loader.setRoot(root);
        loader.setCharset(StandardCharsets.UTF_8);
        loader.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("ui/" + name + ".fxml"));
    }

    public static void load(Object root, URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(root);
        loader.setRoot(root);
        loader.setCharset(StandardCharsets.UTF_8);
        loader.load();
    }
}
