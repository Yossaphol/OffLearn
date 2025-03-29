package Student.Quiz;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuizPageController implements Initializable {
    public VBox leftWrapper;
    public Label score;
    public Button finishBtn;
    public VBox problemContainer;

    HomeController ef = new HomeController();
    testPageController tp = new testPageController();

    List<Question> problemList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemList = tp.getQuestions(); //โจทย์ที่ดึงมาก
        if (problemList == null) {
            problemList = new ArrayList<>();
        }

        setScore(67, 100);
        route();
        setEffect();

        //ตัวอย่างโจทย์
        problemList.add(new Question(1, "What is Love?", "Chemical reaction", "Feeling", "Money", "Understanding each other", "ผิด", "Money", "Feeling"));
        problemList.add(new Question(2, "What is 2 + 2?", "1", "2", "3", "4", "ผิด", "2", "4"));
        problemList.add(new Question(1, "What is Java?", "Programming language", "Coffee", "Planet", "Game", "ถูก", "Programming language", "Programming language"));

        setProblems(problemList);
    }

    public void setProblems(List<Question> questions) {
        problemContainer.getChildren().clear();

        for (int i = 0; i < questions.size(); i++) {
            try {
                Question question = questions.get(i);
                FXMLLoader loader;

                if (question.getType() == 1) {
                    loader = new FXMLLoader(getClass().getResource("/fxml/Student/Quiz/questionCard.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("/fxml/Student/Quiz/answerPageCard2.fxml"));
                }

                VBox problemCard = loader.load();
                QuestionCardController q = loader.getController();
                if(question.getType() == 1){
                    q.setProblemCard1(i + 1, question.getQuestionText(), question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionD(), question.getCorrectness(), question.getSelectedChoice(), question.getCorrectAns());
                }
                else{
                    q.setProblemCard2(i + 1, question.getQuestionText(), question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionD(), question.getCorrectness(), question.getSelectedChoice(), question.getCorrectAns());
                }


                problemContainer.getChildren().add(problemCard);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void setEffect() {
        ef.hoverEffect(finishBtn);
    }

    private void route() {
        Navigator nav = new Navigator();
        finishBtn.setOnMouseClicked(nav::testResult);
    }

    public void setScore(int got, int total) {
        score.setText(got + "/" + total + " คะแนน");
    }
}
