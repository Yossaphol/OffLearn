package Teacher.courseManagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class CourseListController implements Initializable {
    @FXML
    private HBox mainComp;

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
        shadow();
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
        courseImg.setStroke(Color.TRANSPARENT);
        courseImg.setArcHeight(10);
        courseImg.setArcWidth(10);
    }

    public void shadow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(2.5);
        dropShadow.setOffsetY(2.5);
        dropShadow.setColor(Color.web("#c4c4c4", 0.25));

        mainComp.setEffect(dropShadow);
    }


}
