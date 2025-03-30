package Student.task;

import Database.QuizDB;
import Database.StudentScoreDB;
import Database.UserDB;
import Teacher.quiz.QuizItem;
import a_Session.SessionManager;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.ResourceBundle;

public class taskController implements Initializable {
    public VBox navBar;
    public HBox MainFrame;
    public TabPane tabPane;
    public Tab btnUpComing;
    public VBox containerUpComing;
    public Tab btnComplete;
    public VBox containerComplete;
    public Label point;
    public Button task;

    StudentScoreDB dataDB = new StudentScoreDB();
    QuizDB dtaDB = new QuizDB();
    String username = SessionManager.getInstance().getUsername();
    UserDB userDB = new UserDB();
    int userID = userDB.getUserId(username);
    ArrayList<QuizItem> undoneQuiz = (ArrayList<QuizItem>) dataDB.getUndoneQuizzes(userID);
    ArrayList<QuizItem> doneQuiz = (ArrayList<QuizItem>) dataDB.getFinishedQuizzes(userID);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpComing();
        setComplete();
        setTabAnimation();
    }

    private void setUpComing() {
        if(undoneQuiz.isEmpty()){
            Label txt = new Label("คุณไม่มีควิซที่ต้องทำในขณะนี้");
            txt.setStyle("-fx-font-size: 20px;");
            containerUpComing.getChildren().clear();
            containerUpComing.getChildren().add(txt);
            return;
        }
        containerUpComing.getChildren().clear();
        for (QuizItem task : undoneQuiz) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/taskCard.fxml"));
                Node quizItem = loader.load();

                taskCardController controller = loader.getController();
                controller.setTaskInformation(task.getHeader(), dtaDB.getCourseNameByQuizID(task.getQuizID()), task.getMaxScore());

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
        if(doneQuiz.isEmpty()){
            Label txt = new Label("เริ่มทำแบบทดสอบเลย!");
            txt.setStyle("-fx-font-size: 20px;");
            containerComplete.getChildren().clear();
            containerComplete.getChildren().add(txt);
            return;
        }
        containerComplete.getChildren().clear();
        for (QuizItem task : doneQuiz) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/taskCard.fxml"));
                Node quizItem = loader.load();

                taskCardController controller = loader.getController();
                Integer sc = dataDB.getStudentScore(userID, task.getQuizID());
                String score_ = sc+"/"+task.getMaxScore()+" คะแนน";
                controller.setTaskInformation(task.getHeader(), dtaDB.getCourseNameByQuizID(task.getQuizID()), score_);

                Button content = (Button) quizItem;
                hoverEffect(content);
                containerComplete.getChildren().add(content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
