package Student.loginAndSignUp;

import Database.UserDB;
import Student.FontLoader.FontLoader;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class forgotPasswordController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final UserDB userDB = new UserDB();

    @FXML
    private TextField getGmail;
    @FXML
    private TextField getUsername;
    @FXML
    private PasswordField getNewPassword;
    @FXML
    private PasswordField getConfirmPassword;

    FontLoader fontLoader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fontLoader = new FontLoader();
        fontLoader.loadFonts();
    }
    @FXML
    private void handleForgotPassword() {
        String gmail = getGmail.getText();
        String username = getUsername.getText();
        String newPassword = getNewPassword.getText();
        String confirmPassword = getConfirmPassword.getText();

        if (gmail.isEmpty() || username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Update Password Failed", "Please complete all fields.", Alert.AlertType.WARNING);
            return;
        }
        if (!userDB.isUserValid(username, gmail)) {
            showAlert("Update Password Failed", "Username or Gmail is incorrect.", Alert.AlertType.ERROR);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Update Password Failed", "New password and confirm password do not match. Please try again.", Alert.AlertType.ERROR);
            return;
        }

        if (userDB.updatePasswordConnect(gmail, username, newPassword)) {
            showAlert("Update Password Successful", "Updated password successfully!", Alert.AlertType.INFORMATION);
            backToLogin();
        } else {
            showAlert("Update Password Failed", "An error occurred while changing your password.", Alert.AlertType.ERROR);
        }
    }
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void backToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/LoginSingup/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) getUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void toLogin(ActionEvent event) throws IOException {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5));
        fadeOut.setNode(((Node) event.getSource()).getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/LoginSingup/login.fxml"));
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
}

