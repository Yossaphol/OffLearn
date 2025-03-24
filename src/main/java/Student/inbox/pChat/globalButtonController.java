package Student.inbox.pChat;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class globalButtonController extends pChatController implements Initializable{

    @FXML
    private HBox privateButton;

    private Runnable privateButtonClickListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pChatController button = new pChatController();

        hoverEffect(privateButton);

        privateButton.setOnMouseClicked(mouseEvent -> {
            if (privateButtonClickListener != null) {
                privateButtonClickListener.run();
            }
        });
    }

    public void setPrivateButtonClickListener(Runnable listener) {
        this.privateButtonClickListener = listener;
    }



    public void hoverEffect(HBox btn) {
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
