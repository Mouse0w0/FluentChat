package fluentchat.client.ui;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UIHelper {

    public static void load(Object root, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(root);
        loader.setRoot(root);
        loader.setCharset(StandardCharsets.UTF_8);
        loader.load(root.getClass().getResourceAsStream("/ui/" + name + ".fxml"));
    }
}
