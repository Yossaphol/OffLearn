package Student.Offline;

import Utili.OfflineCourseData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class offlineCourseCardController {
    @FXML private Label courseCategory;
    @FXML private Label courseTitle;
    @FXML private Label courseSubtitle;
    @FXML private HBox courseCard;
    @FXML private Rectangle courseImage;

    private OfflineCourseData data;

    public void setCourse(OfflineCourseData data) {
        this.data = data;
        this.courseTitle.setText(data.getCourseName());
        this.courseCategory.setText(data.getCourseCategory());
        this.courseSubtitle.setText(data.getCourseDescription());
        String imgPath = getRandomOfflineImage();
        Image image = new Image(getClass().getResourceAsStream(imgPath));
        ImagePattern pattern = new ImagePattern(image);
        courseImage.setFill(pattern);

    }

    public void setOnClickHandler(EventHandler<MouseEvent> handler) {
        courseCard.setOnMouseClicked(handler);
    }

    public OfflineCourseData getCourseData() {
        return data;
    }

    private static final String[] randomimg = {
            "/img/Picture/a3ea0f6ea140c07de2d150b8e7e48826.jpg",
            "/img/Picture/bg.jpg",
            "/img/Picture/florian-olivo-4hbJ-eymZ1o-unsplash.jpg",
            "/img/Picture/huh.jpg",
            "default_course_pic"
    };

    private String getRandomOfflineImage() {
        int index = (int) (Math.random() * randomimg.length);
        return randomimg[index];
    }

}
