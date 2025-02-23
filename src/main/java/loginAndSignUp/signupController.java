package loginAndSignUp;

import javafx.animation.FadeTransition;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.util.Duration;

public class signupController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Link to login
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5));
        fadeOut.setNode(((Node) event.getSource()).getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);


        fadeOut.setOnFinished(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginSingup/login.fxml"));
                root = loader.load();

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5));
                fadeIn.setNode(root);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);

                fadeIn.play();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        fadeOut.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
