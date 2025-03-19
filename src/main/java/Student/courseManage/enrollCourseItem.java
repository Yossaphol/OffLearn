package Student.courseManage;

import Student.HomeAndNavigation.HomeController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class enrollCourseItem implements Initializable {
    public Rectangle course_image;
    public Circle teacher_pic;
    public Label courseVal;
    public ProgressBar course;
    public VBox container;
    public Label courseName;
    public Label shortDescription;
    public Label teacherName;
    HomeController hm = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    setEffect();
    }

    private void setEffect(){
        hm.hoverEffect(container);
    }




    public void setCourseName(String name){
        courseName.setText(name);
    }

    public void setShortDescription(String t){
        shortDescription.setText(t);
    }

    public void setTeacherName(String name){
        teacherName.setText(name);
    }

    public void setTeacherImg(String path){
        hm.loadAndSetImage(teacher_pic, path);
    }

    public void setCourseImg(String path){
        hm.loadAndSetImage(course_image, path);
    }


}