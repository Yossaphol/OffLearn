package Student.Setting;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    HomeController hm = new HomeController();


    //Default value (temporary use น้า)
    String name = "Wiraya";
    String lastNameUser = "Boonpriam";
    String userName = "Wiraya606";
    String gmailUser = "wirayabovorn606@gmail.com";
    String imgPath = "/img/Profile/user.png";
    String selectedimg = "";


    private boolean isEditing = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        uploadPic.setVisible(false);
        setProfileValue(name, lastNameUser, userName, gmailUser);
        loadAndSetUserImage(picture, imgPath);

    }



    public void editProfile(ActionEvent e){
        if(!isEditing){

            realName.setDisable(isEditing);
            lastName.setDisable(isEditing);
            username.setDisable(isEditing);
            gmail.setDisable(isEditing);
            editProfile.setVisible(isEditing);

            isEditing = true;
            openEditProfile(true);
        }
        else{
            closeProfileEdit();
        }
    }

    public void openEditProfile(Boolean isEditing){
        editingProfileBtnContainer.setVisible(isEditing);
        realName.setEditable(isEditing);
        lastName.setEditable(isEditing);
        username.setEditable(isEditing);
        gmail.setEditable(isEditing);
        uploadPic.setVisible(isEditing);
    }

    public void closeEditProfile(Boolean isEditing){
        realName.setEditable(isEditing);
        lastName.setEditable(isEditing);
        username.setEditable(isEditing);
        gmail.setEditable(isEditing);
        uploadPic.setVisible(isEditing);
    }

    public void setProfileValue(String Name, String LastName, String Username, String Gmail){
        this.name = Name;
        this.lastNameUser = LastName;
        this.userName = Username;
        this.gmailUser = Gmail;

        _setProfileValue(name, lastNameUser, userName, gmailUser);
    }

    public void _setProfileValue(String Name, String LastName, String Username, String Gmail){
        realName.setText(Name);
        lastName.setText(LastName);
        username.setText(Username);
        gmail.setText(Gmail);
    }


    public void saveProfileEdit(ActionEvent e){
        imgPath = selectedimg;
        setProfileValue(realName.getText(), lastName.getText(), username.getText(), gmail.getText());
        closeProfileEdit();
    }

    public void cancelProfileEdit(ActionEvent e){
        loadAndSetUserImage(picture, imgPath);
        setProfileValue(name, lastNameUser, userName, gmailUser);
        closeProfileEdit();
    }

    public void closeProfileEdit(){
        realName.setDisable(isEditing);
        lastName.setDisable(isEditing);
        username.setDisable(isEditing);
        gmail.setDisable(isEditing);
        editProfile.setVisible(isEditing);
        editingProfileBtnContainer.setVisible(false);
        isEditing = false;
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


}
