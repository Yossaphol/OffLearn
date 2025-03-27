package Teacher.navigator;

import Student.navBarAndSearchbar.navBarController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;



public class Navigator {

    private static navbarController navCtrl;
    public static void setNavBarController(navbarController navController) {
        navCtrl = navController;
    }
    public void dashboardRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/statistics/dashboard.fxml", event);
        navCtrl.setCurrentPage("dashboardLabel");
    }

    public void inboxRoute(MouseEvent event){
       navigateTo("/fxml/Teacher/pChat.fxml", event);
       navCtrl.setCurrentPage("inboxLabel");
    }

    public void courseRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/courseManagement/course.fxml", event);
        navCtrl.setCurrentPage("courseLabel");
    }

    public void withdrawRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/showBalance/withdraw.fxml", event);

    }

    public void courseEditRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/courseManagement/courseEdit.fxml", event);
    }

    public void addQuizOrTest(MouseEvent event){
        navigateTo("/fxml/Teacher/Quiz/addQuiz.fxml", event);
    }

    public void quizDetailRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/Quiz/quiz_detail.fxml", event);
    }

    public void addVideoRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/somethingWithVideo/uploadVideo.fxml", event);
    }

    public void videoDetailRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/somethingWithVideo/videoDetail.fxml", event);
    }

    public void withdrawDetailRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/showBalance/withdrawDetail.fxml", event);
    }

    public void withdrawHistoryRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/showBalance/withdrawHistory.fxml", event);
    }

    public void settingRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/setting/setting.fxml", event);
        navCtrl.setCurrentPage("settingLabel");
    }



    public void navigateTo(String fxmlPath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            root.setOpacity(0);
            stage.setScene(scene);
            stage.show();


            //Refresh layout when page change
            Platform.runLater(() -> {
                stage.setWidth(stage.getWidth() - 1);
                stage.setHeight(stage.getHeight() - 1);
                stage.setWidth(stage.getWidth() + 1);
                stage.setHeight(stage.getHeight() + 1);
            });

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            stage.setFullScreen(false);
            stage.setMaximized(true);

        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }


  /*  public void navigateTo(String fxmlPath, Stage stage) {
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
    }*/

}
