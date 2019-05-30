package fluentchat.client.ui;

import fluentchat.client.Client;
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

    }

    @FXML
    public void register() {

    }

    @FXML
    public void exit() {
        Client.exit();
    }
}
