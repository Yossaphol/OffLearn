package Student.myCourse;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class courseCardController {
    public HBox courseCard;
    public Rectangle courseImage;
    public Label courseCategory;
    public Label courseTitle;
    public Label courseSubtitle;
    public ProgressBar courseProgress;

    public void setCourseCard(HBox courseCard) {
        this.courseCard = courseCard;
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
