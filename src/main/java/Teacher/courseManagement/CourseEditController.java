package Teacher.courseManagement;

import Student.FontLoader.FontLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseEditController implements Initializable {

    @FXML
    private VBox searhbar_container;


    @FXML
    private Label addCourse;

    @FXML
    private VBox courseSpace;

    private HBox newCourse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();
        displayNavbar();

        addCourseButton();
    }

    @FXML
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCourseButton(){
        addCourse.setOnMouseClicked(mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseContent.fxml"));
            try {
                newCourse = fxmlLoader.load();
                VBox.setVgrow(newCourse, Priority.ALWAYS);
                courseSpace.getChildren().add(newCourse);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
;