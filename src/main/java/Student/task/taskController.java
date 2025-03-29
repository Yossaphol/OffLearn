package Student.task;

import Database.QuestionDB;
import Database.QuizDB;
import Database.UserDB;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Teacher.quiz.QuizItem;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class taskController implements Initializable {
    public VBox navBar;
    public HBox searhbar_container;
    public HBox MainFrame;
    public TabPane tabPane;
    public Tab btnUpComing;
    public VBox containerUpComing;
    public VBox boxUpComing;
    public Tab btnLate;
    public VBox containerLate;
    public Tab btnComplete;
    public VBox containerComplete;
    public Label point;
    public Label taskDetail;
    public Label taskInformation;
    public Button task;
    public Label labelUpComingDate;
    public VBox boxUpComing1;
    public Label labelUpComingDate1;
    public Button task1;
    public Label taskInformation1;
    public Label taskDetail1;
    public Label point1;
    public VBox boxUpComing2;
    public Button task2;
    public Label taskInformation2;
    public Label taskDetail2;
    public Label point2;
    public Label labelLateDate;
    public Label labelDone;
    public Label labelUpComingDay;
    //พวกที่มีเลขตามหลังเช่น task1 point2 1 คือแท็บหน้าเลยกำหนด 2 คือแท็บหน้าเสร็จ ส่วนที่ไม่มีเลขคือแท็บกำลังจะมาถึง

    QuizDB dataDB = new QuizDB();
    ArrayList<QuizItem> quiz = (ArrayList<QuizItem>) dataDB.getAllThisUserQuiz();
    UserDB userDB = new UserDB();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setUpComing();
        setComplete();
        setTabAnimation();
    }

    private void setUpComing() {
        for (QuizItem task : quiz) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/taskCard.fxml"));
                Node quizItem = loader.load();

                taskCardController controller = loader.getController();
                controller.setTaskInformation(task.getHeader(), task.getLevel(), task.getMaxScore());

                Button content = (Button) quizItem;
                hoverEffect(content);
//                content.setOnMouseClicked(e ->
//                        handleQuizTestLink("TeacherD", task.getLevel(), task.getHeader(), 120, questionDB.countQuestionsByQuizID(task.getQuizID()))
//                );
                containerUpComing.getChildren().add(content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//    private void handleQuizTestLink(String teacherName, String hardness, String testName, int duration, int amountOfQuestion) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/test/preTest.fxml"));
//            Parent root = loader.load();
//
//            pre_testController ctrl = loader.getController();
//            ctrl.displayCard(teacherName, hardness, testName, duration, amountOfQuestion);
//
//            Navigator nav = new Navigator();
//            nav.navigateTo("/fxml/Student/test/preTest.fxml");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    private void setComplete(){
        //containerComplete
    }


    public void hoverEffect(Button btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.02);
        scaleUp.setToY(1.02);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.02);
        scaleDown.setFromY(1.02);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
        });
    }

    public void setTabAnimation() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null && newTab.getContent() != null) {
                newTab.getContent().getStyleClass().add("tab-content");

                newTab.getContent().setTranslateX(10);
                newTab.getContent().setOpacity(0);

                TranslateTransition slide = new TranslateTransition(Duration.millis(300), newTab.getContent());
                slide.setFromX(10);
                slide.setToX(0);

                Timeline opacityAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(newTab.getContent().opacityProperty(), 0)),
                        new KeyFrame(Duration.millis(500), new KeyValue(newTab.getContent().opacityProperty(), 1))
                );

                ParallelTransition animation = new ParallelTransition(slide, opacityAnimation);
                animation.play();
            }
        });
    }


}
