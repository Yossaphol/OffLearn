package Teacher.courseManagement;

import Database.CourseDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    @FXML
    private ImageView delete;

    private CourseItem courseItem;

    @FXML
    private Label price;

    private VBox courseListView;
    private VBox courseEdit;
    private ScrollPane wrapper;
    private VBox courseManagement;
    private int courseId;
    private CourseController courseController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shadow();
        editcourseMyCourse();
        deleteButton();
    }

    public void setCourseItem(CourseItem courseItem){
        this.courseItem = courseItem;
    }

    public void setCourseDisplay(int enrollCount){
        courseName.setText(courseItem.getCourseName());
        courseCat.setText(courseItem.getCourseCat());
        coursePrice.setText(enrollCount + "");
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

    public void setCourseId(int courseId){
        this.courseId = courseId;
    }

    public int getCourseId(){
        return this.courseId;
    }

    public void editcourseMyCourse(){
        mainComp.setOnMouseClicked(mouseEvent -> {
            System.out.println(this.getCourseId());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseEdit.fxml"));
                courseEdit = fxmlLoader.load();
                wrapper.setContent(courseEdit);

                CourseEditController courseEditController = fxmlLoader.getController();

                courseEditController.loadMyCourse(this.getCourseId(), wrapper);


                passWrapper(courseEditController);
                passCourseManagement(courseEditController);
                passMethodCourseController(courseEditController);
                wrapper.setVvalue(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteButton(){
        delete.setOnMouseClicked(mouseEvent -> {
            CourseDB courseDB = new CourseDB();
            if (courseDB.deleteFromDB(this.getCourseId())){
                System.out.println("Delete course from database complete");
            }

            courseListView.getChildren().remove(this);
            courseController.showCourseOnListview();
        });
    }

    public void recieveWrapper(ScrollPane wrapper){this.wrapper = wrapper;}

    public void recieveCourseManagement(VBox courseManagement){this.courseManagement = courseManagement;}

    public void passWrapper(CourseEditController courseEditController){
        courseEditController.recieveWrapper(wrapper);
    }

    public void recieveMethod(CourseController courseController){this.courseController = courseController;}

    public void recieveCourseListView(VBox courseListView){
        this.courseListView = courseListView;
    }

    public void passCourseManagement(CourseEditController courseEditController){
        courseEditController.recieveCourseManagement(courseManagement);
    }

    public void passMethodCourseController(CourseEditController courseEditController){courseEditController.recieveMethod(courseController);}


}
