package Teacher.courseManagement;

import Student.FontLoader.FontLoader;
import Teacher.experiment.QuizController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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

    @FXML
    private Label addQuiz;

    @FXML
    private ScrollPane wrapper;

    @FXML
    private VBox courseManagement;

    private HBox newCourse;
    private VBox newQuiz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();
        displayNavbar();

        addCourseButton();
        addQuizButton();
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
                e.printStackTrace();
            }
        });
    }

    public void passWrapper(QuizController quizController){
        quizController.recieveWrapper(wrapper);
    }

    public void passCourseManagement(QuizController quizController){
        quizController.recieveCourseManagement(courseManagement);
    }

    public void addQuizButton() {
        addQuiz.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/Quiz.fxml"));
                VBox quizContent = fxmlLoader.load();
                wrapper.setContent(quizContent);

                QuizController quizController = fxmlLoader.getController();

                passCourseManagement(quizController);
                passWrapper(quizController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
;