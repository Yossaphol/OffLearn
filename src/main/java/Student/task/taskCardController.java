package Student.task;

import Student.HomeAndNavigation.Navigator;
import Student.Quiz.QuizPageController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class taskCardController implements Initializable {
    public Label taskInformation;
    public Label taskDetail;
    public Label point;
    Navigator nav = new Navigator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setTaskInformation(String information, String subject, int score){
        taskInformation.setText(information);
        taskDetail.setText(subject);
        point.setText(score+" คะแนน");
    }

    public void setTaskInformation(String information, String subject, String score){
        taskInformation.setText(information);
        taskDetail.setText(subject);
        point.setText(score);
    }

    public void quizLink(int quizID,int chapterID, Button taskBtn){
        taskBtn.setOnMouseClicked(e->nav.navigateTo("/fxml/Student/Quiz/quizPage.fxml", chapterID,quizID));
    }

}
