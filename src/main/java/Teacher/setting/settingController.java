package Teacher.setting;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    public TextField security_password;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();
    }
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            VBox navContent = calendarLoader.load();
            navBar.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
