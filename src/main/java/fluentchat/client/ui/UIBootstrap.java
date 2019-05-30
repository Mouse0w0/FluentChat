package fluentchat.client.ui;

import fluentchat.client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIBootstrap extends Application {

    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;

        LoginUI loginUI = new LoginUI();
        primaryStage.setScene(new Scene(loginUI));
        primaryStage.setTitle("Fluent Chat");
        primaryStage.setOnCloseRequest(event -> Client.exit());
        primaryStage.show();
    }
}
