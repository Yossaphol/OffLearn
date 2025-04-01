package Student.Offline;

import Utili.OfflineCourseData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class offlineCourseCardController {
    @FXML private Label courseCategory;
    @FXML private Label courseTitle;
    @FXML private Label courseSubtitle;
    @FXML private HBox courseCard;

    private OfflineCourseData data;

    public void setCourse(OfflineCourseData data) {
        this.data = data;
        this.courseTitle.setText(data.getCourseName());
        this.courseCategory.setText(data.getCourseCategory());
        this.courseSubtitle.setText(data.getCourseDescription());

    }

    public void setOnClickHandler(EventHandler<MouseEvent> handler) {
        courseCard.setOnMouseClicked(handler);
    }

    public OfflineCourseData getCourseData() {
        return data;
    }
}
