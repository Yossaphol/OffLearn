package Teacher.courseManagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseListInDash implements Initializable {

    @FXML
    private Label courseName;

    @FXML
    private Label courseCat;

    @FXML
    private Label courseIncome;

    @FXML
    private Rectangle courseImg;

    @FXML
    private HBox mainComp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shadow();
    }

    public void setDisplay(String img, String name, String cat, int income){
        this.courseName.setText(name);
        this.courseCat.setText(cat);
        this.courseIncome.setText(income + "");
        this.setImageToRectangle(img);
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
