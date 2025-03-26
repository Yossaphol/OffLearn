package Student.navBarAndSearchbar;

import Database.UserDB;
import Student.HomeAndNavigation.*;
import a_Session.SessionManager;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class searchBarC implements Initializable {
    public HBox setting;
    public HBox cart;
    public Button allCourse;
    public HBox profileBox;
    public HBox navPopup;
    public ImageView nav_icon;
    public Button nav;
    public Label settingLabel;
    public Label cartLabel;
    public Circle userPfp;
    @FXML
    public Label username;

    String userName = SessionManager.getInstance().getUsername();
    UserDB userDB = new UserDB();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closePopupAuto();
        applyHoverEffectToInside(navPopup);
        hoverEffect();
        hoverEffect1(profileBox);
        setProfile(userDB.getProfile(userName));
        route();

        String username = SessionManager.getInstance().getUsername();
        if (username != null) {
            this.username.setText(username);
        } else {
            this.username.setText("Guest");
        }
    }

    public void setProfile(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }
        this.userPfp.setStroke(Color.TRANSPARENT);
        this.userPfp.setFill(new ImagePattern(img));
    }

    private void hoverEffect1(HBox btn) {
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
            username.setTextFill(Color.web("#8100cc"));
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            username.setTextFill(Color.BLACK);
            scaleDown.play();
        });
    }

    public void handleLogout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to sign out?");

        if (alert.showAndWait().get() == ButtonType.OK){
            SessionManager.getInstance().clearSession();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/LoginSingup/login.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) username.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Login Page");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void hoverEffect(){
        HomeController hm = new HomeController();
        hm.hoverEffect(allCourse);
    }
    public void route(){
        Navigator nav = new Navigator();
        allCourse.setOnMouseClicked(nav::courseRoute);
        cart.setOnMouseClicked(nav::cartRoute);
        username.setOnMouseClicked(nav::dashboardRoute);
        userPfp.setOnMouseClicked(nav::dashboardRoute);
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

    public void applyHoverEffectToInside(HBox root) {
        for (Node node : root.lookupAll(".navT1")) {
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
        glow.setColor(Color.web("#F7E9FF", 1));

        hBox.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
            switch (hBox.getId()) {
                case "setting":
                    settingLabel.setTextFill(Color.web("#8100cc"));
                    break;
                case "cart":
                    cartLabel.setTextFill(Color.web("#8100cc"));
                    break;
                default:
                    hBox.setStyle("-fx-background-color: #F7E9FF;");
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
                case "setting":
                    settingLabel.setTextFill(Color.BLACK);
                    break;
                case "cart":
                    cartLabel.setTextFill(Color.BLACK);
                default :
                    hBox.setStyle("-fx-background-color: transparent;");
            }

        });
    }




}
