package Teacher.dashboard;

import Teacher.navigator.Navigator;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
import Student.HomeAndNavigation.HomeController;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class dashboardProfileController implements Initializable {

    public Rectangle user_pfp;
    public Label name;
    public Label bio;
    public Label total_review;
    public Button like_btn;
    public Button dislike_btn;
    public Button courseAmount;
    public Button editProfile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    route();
    }

    private void route(){
        Navigator nav = new Navigator();
        editProfile.setOnMouseClicked(nav::settingRoute);
    }

    public void setProfileDetail(String username, String description, String profilePicPath, int likeCount, int disLikeCount, int courseCount){
        name.setText(username);
        bio.setText(description);

        HomeController hm = new HomeController();
        hm.loadAndSetImage(user_pfp, profilePicPath);
        dislike_btn.setText(String.valueOf(disLikeCount));
        like_btn.setText(String.valueOf(likeCount));
        courseAmount.setText(courseCount+" คอร์ส");
    }
}
