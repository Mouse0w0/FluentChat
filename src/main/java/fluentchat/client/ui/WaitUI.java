package fluentchat.client.ui;

import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WaitUI extends AnchorPane {

    public WaitUI() throws IOException {
        UIHelper.load(this, "wait");
    }
}
