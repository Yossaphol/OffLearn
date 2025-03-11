package Student.inbox.gChat;

import Database.*;
import a_Session.SessionHadler;
import a_Session.SessionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Student.swing.postTopic;

import java.net.URL;
import java.util.ResourceBundle;

public class gChatController implements Initializable, SessionHadler {

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

    @FXML
    private Label Name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleSession();

        postTopic postGui = new postTopic();
        refreshContent();

        createTopic.setOnAction(actionEvent -> postGui.openSwingWindow());

        refresh.setOnMouseClicked(mouseEvent -> refreshContent());
    }

    @Override
    public void handleSession() {
        Name.setText(SessionManager.getInstance().getUsername());
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




