package fluentchat.client.ui;

import fluentchat.client.Client;
import fluentchat.network.message.LoginMessage;
import fluentchat.util.PasswordUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginUI extends AnchorPane {



    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label message;

    public LoginUI() throws IOException {
        UIHelper.load(this, "login");
    }

    @FXML
    public void login() throws Exception {
        UIBootstrap.getMainStage().setScene(new Scene(new WaitUI()));
        Client.getExecutor().execute(() -> {
            try {
                Client.getConnection().connect("127.0.0.1", 8080).sync();
            } catch (InterruptedException ignored) {
            }
            Client.getConnection().send(new LoginMessage(username.getText(), PasswordUtils.encode(password.getText())));
        });
    }

    @FXML
    public void register() {

    }
}
