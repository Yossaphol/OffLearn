package Teacher.navigator;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class navbarController implements Initializable {
    public VBox navPopup;
    public Button nav;
    public ImageView nav_icon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    closePopupAuto();
    applyHoverEffectToInside(navPopup);
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
        popup.setViewOrder(-1);
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
        RotateTransition rotate = new RotateTransition(Duration.millis(300), nav_icon);

        if (!popup.isVisible()) {
            rotate.setToAngle(180);
            nav_icon.setImage(new Image(getClass().getResource("/img/icon/close1.png").toExternalForm()));

            popup.setVisible(true);
            popup.setOpacity(0);
            fade.setFromValue(0);
            fade.setToValue(1);
        } else {
            rotate.setToAngle(0);
            nav_icon.setImage(new Image(getClass().getResource("/img/icon/menu.png").toExternalForm()));

            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> popup.setVisible(false));
        }

        rotate.play();
        fade.play();
    }

    public void closePopupAuto() {
        navPopup.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    if (navPopup.isVisible() && !navPopup.isHover()) {
                        closePopup(navPopup);
                    }
                });
            }
        });
    }


    private void closePopup(Node popup) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
        RotateTransition rotate = new RotateTransition(Duration.millis(300), nav_icon);
        rotate.setToAngle(0);
        nav_icon.setImage(new Image(getClass().getResource("/img/icon/menu.png").toExternalForm()));

        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> popup.setVisible(false));

        rotate.play();
        fade.play();
    }

    public void applyHoverEffectToInside(VBox root) {
        for (Node node : root.lookupAll(".navT")) {
            if (node instanceof HBox p) {
                hoverEffect(p);
            }
        }
    }
    public void hoverEffect(HBox hBox) {
        // Scale transition
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), hBox);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.07);
        scaleUp.setToY(1.07);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), hBox);
        scaleDown.setFromX(1.07);
        scaleDown.setFromY(1.07);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        DropShadow glow = new DropShadow();
        glow.setRadius(20);
        glow.setSpread(0.5);
        glow.setColor(Color.web("#EFF7FF", 1));

        hBox.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
            switch (hBox.getId()) {
                default:
                    hBox.setStyle("-fx-background-color: #EFF7FF;");
                    break;
            }
        });

        hBox.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            switch (hBox.getId()){
                case "learn_now":
                    hBox.setEffect(null);
                    hBox.setStyle("-fx-background-radius: 30;" + "-fx-background-color: #8100CC;");
                    break;
                default :
                    hBox.setStyle("-fx-background-color: transparent;");
            }

        });
    }


}
