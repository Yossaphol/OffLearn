package HomeAndNavigation;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
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
        navigateTo("/fxml/HomePage/home.fxml", event);

    }

    public void dashboardRoute(MouseEvent event){
        navigateTo("/fxml/statistics/dashboard.fxml", event);
    }

    public void courseRoute(MouseEvent event){
        navigateTo("/fxml/courseManage/course.fxml", event);
    }

    public void inboxRoute(MouseEvent event){
        navigateTo("/fxml/inbox/pChat.fxml", event);
    }

    public void taskRoute(MouseEvent event){
        navigateTo("/fxml/courseManage/task.fxml", event);
    }

    public void roadmapRoute(MouseEvent event){
        navigateTo("/fxml/courseManage/roadmap.fxml", event);
    }

    public void myCourseRoute(MouseEvent event){
        navigateTo("/fxml/courseManage/myCourse.fxml", event);
    }

    public void cartRoute(MouseEvent event){
        navigateTo("/fxml/courseManage/cart.fxml", event);
    }

    public void leaderboardRoute(MouseEvent event){
        navigateTo("/fxml/statistics/leaderboard.fxml", event);
    }

    public void settingRoute(MouseEvent event){
        navigateTo("/fxml/setting/setting.fxml", event);
    }

    public void logoutRoute(MouseEvent event){
        navigateTo("/fxml/LoginSingup/logout.fxml", event);
    }

    public void navigateTo(String fxmlPath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            root.setOpacity(0);
            stage.setScene(scene);
            stage.setMaximized(true);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            stage.show();
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
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}