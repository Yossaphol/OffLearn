package Student.loginAndSignUp;

import Database.ConnectDB;
import Database.UserDB;
import Student.FontLoader.FontLoader;

import a_Session.SessionManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final UserDB userDB = new UserDB();

    @FXML
    private TextField getUsername;
    @FXML
    private PasswordField getPassword;

    FontLoader fontLoader;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fontLoader = new FontLoader();
        fontLoader.loadFonts();

    }

    @FXML
    private void handleLogin() {
        String username = getUsername.getText();
        String password = getPassword.getText();

        if (username.isEmpty() & password.isEmpty()) {
            showAlert("Login Failed", "Please enter username and password.", Alert.AlertType.WARNING);
            return;
        } else if (username.isEmpty()) {
            showAlert("Login Failed", "Please enter username.", Alert.AlertType.WARNING);
            return;
        } else if (password.isEmpty()) {
            showAlert("Login Failed", "Please enter password.", Alert.AlertType.WARNING);
            return;
        }

        if (userDB.loginConnect(username, password)) {
            SessionManager.getInstance().setUsername(username);
            showAlert("Login Successful", "Welcome, " + username, Alert.AlertType.INFORMATION);
            openHomePage();
        } else {
            showAlert("Login Failed", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void openHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/mainPage.fxml"));
            root = loader.load();

            Stage stage = (Stage) getUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot load home page.", Alert.AlertType.ERROR);
        }
    }
    // Link to signup
    @FXML
    private void switchtoSignUp(ActionEvent event) throws IOException {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5));
        fadeOut.setNode(((Node) event.getSource()).getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/LoginSingup/signup.fxml"));
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

    // Link to forgotPassword
    @FXML
    private void forgotPassword(ActionEvent event) throws IOException {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5));
        fadeOut.setNode(((Node) event.getSource()).getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/LoginSingup/forgotPassword.fxml"));
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

    @FXML
    public void switchtoTeacherLogin(ActionEvent event) throws IOException {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5));
        fadeOut.setNode(((Node) event.getSource()).getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Teacher/LoginAndSignup/login.fxml"));
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
