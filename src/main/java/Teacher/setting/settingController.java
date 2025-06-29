package Teacher.setting;

import Database.User;
import Database.UserDB;
import Database.withdraw;
import Database.WithdrawDB;
import Student.HomeAndNavigation.HomeController;
import a_Session.SessionManager;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import mediaUpload.MediaUpload;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class settingController implements Initializable {
    public HBox navBar;
    public VBox setting_container;
    public Label setting_header;
    public VBox privateinfo_container;
    public Label privateinfo_header;
    public Label privateinfo_header_firstname;
    public TextField privateinfo_firstname;
    public Label privateinfo_header_lastname;
    public TextField privateinfo_lastname;
    public Label privateinfo_gmail_header;
    public TextField privateinfo_gmail;
    public VBox security_container;
    public Label security_header;
    public Label security_username_header;
    public TextField security_username;
    public Label security_password_header;
    public Button security_change_button;
    public Label payment_header;
    public Label bankaccount_header;
    public Label bankaccount_firstname_header;
    public TextField bankaccount_firstname;
    public Label bankaccount_lastname_header;
    public TextField bankaccount_lastname;
    public Label bankaccount_number_header;
    public TextField bankaccount_number;
    public Label bankaccount_bank_header;
    public TextField bankaccount_bank;
    public Button bankaccount_change_button;
    public PasswordField security_password;
    public HBox saveCancelBtn;
    public HBox saveCancelBtn1;
    public Button cancelChange1;
    public Button saveChange1;
    public Button saveChange;
    public Button cancelChange;
    public HBox confirmPassContainer;
    public HBox saveCancelProfileBtn;
    public Button uploadPic;
    public Circle picture;
    public Button editBtn;
    public Button cancelProfile;
    public Button saveProfile;
    private MediaUpload m;
    public TextField oldpw;
    public TextField newpwfirst;
    public TextField newpwsecond;
    public TextField description;

    private boolean isPasswordEditing = false;
    private boolean isPaymentEditing = false;

    HomeController ef = new HomeController();

    UserDB userDB = new UserDB();
    String sessionUsername = SessionManager.getInstance().getUsername();
    WithdrawDB db;
    withdraw wd;
    User user = userDB.getUserInfo(sessionUsername);
    int userID = userDB.getUserId(SessionManager.getInstance().getUsername());

    String firstname = user.getFirstname();
    String lastNameUser = user.getLastname();
    String userName = user.getUsername();
    String gmailUser = user.getEmail();
    String descriptionUser = user.getDescription();
    String selectedimg = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();
        setWithdrawInfo();
        setProfileValue(firstname, lastNameUser, gmailUser, descriptionUser);
        setUserImage(user.getProfile());
        security_username.setText(userName);
        security_username.setEditable(false);

        setEffect();
        uploadPic.setVisible(false);

        db = new WithdrawDB();
        wd = db.getWithdrawInfo(userID);

        if (wd == null) {
            bankaccount_change_button.setText("เพิ่มบัญชี");
        }

    }


    public void setProfileValue(String Name, String LastName, String Gmail, String descriptionn){
        this.firstname = Name;
        this.lastNameUser = LastName;
        this.gmailUser = Gmail;
        this.descriptionUser = descriptionn;

        _setProfileValue(firstname, lastNameUser, gmailUser, descriptionUser);
    }

    public void _setProfileValue(String Name, String LastName, String Gmail, String descriptionn){
        privateinfo_firstname.setText(Name);
        privateinfo_lastname.setText(LastName);
        privateinfo_gmail.setText(Gmail);
        description.setText(descriptionn);
    }

    public void saveProfileEdit(ActionEvent e) {
        String newFirstname = privateinfo_firstname.getText();
        String newLastname = privateinfo_lastname.getText();
        String newEmail = privateinfo_gmail.getText();
        String adddescription = description.getText();

        boolean isUpdated = userDB.updateUserInfo(sessionUsername, newFirstname, newLastname, newEmail, adddescription);

        if (isUpdated) {
            if (!selectedimg.isEmpty()) {
                boolean isImageUpdated = userDB.updateProfileImage(sessionUsername, selectedimg);
                if (isImageUpdated) {
                    user = userDB.getUserInfo(sessionUsername);
                    setUserImage(user.getProfile());
                }
            }

            showAlert("Update Successful", "Success", Alert.AlertType.INFORMATION);
            setProfileValue(newFirstname, newLastname, newEmail, adddescription);
            editProfile();
        } else {
            showAlert("Update Failed", "Fail", Alert.AlertType.ERROR);
        }
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

    private void setEffect(){
        ef.hoverEffect(security_change_button);
        ef.hoverEffect(saveChange);
        ef.hoverEffect(cancelChange);

        ef.hoverEffect(bankaccount_change_button);
        ef.hoverEffect(saveChange1);
        ef.hoverEffect(cancelChange1);

        ef.hoverEffect(uploadPic);
        ef.hoverEffect(saveProfile);
        ef.hoverEffect(cancelProfile);
    }

    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            navBar.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //เปิด-ปิด การแก้ไข TextField Pasword
    public void editPassword(ActionEvent event){
        if(!isPasswordEditing){
            security_username.setDisable(isPasswordEditing);
            security_change_button.setVisible(isPasswordEditing);

            isPasswordEditing = true;
            saveCancelBtn.setVisible(isPasswordEditing);
            confirmPassContainer.setVisible(isPasswordEditing);
        }else{
            security_username.setDisable(isPasswordEditing);
            security_change_button.setVisible(isPasswordEditing);

            isPasswordEditing = false;
            saveCancelBtn.setVisible(isPasswordEditing);
            confirmPassContainer.setVisible(isPasswordEditing);
        }
        applyTransition(security_username);
        applyTransition(security_change_button);
        applyTransition(saveCancelBtn);
        applyTransition(confirmPassContainer);
    }

    //เปิด-ปิด การแก้ไข TextField Pasword
    public void editPassword(){
        if(!isPasswordEditing){
            security_username.setDisable(isPasswordEditing);
            security_change_button.setVisible(isPasswordEditing);

            isPasswordEditing = true;
            saveCancelBtn.setVisible(isPasswordEditing);
            confirmPassContainer.setVisible(isPasswordEditing);
        }else{
            security_username.setDisable(isPasswordEditing);
            security_change_button.setVisible(isPasswordEditing);

            isPasswordEditing = false;
            saveCancelBtn.setVisible(isPasswordEditing);
            confirmPassContainer.setVisible(isPasswordEditing);
        }
        applyTransition(security_username);
        applyTransition(security_change_button);
        applyTransition(saveCancelBtn);
        applyTransition(confirmPassContainer);
    }

    //เปิด-ปิด การแก้ไข TextField Profile
    public void editProfile(ActionEvent e){
        if(!saveCancelProfileBtn.isVisible()){
            //Visible
            saveCancelProfileBtn.setVisible(true);
            uploadPic.setVisible(true);
            editBtn.setVisible(false);

            //set Textfield
            privateinfo_firstname.setDisable(false);
            privateinfo_lastname.setDisable(false);
            privateinfo_gmail.setDisable(false);
            description.setDisable(false);

        }else{
            //Visible
            saveCancelProfileBtn.setVisible(false);
            uploadPic.setVisible(false);
            editBtn.setVisible(true);

            //set Textfield
            privateinfo_firstname.setDisable(true);
            privateinfo_lastname.setDisable(true);
            privateinfo_gmail.setDisable(true);
            description.setDisable(true);

        }
        applyTransition(saveCancelProfileBtn);
        applyTransition(uploadPic);
        applyTransition(editBtn);
    }

    //เปิด-ปิด การแก้ไข TextField Profile
    public void editProfile(){
        if(!saveCancelProfileBtn.isVisible()){
            //Visible
            saveCancelProfileBtn.setVisible(true);
            uploadPic.setVisible(true);
            editBtn.setVisible(false);

            //set Textfield
            privateinfo_firstname.setDisable(false);
            privateinfo_lastname.setDisable(false);
            privateinfo_gmail.setDisable(false);
            description.setDisable(false);
        }else{
            //Visible
            saveCancelProfileBtn.setVisible(false);
            uploadPic.setVisible(false);
            editBtn.setVisible(true);

            //set Textfield
            privateinfo_firstname.setDisable(true);
            privateinfo_lastname.setDisable(true);
            privateinfo_gmail.setDisable(true);
            description.setDisable(true);

        }
        applyTransition(saveCancelProfileBtn);
        applyTransition(uploadPic);
        applyTransition(editBtn);
    }


    //เปิด-ปิด การแก้ไข TextField Payment
    public void editPayment(ActionEvent event){
        if(!isPaymentEditing){
            bankaccount_firstname.setDisable(isPaymentEditing);
            bankaccount_lastname.setDisable(isPaymentEditing);
            bankaccount_number.setDisable(isPaymentEditing);
            bankaccount_bank.setDisable(isPaymentEditing);
            bankaccount_change_button.setVisible(isPaymentEditing);

            isPaymentEditing = true;
            saveCancelBtn1.setVisible(isPaymentEditing);
        }else{
            bankaccount_firstname.setDisable(isPaymentEditing);
            bankaccount_lastname.setDisable(isPaymentEditing);
            bankaccount_number.setDisable(isPaymentEditing);
            bankaccount_bank.setDisable(isPaymentEditing);
            bankaccount_change_button.setVisible(isPaymentEditing);

            isPaymentEditing = false;
            saveCancelBtn1.setVisible(isPaymentEditing);
        }
        applyTransition(bankaccount_change_button);
        applyTransition(saveCancelBtn1);
    }
    //เปิด-ปิด การแก้ไข TextField Payment
    public void editPayment() {
        if (!isPaymentEditing) {
            bankaccount_firstname.setDisable(isPaymentEditing);
            bankaccount_lastname.setDisable(isPaymentEditing);
            bankaccount_number.setDisable(isPaymentEditing);
            bankaccount_bank.setDisable(isPaymentEditing);
            bankaccount_change_button.setVisible(isPaymentEditing);

            isPaymentEditing = true;
            saveCancelBtn1.setVisible(isPaymentEditing);
        } else {
            bankaccount_firstname.setDisable(isPaymentEditing);
            bankaccount_lastname.setDisable(isPaymentEditing);
            bankaccount_number.setDisable(isPaymentEditing);
            bankaccount_bank.setDisable(isPaymentEditing);
            bankaccount_change_button.setVisible(isPaymentEditing);

            isPaymentEditing = false;
            saveCancelBtn1.setVisible(isPaymentEditing);
        }
        applyTransition(bankaccount_change_button);
        applyTransition(saveCancelBtn1);
    }

    @FXML
    public void cancelChangeProfile(ActionEvent event){
        setProfileValue(firstname, lastNameUser, gmailUser, descriptionUser);
        editProfile();
    }

    public void setWithdrawInfo() {
        String sessionUsername = SessionManager.getInstance().getUsername();
        UserDB userDB = new UserDB();
        int userId = userDB.getUserId(sessionUsername);
        withdraw userWithdraw = WithdrawDB.getWithdrawInfo(userId);
        System.out.println(userId);
        System.out.println(userWithdraw);
        if (userWithdraw != null) {
            bankaccount_number.setText(userWithdraw.getAccountNumber());
            bankaccount_firstname.setText(userWithdraw.getAccountFName());
            bankaccount_lastname.setText(userWithdraw.getAccountLName());
            bankaccount_bank.setText(userWithdraw.getBankName());
        } else {
            System.out.println("Not found data");
        }
    }

    @FXML
    public void saveChangeOnPaymentEdit(ActionEvent event) {
        String bankFName = bankaccount_firstname.getText();
        String bankLName = bankaccount_lastname.getText();
        String acctNumber = bankaccount_number.getText();
        String bankname = bankaccount_bank.getText();

        if (bankFName.isEmpty() || bankLName.isEmpty() || acctNumber.isEmpty() || bankname.isEmpty()) {
            showAlert("Update Failed", "Please complete all fields.", Alert.AlertType.WARNING);
            return;
        }
        if (!(wd == null) && (db.updateWithdrawInfo(acctNumber, bankFName, bankLName, bankname, userID,wd))) {
//            System.out.println("Successfully updated");
            showAlert("Success", "Update payment successfully!", Alert.AlertType.INFORMATION);
            editPayment();
        } else if ((wd == null) &&(db.saveToDB(acctNumber, bankFName, bankLName, bankname, userID, wd))) {
//            System.out.println("Successfully Insert");
            showAlert("Success", "Insert payment successfully!", Alert.AlertType.INFORMATION);
            reloadPage(event);
            editPayment();
        } else {
//            System.out.println("Sorry");
            showAlert("Error", "Sorry, something went wrong!", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void cancelChangeOnPaymentEdit(ActionEvent event){
        editPayment();
        setWithdrawInfo();
    }

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
            editPassword();
        } else {
            showAlert("Error", "Sorry, something went wrong!", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void cancelPassword(ActionEvent event){
        editPassword();
    }

    public void applyTransition(Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(200), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void reloadPage(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Teacher/setting/setting.fxml"));
            Parent root = loader.load();

            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
