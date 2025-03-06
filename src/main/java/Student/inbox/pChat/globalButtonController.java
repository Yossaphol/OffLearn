package Student.inbox.pChat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class globalButtonController extends pChatController implements Initializable{

    @FXML
    private HBox privateButton;
    private Runnable privateButtonClickListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pChatController button = new pChatController();

        privateButton.setOnMouseEntered(mouseEvent -> privateButton.setOpacity(0.5));
        privateButton.setOnMouseExited(mouseEvent -> privateButton.setOpacity(1));

        privateButton.setOnMouseClicked(mouseEvent -> {
            if (privateButtonClickListener != null) {
                privateButtonClickListener.run();
            }
        });
    }

    public void setPrivateButtonClickListener(Runnable listener) {
        this.privateButtonClickListener = listener;
    }

}
