package Teacher.dashboard;

import Database.CourseDB;
import Database.User;
import Database.UserDB;
import a_Session.SessionHadler;
import a_Session.SessionManager;
import Teacher.navigator.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
import Student.HomeAndNavigation.HomeController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class dashboardProfileController implements Initializable, SessionHadler {

    @FXML
    private Rectangle user_pfp;

    @FXML
    private Label setfullname;

    @FXML
    private Label bio;

    @FXML
    private Label total_review;

    @FXML
    private Button like_btn;

    @FXML
    private Button dislike_btn;

    @FXML
    private Button courseAmount;

    @FXML
    private Button editProfile;

    @FXML
    private Label countMyCourse;

    private UserDB userDB;
    private CourseDB courseDB;
    private int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        route();

        UserDB userDB = new UserDB();
        String sessionUsername = SessionManager.getInstance().getUsername();
        User user = userDB.getUserInfo(sessionUsername);

        String name = user.getFirstname();
        String lastNameUser = user.getLastname();
        String fullname = name + "  " + lastNameUser;
        setfullname.setText(fullname);

        setProfile(userDB.getProfile(sessionUsername));
    }

    private void route(){
        Navigator nav = new Navigator();
        editProfile.setOnMouseClicked(nav::settingRoute);
    }

    public void setProfileDetail(String username, String description, String profilePicPath, int likeCount, int disLikeCount, int courseCount){

        userDB = new UserDB();
        courseDB = new CourseDB();
        userId = userDB.getUserId(SessionManager.getInstance().getUsername());
        int count = courseDB.countMyCourses(userId);
//        setfullname.setText(username);
        bio.setText(description);

        HomeController hm = new HomeController();
//        hm.loadAndSetImage(user_pfp, profilePicPath);
        dislike_btn.setText(String.valueOf(disLikeCount));
        like_btn.setText(String.valueOf(likeCount));
        countMyCourse.setText(count + "");
    }

    public void setProfile(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        this.user_pfp.setStroke(Color.TRANSPARENT);
        this.user_pfp.setFill(new ImagePattern(img));
    }

    @Override
    public void handleSession() {

    }
}
