package Student.leaderboard;

import Student.HomeAndNavigation.HomeController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class studentNodeController implements Initializable {
    public Label sequence;
    public Circle profilePic;
    public Label score;
    public Label name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDetail(int order, String std_name, int std_score, String pictureURL){
        sequence.setText(String.valueOf(order));
        name.setText(std_name);
        score.setText(String.valueOf(std_score));
        setProfile(pictureURL);
    }

    public void setProfile(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        this.profilePic.setStroke(Color.TRANSPARENT);
        this.profilePic.setFill(new ImagePattern(img));
    }

}
