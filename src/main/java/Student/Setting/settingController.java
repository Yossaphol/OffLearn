package Student.Setting;

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
    public TextField name1;
    public TextField lastName1;
    public TextField accountNo;
    public TextField bankName;
    public HBox editPaymentBox;
    public Button editPaymentBtn;
    public Button cancelEditProfile;
    public Button savePassword;
    public Button cancelPassword;
    public TextField oldpw;
    public TextField newpwfirst;
    public TextField newpwsecond;
    private boolean isChangePass = false;
    HomeController hm = new HomeController();

    UserDB userDB = new UserDB();
    String sessionUsername = SessionManager.getInstance().getUsername();
    User user = userDB.getUserInfo(sessionUsername);

    String name = user.getFirstname();
    String lastNameUser = user.getLastname();
    String userName = user.getUsername();
    String gmailUser = user.getEmail();
    String imgPath = (user.getProfile() != null) ? user.getProfile().toString() : "/img/Profile/user.png";
//    String imgPath = "/img/Profile/user.png";
    String selectedimg = "";

    private boolean isEditing = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        uploadPic.setVisible(false);
        setProfileValue(name, lastNameUser, gmailUser);
        loadAndSetUserImage(picture, imgPath);
        username1.setText(userName);
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


    public void saveProfileEdit(ActionEvent e){
        String newFirstname = realName.getText();
        String newLastname = lastName.getText();
        String newEmail = gmail.getText();

        UserDB userDB = new UserDB();
        boolean isUpdated = userDB.updateUserInfo(sessionUsername, newFirstname, newLastname, newEmail);
        if (isUpdated) {
            showAlert("Update Successful", "Success", Alert.AlertType.INFORMATION);
            imgPath = selectedimg;
            setProfileValue(newFirstname, newLastname, newEmail);
            closeProfileEdit();
        } else {
            showAlert("Update Failed", "Fail", Alert.AlertType.ERROR);
        }
//        imgPath = selectedimg;
//        setProfileValue(realName.getText(), lastName.getText(), gmail.getText());
//        closeProfileEdit();

    }

    public void cancelProfileEdit(ActionEvent e){
        loadAndSetUserImage(picture, imgPath);
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

    public void uploadImg(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
//
//        if (selectedFile != null) {
//            // ตั้งค่าตำแหน่งของไฟล์ที่เลือก
//            selectedimg = selectedFile.getAbsolutePath();
//
//            // อัปโหลดไฟล์ไปยังฐานข้อมูล
//            boolean isUploaded = userDB.updateProfileImage(sessionUsername, selectedFile);
//
//            if (isUploaded) {
//                // ถ้าอัปโหลดสำเร็จ ให้แสดงรูปใหม่ใน UI
//                loadAndSetUserImage(picture, selectedimg);
//            }
//        }
    }

    public void loadAndSetUserImage(Shape shape, String path) {
        try {
            Image img;
            if (path.startsWith("/") || getClass().getResource(path) != null) {
                img = new Image(getClass().getResource(path).toExternalForm());
            } else {
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("File not found: " + path);
                    return;
                }
                img = new Image(file.toURI().toString());
            }

            shape.setStroke(Color.TRANSPARENT);
            shape.setFill(new ImagePattern(img));
        } catch (Exception ex) {
            ex.printStackTrace();
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
            showAlert("Success", "เปลี่ยนรหัสผ่านสำเร็จ!", Alert.AlertType.INFORMATION);
            closePasswordField();
        } else {
            showAlert("Error", "เกิดข้อผิดพลาดในการเปลี่ยนรหัสผ่าน!", Alert.AlertType.ERROR);
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