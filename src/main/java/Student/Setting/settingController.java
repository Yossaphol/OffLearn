package Student.Setting;

import javafx.stage.FileChooser;
import mediaUpload.MediaUpload;
import Database.User;
import Database.UserDB;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import a_Session.SessionManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class settingController implements Initializable {

    public Button uploadPic;
    public Button editProfile;
    public TextField realName;
    public TextField lastName;
    public TextField username;
    public TextField gmail;
    public Button saveEditProfile;
    public HBox editingProfileBtnContainer;
    public Circle picture;
    public Button changePasswordBtn;
    public HBox changePasswordField;
    public TextField username1;
    public RadioButton quizTrue;
    public RadioButton quizFalse;
    public RadioButton mTrue;
    public RadioButton mFalse;
    public RadioButton cTrue;
    public RadioButton cFalse;
    public Button cancelEditProfile;
    public Button savePassword;
    public Button cancelPassword;
    public TextField oldpw;
    public TextField newpwfirst;
    public TextField newpwsecond;
    private boolean isChangePass = false;
    private MediaUpload m;
    HomeController hm = new HomeController();

    UserDB userDB = new UserDB();
    String sessionUsername = SessionManager.getInstance().getUsername();
    User user = userDB.getUserInfo(sessionUsername);

    String name = user.getFirstname();
    String lastNameUser = user.getLastname();
    String userName = user.getUsername();
    String gmailUser = user.getEmail();
//    String imgPath = (user.getProfile() != null) ? user.getProfile().toString() : "/img/Profile/user.png";
//    String imgPath = "/img/Profile/user.png";
    String selectedimg = "";

    private boolean isEditing = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        uploadPic.setVisible(false);

        setProfileValue(name, lastNameUser, gmailUser);
        setUserImage(user.getProfile());
        username1.setText(userName);
        username1.setEditable(false);
        setEffect();
    }

    private void setEffect(){
        hm.hoverEffect(uploadPic);
        hm.hoverEffect(saveEditProfile);
        hm.hoverEffect(cancelEditProfile);
        hm.hoverEffect(changePasswordBtn);
        hm.hoverEffect(savePassword);
        hm.hoverEffect(cancelEditProfile);
    }

    //Start profile edit part
    public void editProfile(ActionEvent e){
        if (!isEditing) {

            realName.setDisable(isEditing);
            lastName.setDisable(isEditing);
            gmail.setDisable(isEditing);
            editProfile.setVisible(isEditing);

            isEditing = true;
            openEditProfile(true);
        }
        else {
            closeProfileEdit();
        }
    }


    public void openEditProfile(Boolean isEditing) {
        realName.setEditable(isEditing);
        lastName.setEditable(isEditing);
        gmail.setEditable(isEditing);
        uploadPic.setVisible(isEditing);

        if (isEditing) {
            editingProfileBtnContainer.setOpacity(0);
            editingProfileBtnContainer.setVisible(true);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), editingProfileBtnContainer);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        } else {
            editingProfileBtnContainer.setVisible(false);
        }
    }

    public void closeEditProfile(Boolean isEditing) {
        realName.setEditable(isEditing);
        lastName.setEditable(isEditing);
        gmail.setEditable(isEditing);
        uploadPic.setVisible(isEditing);

        if (!isEditing) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), editingProfileBtnContainer);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> editingProfileBtnContainer.setVisible(false));
            fadeOut.play();
        }
    }


    public void setProfileValue(String Name, String LastName, String Gmail){
        this.name = Name;
        this.lastNameUser = LastName;
        this.gmailUser = Gmail;

        _setProfileValue(name, lastNameUser, gmailUser);
    }

    public void _setProfileValue(String Name, String LastName, String Gmail){
        realName.setText(Name);
        lastName.setText(LastName);
        gmail.setText(Gmail);
    }


    public void saveProfileEdit(ActionEvent e) {
        String newFirstname = realName.getText();
        String newLastname = lastName.getText();
        String newEmail = gmail.getText();

        boolean isUpdated = userDB.updateUserInfo(sessionUsername, newFirstname, newLastname, newEmail);

        if (isUpdated) {
            // Upload image to database if a new image was selected
            if (!selectedimg.isEmpty()) {
                boolean isImageUpdated = userDB.updateProfileImage(sessionUsername, selectedimg);
                if (isImageUpdated) {
                    user = userDB.getUserInfo(sessionUsername); // Refresh user info
                    setUserImage(user.getProfile());  // Update UI with the new profile image
                }
            }

            showAlert("Update Successful", "Success", Alert.AlertType.INFORMATION);
            setProfileValue(newFirstname, newLastname, newEmail);
            closeProfileEdit();
        } else {
            showAlert("Update Failed", "Fail", Alert.AlertType.ERROR);
        }
    }



    public void cancelProfileEdit(ActionEvent e){
//        loadAndSetUserImage(picture, imgPath);
        setProfileValue(name, lastNameUser, gmailUser);
        closeProfileEdit();
    }

    public void closeProfileEdit() {
        isEditing = false;
        realName.setDisable(isEditing);
        lastName.setDisable(isEditing);
        gmail.setDisable(isEditing);
        editProfile.setVisible(true);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), editingProfileBtnContainer);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(event -> editingProfileBtnContainer.setVisible(false));
        fadeOut.play();
        closeEditProfile(isEditing);
    }

    private void setUserImage(String imgPath) {
        try {
            if (imgPath == null || imgPath.isEmpty()) {
                imgPath = "/img/Profile/user.png";
            }

            Image img;

            if (imgPath.startsWith("http")) {
                img = new Image(imgPath, true);

                img.progressProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.doubleValue() == 1.0) {
                        Platform.runLater(() -> {
                            picture.setFill(new ImagePattern(img));
                        });
                    }
                });
            } else if (imgPath.startsWith("/") || getClass().getResource(imgPath) != null) {
                img = new Image(getClass().getResource(imgPath).toExternalForm());
                picture.setFill(new ImagePattern(img));
            } else {
                File file = new File(imgPath);
                if (!file.exists()) {
                    img = new Image(getClass().getResource("/img/Profile/user.png").toExternalForm());
                } else {
                    img = new Image(file.toURI().toString());
                }
                picture.setFill(new ImagePattern(img));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                Image defaultImg = new Image(getClass().getResource("/img/Profile/user.png").toExternalForm());
                picture.setFill(new ImagePattern(defaultImg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImg(ActionEvent e) {
        m = new MediaUpload();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String uploadedUrl = m.uploadImg(selectedFile);

            if (uploadedUrl != null) {
                selectedimg = uploadedUrl;

                // Ensure image is loaded on JavaFX Application Thread
                Platform.runLater(() -> {
                    setUserImage(selectedimg);
                });
            } else {
                showAlert("Upload Failed", "Unable to upload the image.", Alert.AlertType.ERROR);
            }
        }
    }


    //End profile edit part


    //Start password edit part
    public void updatepw(ActionEvent e){
        String username = SessionManager.getInstance().getUsername();
        String oldpwfromdb = userDB.getOldPasswordFromDB(username);
        String oldPassword = oldpw.getText();
        String newPasswordFirst = newpwfirst.getText();
        String confirmNewPassword = newpwsecond.getText();
        if (oldPassword.isEmpty() || newPasswordFirst.isEmpty() || confirmNewPassword.isEmpty()) {
            showAlert("Update Password Failed", "Please complete all fields.", Alert.AlertType.WARNING);
            return;
        }
        if (!newPasswordFirst.equals(confirmNewPassword)) {
            showAlert("Passwords do not match", "Please fill in the information to match.", Alert.AlertType.ERROR);
            return;
        }
        if (!oldpwfromdb.equals(oldPassword)) {
            showAlert("Error", "Old Password does not match.", Alert.AlertType.ERROR);
            return;
        }
        if (userDB.updatePassword(username, newPasswordFirst)) {
            showAlert("Success", "Update password successfully!", Alert.AlertType.INFORMATION);
            closePasswordField();
        } else {
            showAlert("Error", "Sorry, something went wrong!", Alert.AlertType.ERROR);
        }
    }

    public void changePassword(ActionEvent e){
        if(!isChangePass){
            isChangePass = true;
            openPasswordField();
        }
        else{
            isChangePass = false;
            closePasswordField();
        }
    }

    public void openPasswordField() {
        changePasswordBtn.setVisible(false);
        username1.setDisable(false);
        changePasswordField.setOpacity(0);
        changePasswordField.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), changePasswordField);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public void closePasswordField() {
        username1.setDisable(true);
        changePasswordBtn.setVisible(true);
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), changePasswordField);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> changePasswordField.setVisible(false));
        fadeOut.play();
    }
    public void cancelChangePass(ActionEvent e){
        closePasswordField();
    }


    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}