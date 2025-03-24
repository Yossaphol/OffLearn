package Student.inbox.gChat;

import Database.*;
import a_Session.SessionHadler;
import a_Session.SessionManager;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Student.swing.postTopic;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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

    @FXML
    private Circle profile;

    private String name;
    private UserDB userDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDB = new UserDB();
        handleSession();

        setProfile(userDB.getProfile(name));

        postTopic postGui = new postTopic();
        refreshContent();

        createTopic.setOnAction(actionEvent -> postGui.openSwingWindow());

        refresh.setOnMouseClicked(mouseEvent -> refreshContent());

        setEffect();
    }

    @Override
    public void handleSession() {
        this.name = SessionManager.getInstance().getUsername();
        Name.setText(name);
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

    public void setProfile(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        this.profile.setStroke(Color.TRANSPARENT);
        this.profile.setFill(new ImagePattern(img));
    }


    public void setEffect(){
        hoverEffect(createTopic);
    }

    public void hoverEffect(Button btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.05);
        scaleDown.setFromY(1.05);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
        });
    }

}