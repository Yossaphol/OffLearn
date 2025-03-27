package Teacher.quizDetail;

import Teacher.courseManagement.Course;
import Teacher.courseManagement.CourseController;
import Teacher.courseManagement.CourseEditController;
import Teacher.quiz.QuizBoxContent;
import Teacher.quiz.QuizBoxItem;
import Teacher.quiz.QuizItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizDetailController implements Initializable {

    @FXML
    private HBox quiz_container;

    @FXML
    private Label back;

    @FXML
    private Label quizName;

    @FXML
    private Label min;

    @FXML
    private Label max;

    @FXML
    private Label count;

    @FXML
    private HBox detailBox;

    @FXML
    private BubbleChart quiz_chart;

    @FXML
    private Label quiz_score_top;

    @FXML
    private Label quiz_score_avg;

    @FXML
    private Label quiz_score_min;

    @FXML
    private Label quiz_score_std;


    private ScrollPane wrapper;
    private VBox courseManagement;
    private CourseController courseController;
    private int courseID;
    private QuizBoxItem quizBoxItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shadow();
        backButton();
    }

    public void shadow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(2.5);
        dropShadow.setOffsetY(2.5);
        dropShadow.setColor(Color.GRAY);

        detailBox.setEffect(dropShadow);
    }

    public void backButton(){
        back.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseEdit.fxml"));
                VBox courseEdit = fxmlLoader.load();

                CourseEditController courseEditController = fxmlLoader.getController();
                wrapper.setContent(courseEdit);

                courseEditController.loadMyCourse(courseID, wrapper);

                passWrapper(courseEditController);
                passCourseManagement(courseEditController);
                passMethodCourseController(courseEditController);

                wrapper.setVvalue(0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setDetail(QuizBoxItem quizBoxItem){
        this.quizName.setText(quizBoxItem.getName());
        this.min.setText(quizBoxItem.getMinScore() + "");
        this.max.setText(quizBoxItem.getMaxScore() + "");
        this.count.setText(quizBoxItem.getQuestionCount() + "");
    }

    public void recieveWrapper(ScrollPane wrapper){
        this.wrapper = wrapper;
    }

    public void recieveQuizBoxItem(QuizBoxItem quizBoxItem){ this.quizBoxItem = quizBoxItem;}

    public void recieveCourseId(int courseID){ this.courseID = courseID; }

    public void recieveCourseController(CourseController courseController) { this.courseController = courseController;}

    public void recieveCourseMangement(VBox courseManagement){ this.courseManagement = courseManagement; }

    public void passWrapper(CourseEditController courseEditController){ courseEditController.recieveWrapper(wrapper);}

    public void passCourseManagement(CourseEditController courseEditController){ courseEditController.recieveCourseManagement(courseManagement);}

    public void passMethodCourseController(CourseEditController courseEditController){ courseEditController.recieveMethod(courseController);}
}
