package client.inbox.gChat;

import client.inbox.DataBase.topicDB;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import client.swing.postTopic;
import client.FontLoader.FontLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class gChatController implements Initializable {

    @FXML
    private Button createTopic;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView refresh;

    @FXML
    private VBox main;

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        postTopic postGui = new postTopic();
        refreshContent();

        createTopic.setOnAction(actionEvent -> postGui.openSwingWindow());

        refresh.setOnMouseClicked(mouseEvent -> refreshContent());
    }

    public void refreshContent() {
        new Thread(() -> {
            topicDB topicDB = new topicDB();
            VBox newContent = topicDB.showTopicFromDB();

            Platform.runLater(() -> scrollPane.setContent(newContent));
        }).start();
    }

    public Pane getPane(){
        return this.pane;
    }

    public VBox getMain(){
        return this.main;
    }
}




