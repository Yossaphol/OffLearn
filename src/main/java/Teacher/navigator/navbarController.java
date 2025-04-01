package Teacher.navigator;

import Student.navBarAndSearchbar.navBarController;
import Teacher.navigator.Navigator;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class navbarController implements Initializable {

    public HBox navPopup;
    public Button nav;
    public ImageView nav_icon;
    public Button withdrawBtn;
    public HBox dashboard;
    public HBox course;
    public HBox inbox;
    public HBox setting;
    public Circle userPfp;
    public Label dashboardLabel;
    public Label courseLabel;
    public Label inboxLabel;
    public Label settingLabel;
    public HBox logo;
    public Button logout;

    private String currentPage;
    public ArrayList<Label> buttons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Navigator.setNavBarController(this); // ✅ set ตัวเองให้ Navigator
        closePopupAuto();
        applyHoverEffectToInside(navPopup);
        hoverEffect(withdrawBtn);
        route();

        buttons.add(dashboardLabel);
        buttons.add(courseLabel);
        buttons.add(inboxLabel);
        buttons.add(settingLabel);

        setCurrentPage("dashboardLabel");
        changeFontColor();
    }

    public void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to sign out?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            SessionManager.getInstance().clearSession();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/LoginSingup/login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) logout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Login Page");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void openPopup(ActionEvent event) {
        Button clickedbtn = (Button) event.getSource();
        switch (clickedbtn.getId()) {
            case "nav":
            case "month_select":
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
        for (Node node : root.lookupAll(".navT")) {
            if (node instanceof HBox p) {
                hoverEffect(p);
            }
        }
    }

    public void hoverEffect(HBox hBox) {
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
            hBox.setStyle("-fx-background-color: #EFF7FF;");
        });

        hBox.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            if ("learn_now".equals(hBox.getId())) {
                hBox.setEffect(null);
                hBox.setStyle("-fx-background-radius: 30; -fx-background-color: #8100CC;");
            } else {
                hBox.setStyle("-fx-background-color: transparent;");
            }
        });
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

        btn.setOnMouseEntered(mouseEvent -> scaleUp.play());
        btn.setOnMouseExited(mouseEvent -> scaleDown.play());
    }

    public void route() {
        Navigator nav = new Navigator();
        dashboard.setOnMouseClicked(nav::dashboardRoute);
        logo.setOnMouseClicked(nav::dashboardRoute);
        course.setOnMouseClicked(nav::courseRoute);
        inbox.setOnMouseClicked(nav::inboxRoute);
        withdrawBtn.setOnMouseClicked(nav::withdrawRoute);
        setting.setOnMouseClicked(nav::settingRoute);
    }

    public void changeFontColor() {
        for (Label btn : buttons) {
            btn.setTextFill(btn.getId().equals(currentPage) ? Color.web("#0675DE") : Color.BLACK);
        }
    }

    public void setCurrentPage(String id) {
        currentPage = id;
        changeFontColor();
    }
}
