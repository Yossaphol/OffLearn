package inbox.gChat;

import inbox.DataBase.topicDB;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import swing.postTopic;

import java.net.URL;
import java.util.ResourceBundle;

public class gChatController implements Initializable {

    @FXML
    private Button createTopic;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView refresh;

    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        postTopic postGui = new postTopic();
        refreshContent();

        createTopic.setOnAction(actionEvent -> postGui.openSwingWindow());

        refresh.setOnMouseClicked(mouseEvent -> refreshContent());


    }

    private void refreshContent() {
        new Thread(() -> {
            topicDB topicDB = new topicDB();
            VBox newContent = topicDB.showTopicFromDB();

            Platform.runLater(() -> scrollPane.setContent(newContent));
        }).start();
    }
}




