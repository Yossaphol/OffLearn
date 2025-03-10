package Student.loginAndSignUp;

import Database.UserDB;
import Student.FontLoader.FontLoader;
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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.util.Duration;

public class signupController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final UserDB userDB = new UserDB();

    @FXML
    private TextField getFirstName;
    @FXML
    private TextField getLastName;
    @FXML
    private TextField getUsername;
    @FXML
    private TextField getPassword;
    @FXML
    private TextField getEmail;

    FontLoader fontLoader;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fontLoader = new FontLoader();
        fontLoader.loadFonts();
    }

    @FXML
    private void handleSignup() {
        String firstName = getFirstName.getText();
        String lastName = getLastName.getText();
        String fullname = firstName + " " + lastName;
        String username = getUsername.getText();
        String password = getPassword.getText();
        String email = getEmail.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            showAlert("Sign Up Failed", "Please fill in all fields.", Alert.AlertType.WARNING);
            return;
        }

        if (userDB.signupConnect(fullname, username, password, email)) {
            showAlert("Signup Successful", "Account created successfully!", Alert.AlertType.INFORMATION);
            backToLogin();
        } else {
            showAlert("Sign Up Failed", "The username or Gmail already exists.", Alert.AlertType.ERROR);
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
    private void backToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/LoginSingup/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) getFirstName.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Link to login
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
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
