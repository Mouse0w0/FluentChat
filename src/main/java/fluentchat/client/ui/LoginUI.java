package fluentchat.client.ui;

import fluentchat.client.Client;
import fluentchat.network.message.LoginMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Base64;

import static org.apache.commons.codec.digest.DigestUtils.sha1;

public class LoginUI extends AnchorPane {

    private static final String OBSCURED_CODE = "FluentChat";

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
    public void login() {
        try {
            Client.getConnection().connect("127.0.0.1", 8080).sync();
        } catch (InterruptedException ignored) {
        }
        Client.getConnection().send(new LoginMessage(username.getText(),
                Base64.getEncoder().encodeToString(sha1(password.getText() + OBSCURED_CODE))));
    }

    @FXML
    public void register() {

    }
}
