package Student.dashboard;

import Database.User;
import Database.UserDB;
import a_Session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class dashboardProfileEditController implements Initializable {
    @FXML
    public Label setfullname;
    public Rectangle userpfp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userName = SessionManager.getInstance().getUsername();
        UserDB userDB = new UserDB();

        User user = userDB.getUserInfo(userName);

        setProfile(userDB.getProfile(userName));

        String name = user.getFirstname();
        String lastNameUser = user.getLastname();
        String fullname = name + " " + lastNameUser;
        setfullname.setText(fullname);
    }

    public void setProfile(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        this.userpfp.setStroke(Color.TRANSPARENT);
        this.userpfp.setFill(new ImagePattern(img));
    }
}
