package Teacher.courseManagement;

import Database.CourseDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CourseController implements Initializable {

    @FXML
    private HBox navBar;

    @FXML
    private Button newCourse;

    @FXML
    private ScrollPane wrapper;

    @FXML
    private VBox courseManagement;

    @FXML
    private VBox courseListView;

    @FXML
    private Label count;

    private VBox courseEdit;
    private CourseDB courseDB;
    private int userID = 00000001;
    private ArrayList<CourseItem> courseList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();
        newCourseButton();
        showCourseOnListview();
    }

    @FXML
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            navBar.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void passWrapper(CourseEditController courseEditController){
        courseEditController.recieveWrapper(wrapper);
    }

    public void passCourseManagement(CourseEditController courseEditController){
        courseEditController.recieveCourseList(courseManagement);
    }

    public void passWrapperMyCourse(CourseListController courseListController){
        courseListController.recieveWrapper(wrapper);
    }

    public void passCourseManagementMyCourse(CourseListController courseListController){
        courseListController.recieveCourseManagement(courseManagement);
    }

    public void passMyMethod(CourseEditController courseEditController){courseEditController.recieveMethod(this);}

    public void newCourseButton(){
        newCourse.setOnAction(actionEvent -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseEdit.fxml"));
                courseEdit = fxmlLoader.load();
                wrapper.setContent(courseEdit);

                CourseEditController courseEditController = fxmlLoader.getController();

                passWrapper(courseEditController);
                passCourseManagement(courseEditController);
                passMyMethod(courseEditController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showCourseOnListview() {
        courseListView.getChildren().clear();
        this.courseDB = new CourseDB();
        courseList = courseDB.getMyCourse(userID);

        count.setText(courseList.size() + "");

        if (courseList.isEmpty()) {
            Label noCourseLabel = new Label("ไม่มีหลักสูตรที่จะแสดง");
            courseListView.getChildren().add(noCourseLabel);
            return;
        }

        for (CourseItem c : courseList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseList.fxml"));
                Parent courseItemParent = fxmlLoader.load();

                CourseListController controller = fxmlLoader.getController();

                controller.setCourseItem(c);
                controller.setCourseDisplay();
                controller.setCourseId(c.getCourseId());

                passCourseManagementMyCourse(controller);
                passWrapperMyCourse(controller);

                courseListView.getChildren().add(courseItemParent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
