package Teacher.navigator;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class navbarController implements Initializable {
    public VBox navPopup;
    public Button nav;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    closePopupAuto();
    }

    @FXML
    private void openPopup(ActionEvent event){
        Button clickedbtn = (Button) event.getSource();
        switch (clickedbtn.getId()){
            case "nav":
                _openPopup(navPopup);
                break;
        }
    }

    @FXML
    private void _openPopup(Node popup) {
        popup.toFront();
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);

        if (!popup.isVisible()) {
            popup.setVisible(true);
            popup.setOpacity(0);
            fade.setFromValue(0);
            fade.setToValue(1);
        } else {
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> popup.setVisible(false));
        }
        fade.play();
    }

    public void closePopupAuto() {
        navPopup.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (navPopup.isVisible() && !navPopup.isHover()) {
                closePopup(navPopup);
            }
        });
    }



    private void closePopup(Node popup) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> popup.setVisible(false));
        fade.play();
    }
}
