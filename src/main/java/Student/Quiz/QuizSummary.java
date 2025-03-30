package Student.Quiz;

import Database.QuestionDB;
import Student.HomeAndNavigation.Navigator;
import Teacher.quiz.QuestionItem;
import Teacher.quiz.QuizItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuizSummary implements Initializable {

    @FXML
    private Button complete;

    @FXML
    private VBox questionSpace;

    @FXML
    private Label score;

    @FXML
    private Label quizName;

    private ArrayList<QuestionItem> questionItemsList;
    private int quizId;
    private Navigator navigator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComplete();
    }

    public void loadAnwer(int point , QuizItem quizItem, int quizId){
        QuestionDB questionDB = new QuestionDB();
        this.quizId = quizId;
        questionItemsList = questionDB.getQuestionsByQuizID(quizId);

        for (QuestionItem q : questionItemsList){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Student/Quiz/questionCard.fxml"));
                VBox questionCard = fxmlLoader.load();

                QuestionCardController questionCardController = fxmlLoader.getController();

                questionCardController.setQuestionItem(q);
                questionCardController.setDisplay(q.getCorrectChoice());

                questionSpace.getChildren().add(questionCard);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.score.setText(point + "/" + quizItem.getMaxScore() + " คะแนน");
        this.quizName.setText(quizItem.getHeader());
    }

    public void setComplete(){
        navigator = new Navigator();
        complete.setOnAction(actionEvent -> navigator.myCourseRoute(actionEvent));
    }



}
