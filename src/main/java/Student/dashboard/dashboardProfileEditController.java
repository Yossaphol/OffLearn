package Student.dashboard;

import Database.*;
import a_Session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class dashboardProfileEditController implements Initializable {
    @FXML
    public Label setfullname;
    public Rectangle userpfp;
//    public Label numRanks;
    public Label points;

    private final UserDB userDB = new UserDB();
    private final ScoreDB scoreDB = new ScoreDB();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userName = SessionManager.getInstance().getUsername();
        User user = userDB.getUserInfo(userName);
        int userId = userDB.getUserId(userName);

        if (user != null) {
            setProfile(userDB.getProfile(userName));
            setfullname.setText(user.getFirstname() + " " + user.getLastname());
        }

        updatePoints(userId);
    }

    public void setProfile(String Url) {
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }
        this.userpfp.setStroke(Color.TRANSPARENT);
        this.userpfp.setFill(new ImagePattern(img));
    }

    private void updatePoints(int userID) {
        int totalPoints = scoreDB.getTotalScore(userID);
        points.setText(String.valueOf(totalPoints));
    }


}