package Teacher.setting;

import Database.User;
import Database.UserDB;
import Student.HomeAndNavigation.HomeController;
import a_Session.SessionManager;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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
    public VBox notification_container;
    public Label notification_header;
    public Label notification_quiz_header;
    public RadioButton notification_quiz_y;
    public RadioButton notification_quiz_n;
    public Label notification_message_header;
    public RadioButton notification_message_y;
    public RadioButton notification_message_n;
    public Label notification_startingcourse_header;
    public RadioButton notification_startingcourse_y;
    public RadioButton notification_startingcourse_n;
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
    public Button cancel_button;
    public Button save_button;
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

    private boolean isPasswordEditing = false;
    private boolean isPaymentEditing = false;

    HomeController ef = new HomeController();

    UserDB userDB = new UserDB();
    String sessionUsername = SessionManager.getInstance().getUsername();
    User user = userDB.getUserInfo(sessionUsername);

    String firstname = user.getFirstname();
    String lastNameUser = user.getLastname();
    String userName = user.getUsername();
    String gmailUser = user.getEmail();
    //    String imgPath = (user.getProfile() != null) ? user.getProfile().toString() : "/img/Profile/user.png";
    String selectedimg = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();

        security_password.setText("********");


        setEffect();
        uploadPic.setVisible(false);
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
        }else{
            //Visible
            saveCancelProfileBtn.setVisible(false);
            uploadPic.setVisible(false);
            editBtn.setVisible(true);

            //set Textfield
            privateinfo_firstname.setDisable(true);
            privateinfo_lastname.setDisable(true);
            privateinfo_gmail.setDisable(true);

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
        }else{
            //Visible
            saveCancelProfileBtn.setVisible(false);
            uploadPic.setVisible(false);
            editBtn.setVisible(true);

            //set Textfield
            privateinfo_firstname.setDisable(true);
            privateinfo_lastname.setDisable(true);
            privateinfo_gmail.setDisable(true);

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
        editProfile();
    }


    @FXML
    private void cancelChangeOnPaymentEdit(ActionEvent event){
        editPayment();
    }


    @FXML
    private void saveProfileEdit(ActionEvent event){

        editPassword();
    }


    public void uploadImg(ActionEvent event) {
        //
    }


    public void applyTransition(Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(200), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

}
