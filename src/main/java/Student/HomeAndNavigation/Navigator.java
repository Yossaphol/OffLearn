package Student.HomeAndNavigation;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import Student.mainPage.mainPageController;

import java.io.IOException;

public class Navigator {

    private static mainPageController controller;

    public static void setController(mainPageController ctrl) {
        controller = ctrl;
    }

    public void homeRoute(MouseEvent event){
        navigateTo("/fxml/Student/HomePage/home.fxml");
    }

    public void dashboardRoute(MouseEvent event){
        System.out.println("You click at dashboard!");
        navigateTo("/fxml/Student/statistics/dashboard.fxml");
    }

    public void courseRoute(MouseEvent event){
        navigateTo("/fxml/Student/courseManage/course.fxml");
    }

    public void inboxRoute(MouseEvent event){
        navigateTo("/fxml/Student/inbox/pChat.fxml");
    }

    public void taskRoute(MouseEvent event){
        navigateTo("/fxml/Student/courseManage/task.fxml");
    }

    public void roadmapRoute(MouseEvent event){
        navigateTo("/fxml/Student/courseManage/roadmap.fxml");
    }

    public void myCourseRoute(MouseEvent event){
        navigateTo("/fxml/Student/courseManage/myCourse.fxml");
    }

    public void cartRoute(MouseEvent event){
        navigateTo("/fxml/Student/courseManage/cart.fxml");
    }

    public void leaderboardRoute(MouseEvent event){
        navigateTo("/fxml/Student/statistics/leaderboard.fxml");
    }

    public void settingRoute(MouseEvent event){
        navigateTo("/fxml/Student/setting/setting.fxml");
    }

    public void logoutRoute(MouseEvent event){
        navigateTo("/fxml/Student/LoginSignup/logout.fxml");
    }

    private void navigateTo(String fxmlPath) {
        if (controller != null) {
            controller.displayContent(fxmlPath);
        } else {
            System.out.println("Navigator Error: mainPageController instance is null!");
        }
    }

}


//    public void navigateTo(String fxmlPath, MouseEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            Parent root = loader.load();
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root);
//
//            root.setOpacity(0);
//            stage.setScene(scene);
//
//            stage.setFullScreen(true);
//            stage.setFullScreenExitHint("");
//
//            stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
//                if (!isNowFullScreen) {
//                    stage.setFullScreen(false);
//                    stage.setMaximized(true);
//                }
//            });
//
//            stage.show();
//
//            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
//            fadeIn.setFromValue(0);
//            fadeIn.setToValue(1);
//            fadeIn.play();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void navigateTo(String fxmlPath, Stage stage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            Parent root = loader.load();
//
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.setFullScreen(false);
//            stage.setFullScreenExitHint("");
//
//            stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
//                if (!isNowFullScreen) {
//                    stage.setFullScreen(false);
//                    stage.setMaximized(true);
//                }
//            });
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }