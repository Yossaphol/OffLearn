package Student.myCourse;

import Student.learningPage.learningPageController;
import Student.mainPage.mainPageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class courseCardController implements Initializable {
    @FXML
    private Rectangle courseImage;
    @FXML
    private Label courseCategory;
    @FXML
    private Label courseTitle;
    @FXML
    private Label courseSubtitle;
    @FXML
    private ProgressBar courseProgress;
    @FXML

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setCourseImage(String image) {
        Image img = new Image(image);
        this.courseImage.setStroke(Color.TRANSPARENT);
        this.courseImage.setFill(new ImagePattern(img));
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory.setText(courseCategory);
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle.setText(courseTitle);
    }

    public void setCourseSubtitle(String courseSubtitle) {
        this.courseSubtitle.setText(courseSubtitle);
    }

    public void setCourseProgress(double courseProgress) {
        this.courseProgress.setProgress(courseProgress/100);
    }
}
