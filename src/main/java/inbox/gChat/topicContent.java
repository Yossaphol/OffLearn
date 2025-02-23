package inbox.gChat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class topicContent implements Initializable {

    @FXML
    private Label name;

    @FXML
    private ImageView profile;

    @FXML
    private Label time;

    @FXML
    private Text messages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setMessages(String messages) {
        this.messages.setText(messages);
    }

    public void setName(String name){
        this.name.setText(name);
    }

    public void setTime(String time){
        this.time.setText(time);
    }

}
