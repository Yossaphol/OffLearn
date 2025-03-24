package Teacher.courseManagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
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

    private VBox courseEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();
        newCourseButton();
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


    public void newCourseButton(){
        newCourse.setOnAction(actionEvent -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseEdit.fxml"));
                courseEdit = fxmlLoader.load();
                wrapper.setContent(courseEdit);

                CourseEditController courseEditController = fxmlLoader.getController();

                passWrapper(courseEditController);
                passCourseManagement(courseEditController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
