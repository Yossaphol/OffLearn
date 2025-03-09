package Teacher.navigator;

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

    public void dashboardRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/statistics/dashboard.fxml", event);
    }

    public void inboxRoute(MouseEvent event){
    //    navigateTo("/fxml/Teacher/inbox/pChat.fxml", event);
    }

    public void courseRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/course.fxml", event);
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

    public void settingRoute(MouseEvent event){
        navigateTo("/fxml/Teacher/setting/setting.fxml", event);
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
