package fluentchat.client.ui;

import fluentchat.client.Client;
import fluentchat.network.message.LoginMessage;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginUI extends AnchorPane {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    public LoginUI() throws IOException {
        UIHelper.load(this, "login");
    }

    @FXML
    public void login() {
        try {
            Client.getConnection().connect("127.0.0.1", 8080).sync();
        } catch (InterruptedException ignored) {
        }
        Client.getConnection().getChannel().writeAndFlush(new LoginMessage(username.getText(), password.getText()));
    }

    @FXML
    public void register() {

    }

    @FXML
    public void exit() {
        Client.exit();
    }
}
