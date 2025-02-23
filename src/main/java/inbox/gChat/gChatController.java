package inbox.gChat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import swing.postTopic;

import java.net.URL;
import java.util.ResourceBundle;

public class gChatController implements Initializable {

    @FXML
    private Button createTopic;
    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void CreateTopic(){
        if (primaryStage != null) {
            postTopic postGui = new postTopic();
            createTopic.setOnAction(actionEvent -> postGui.openSwingWindow(this.primaryStage));
        } else {
            System.out.println("Error: primaryStage is null");
        }
    }

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
        CreateTopic();

    }
}
