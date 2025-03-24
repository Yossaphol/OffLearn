package Teacher.courseManagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class CourseListController implements Initializable {
    @FXML
    private Label courseName;

    @FXML
    private Rectangle courseImg;

    @FXML
    private Label coursePrice;

    @FXML
    private Label courseCat;

    private CourseItem courseItem;

    @FXML
    private Label price;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setCourseItem(CourseItem courseItem){
        this.courseItem = courseItem;
    }

    public void setCourseDisplay(){
        courseName.setText(courseItem.getCourseName());
        courseCat.setText(courseItem.getCourseCat());
        coursePrice.setText(courseItem.getCoursePrice() + "");
        this.setImageToRectangle(courseItem.getCourseImg());
    }

    public void setImageToRectangle(String imageUrl) {
        Image image = new Image(imageUrl);
        courseImg.setFill(new ImagePattern(image));
    }


}
