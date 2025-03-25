package Student.dashboard;

import Database.User;
import Database.UserDB;
import a_Session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class dashboardProfileEditController implements Initializable {
    @FXML
    public Label setfullname;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDB userDB = new UserDB();
        String sessionUsername = SessionManager.getInstance().getUsername();
        User user = userDB.getUserInfo(sessionUsername);

        String name = user.getFirstname();
        String lastNameUser = user.getLastname();
        String fullname = name + " " + lastNameUser;
        setfullname.setText(fullname);
    }
}
