package Student.Setting;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
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
    public Button savePayment;
    public Button cancelPaymentChange;
    private boolean isChangePass = false;
    HomeController hm = new HomeController();
    private boolean isEditingPayment = false;


    //Default value (temporary use น้า)
    String name = "Wiraya";
    String lastNameUser = "Boonpriam";
    String userName = "Wiraya606";
    String gmailUser = "wirayabovorn606@gmail.com";
    String imgPath = "/img/Profile/user.png";
    String selectedimg = "";

    //Payment value (temporary use น้า)
    String namep = "Wirayabovorn";
    String lastnamep = "Boonpriam";
    String accountN = "1313880423";
    String bank = "KBANK";

    private boolean isEditing = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        uploadPic.setVisible(false);
        setProfileValue(name, lastNameUser, gmailUser);
        loadAndSetUserImage(picture, imgPath);
        username1.setText(userName);
        setRadioGroup();
        setBankDetail(namep, lastnamep, accountN, bank);

        setEffect();
    }

    private void setEffect(){
        hm.hoverEffect(uploadPic);
        hm.hoverEffect(saveEditProfile);
        hm.hoverEffect(cancelEditProfile);
        hm.hoverEffect(changePasswordBtn);
        hm.hoverEffect(savePassword);
        hm.hoverEffect(cancelEditProfile);
        hm.hoverEffect(editPaymentBtn);
        hm.hoverEffect(savePayment);
        hm.hoverEffect(cancelPaymentChange);
    }

    private void setRadioGroup(){
        ToggleGroup quizGroup = new ToggleGroup();
        quizTrue.setToggleGroup(quizGroup);
        quizFalse.setToggleGroup(quizGroup);


        ToggleGroup messageGroup = new ToggleGroup();
        mTrue.setToggleGroup(messageGroup);
        mFalse.setToggleGroup(messageGroup);

        ToggleGroup upComing = new ToggleGroup();
        cTrue.setToggleGroup(upComing);
        cFalse.setToggleGroup(upComing);


        //Set default
        quizTrue.setSelected(true);
        mTrue.setSelected(true);
        cTrue.setSelected(true);
    }


    //Start profile edit part
    public void editProfile(ActionEvent e){
        if(!isEditing){

            realName.setDisable(isEditing);
            lastName.setDisable(isEditing);
            gmail.setDisable(isEditing);
            editProfile.setVisible(isEditing);

            isEditing = true;
            openEditProfile(true);
        }
        else{
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
        imgPath = selectedimg;
        setProfileValue(realName.getText(), lastName.getText(), gmail.getText());
        closeProfileEdit();
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

        if (selectedFile != null) {
            selectedimg = selectedFile.getAbsolutePath();
            loadAndSetUserImage(picture, selectedimg);
        }
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

    public void editPaymentInfo(ActionEvent e){
        if(!isEditingPayment){
            openEditPaymentField();

            isEditingPayment = true;
        }else {
            closeEditPaymentField();
            isEditingPayment = false;
        }
    }



    private void openEditPaymentField() {
        editPaymentBtn.setVisible(false);

        editPaymentBox.setOpacity(0);
        editPaymentBox.setVisible(true);
        name1.setDisable(false);
        lastName1.setDisable(false);
        accountNo.setDisable(false);
        bankName.setDisable(false);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), editPaymentBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private void closeEditPaymentField() {
        editPaymentBtn.setVisible(true);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), editPaymentBox);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> {
            editPaymentBox.setVisible(false);

            name1.setDisable(true);
            lastName1.setDisable(true);
            accountNo.setDisable(true);
            bankName.setDisable(true);
        });

        fadeOut.play();
    }

    public void savePaymentinfo(ActionEvent e){
        this.namep = name1.getText();
        this.lastnamep = lastName1.getText();
        this.accountN = accountNo.getText();
        this.bank = bankName.getText();

        setBankDetail(namep, lastnamep, accountN, bank);
        closeEditPaymentField();
    }


    public void cancelPaymentInfo(ActionEvent e){
        setBankDetail(namep, lastnamep, accountN, bank);
        closeEditPaymentField();
    }

    public void setBankDetail(String name, String lastname, String acctNo, String bank) {
        acctNo = acctNo.replace("-", "");
        name1.setText(name);
        lastName1.setText(lastname);

        if (acctNo.length() >= 6) {
            String formattedAcctNo = acctNo.substring(0, 3) + "-" +
                    acctNo.substring(3, 6) + "-" +
                    acctNo.substring(6);
            accountNo.setText(formattedAcctNo);
        } else {
            accountNo.setText(acctNo);
        }
        bankName.setText(bank);
    }
    //End edit payment part

}
