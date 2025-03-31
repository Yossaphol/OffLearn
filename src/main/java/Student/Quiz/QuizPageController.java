package Student.Quiz;

import Database.*;
import Student.HomeAndNavigation.Navigator;
import Teacher.quiz.QuestionItem;
import Teacher.quiz.QuizItem;
import a_Session.SessionHandler;
import a_Session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuizPageController implements Initializable, SessionHandler {

    @FXML
    private VBox questionSpace;

    @FXML
    private Button sendButton;

    @FXML
    private Label name;

    @FXML
    private Label min;

    @FXML
    private Label max;

    @FXML
    private Label level;

    private int quizId;
    private ArrayList<QuestionItem> questionItemsList;
    private QuestionDB questionDB;
    private ArrayList<QuestionCardController> questionCardControllersList;
    private int point;
    private Navigator navigator;
    private ScoreDB scoreDB;
    private int chapterID;
    private int userID;
    private ChapterDB chapterDB;
    private CourseDB courseDB;
    private QuizDB quizDB;
    private QuizItem quizItem;
    private String userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionCardControllersList = new ArrayList<QuestionCardController>();
        loadQuiz();
        setSendButton();
        handleSession();
    }

    @Override
    public void handleSession() {
        this.userName = SessionManager.getInstance().getUsername();

        UserDB userDB = new UserDB();
        this.userID = userDB.getUserId(userName);
    }

    public void setQuizDesc(){
        this.name.setText(quizItem.getHeader());
        this.min.setText(quizItem.getMinScore() + " คะแนน");
        this.max.setText(quizItem.getMaxScore() + " คะแนน");
        this.level.setText(quizItem.getLevel());
    }

    public void loadQuiz(){
        questionDB = new QuestionDB();
        questionItemsList = questionDB.getQuestionsByQuizID(quizId);

        for (QuestionItem q : questionItemsList){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Student/Quiz/questionCard.fxml"));
                VBox questionCard = fxmlLoader.load();

                QuestionCardController questionCardController = fxmlLoader.getController();

                questionCardController.setQuestionItem(q);
                questionCardController.setSendButton(sendButton);
                questionCardController.setDisplay();

                questionCardControllersList.add(questionCardController);
                questionSpace.getChildren().add(questionCard);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSendButton(){
        navigator = new Navigator();
        scoreDB = new ScoreDB();
        courseDB = new CourseDB();
        quizDB = new QuizDB();

        quizItem = quizDB.getQuizById(quizId);

        sendButton.setOnAction(actionEvent -> {
            this.point = 0;
            boolean allAnswered = true;

            for (QuestionCardController q : questionCardControllersList){
                q.checkAnswer();
                if (q.getPoint() == -1) {
                    allAnswered = false;
                } else {
                    this.point += q.getPoint();
                }
            }
            if (!allAnswered) {
                showAlert("คำตอบไม่ครบ", "กรุณาตอบคำถามให้ครบทุกข้อก่อนส่ง", Alert.AlertType.ERROR);
                return;
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("ยืนยันการส่งคำตอบ");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("ท่านต้องการส่งคำตอบใช่หรือไม่?");

            if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                navigator.QuizResult(point, courseDB.getCourseIDByChapterID(chapterID), quizItem, chapterID);
                scoreDB.saveToDB(courseDB, userID, chapterID, point, quizId);
                System.out.println("" + point);
            } else {
                System.out.println("ยกเลิกการส่งคำตอบ");
            }
        });
    }


    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setQuizId(int quizId){
        this.quizId = quizId;
    }

    public void setChapterID(int chapterID){
        this.chapterID = chapterID;
    }


}
