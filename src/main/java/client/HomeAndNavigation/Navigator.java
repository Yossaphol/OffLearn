package client.HomeAndNavigation;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Navigator {



    public void homeRoute(MouseEvent event){
        navigateTo("/fxml/client/HomePage/home.fxml", event);

    }

    public void dashboardRoute(MouseEvent event){
        navigateTo("/fxml/client/statistics/dashboard.fxml", event);
    }

    public void courseRoute(MouseEvent event){
        navigateTo("/fxml/client/courseManage/course.fxml", event);
    }

    public void inboxRoute(MouseEvent event){
        navigateTo("/fxml/client/inbox/pChat.fxml", event);
    }

    public void taskRoute(MouseEvent event){
        navigateTo("/fxml/client/courseManage/task.fxml", event);
    }

    public void roadmapRoute(MouseEvent event){
        navigateTo("/fxml/client/courseManage/roadmap.fxml", event);
    }

    public void myCourseRoute(MouseEvent event){
        navigateTo("/fxml/client/courseManage/myCourse.fxml", event);
    }

    public void cartRoute(MouseEvent event){
        navigateTo("/fxml/client/courseManage/cart.fxml", event);
    }

    public void leaderboardRoute(MouseEvent event){
        navigateTo("/fxml/client/statistics/leaderboard.fxml", event);
    }

    public void settingRoute(MouseEvent event){
        navigateTo("/fxml/client/setting/setting.fxml", event);
    }

    public void logoutRoute(MouseEvent event){
        navigateTo("/fxml/client/LoginSingup/logout.fxml", event);
    }

    public void navigateTo(String fxmlPath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            root.setOpacity(0);
            stage.setScene(scene);

            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");

            stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
                if (!isNowFullScreen) {
                    stage.setFullScreen(false);
                    stage.setMaximized(true);
                }
            });

            stage.show();

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateTo(String fxmlPath, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setFullScreen(false);
            stage.setFullScreenExitHint("");

            stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
                if (!isNowFullScreen) {
                    stage.setFullScreen(false);
                    stage.setMaximized(true);
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}