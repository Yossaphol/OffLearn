package Teacher.navigator;

import Student.navBarAndSearchbar.navBarController;
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

    private static navbarController navCtrl;

    public static void setNavBarController(navbarController navController) {
        navCtrl = navController;
    }

    private void setCurrentPageSafe(String label) {
        if (navCtrl != null) {
            navCtrl.setCurrentPage(label);
        } else {
            System.err.println("⚠️ navCtrl is null. Did you forget to call Navigator.setNavBarController()?");
        }
    }

    public void dashboardRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/statistics/dashboard.fxml", event);
        setCurrentPageSafe("dashboardLabel");
    }

    public void inboxRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/pChat.fxml", event);
        setCurrentPageSafe("inboxLabel");
    }

    public void courseRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/courseManagement/course.fxml", event);
        setCurrentPageSafe("courseLabel");
    }

    public void withdrawRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/showBalance/withdraw.fxml", event);
    }

    public void courseEditRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/courseManagement/courseEdit.fxml", event);
    }

    public void addQuizOrTest(MouseEvent event) {
        navigateTo("/fxml/Teacher/Quiz/addQuiz.fxml", event);
    }

    public void quizDetailRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/Quiz/quiz_detail.fxml", event);
    }

    public void addVideoRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/somethingWithVideo/uploadVideo.fxml", event);
    }

    public void videoDetailRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/somethingWithVideo/videoDetail.fxml", event);
    }

    public void withdrawDetailRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/showBalance/withdrawDetail.fxml", event);
    }

    public void withdrawHistoryRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/showBalance/withdrawHistory.fxml", event);
    }

    public void settingRoute(MouseEvent event) {
        navigateTo("/fxml/Teacher/setting/setting.fxml", event);
        setCurrentPageSafe("settingLabel");
    }

    public void navigateTo(String fxmlPath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Node source = (Node) event.getSource();
            if (source == null || source.getScene() == null) {
                System.err.println("Error: Event source is null or has no scene.");
                return;
            }

            Stage stage = (Stage) source.getScene().getWindow();
            Scene scene = new Scene(root);

            root.setOpacity(0);
            stage.setScene(scene);
            stage.show();

            Platform.runLater(stage::sizeToScene);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            stage.setMaximized(true);

        } catch (IOException e) {
            System.err.println("❌ Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
