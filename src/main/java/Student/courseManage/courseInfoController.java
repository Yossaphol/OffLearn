package Student.courseManage;

import Database.UserDB;
import Student.HomeAndNavigation.Navigator;
import Teacher.courseManagement.CourseItem;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class courseInfoController implements Initializable {
    public Label courseName;
    public Rectangle course_image;
    public Circle teacher_pic;
    public Label teacherName;
    Navigator nav = new Navigator();
    UserDB userDB = new UserDB();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void route(){
        Navigator nav = new Navigator();
        //continue_task.setOnMouseClicked(nav::taskRoute);
    }

    public void setCourseInformation(CourseItem course){
        courseName.setText(course.getCourseName());
        teacherName.setText(course.getTeacherName());
        setProfileCourse(course.getCourseImg());
        setProfile(userDB.getUserNameProfileAndSpecByCourseID(course.getCourseId())[1]);
    }
    public void setProfile(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }
        this.teacher_pic.setStroke(Color.TRANSPARENT);
        this.teacher_pic.setFill(new ImagePattern(img));
    }
    public void setProfileCourse(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }
        this.course_image.setStroke(Color.TRANSPARENT);
        this.course_image.setFill(new ImagePattern(img));
    }

//    public void courseLink(int quizID,int chapterID, javafx.scene.control.Button taskBtn){
//        taskBtn.setOnMouseClicked(e->nav.navigateTo("/fxml/Student/learningPage/learningPage.fxml", chapterID,quizID));
//    }

}
