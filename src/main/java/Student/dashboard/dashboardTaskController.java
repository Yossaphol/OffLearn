package Student.dashboard;

import Student.HomeAndNavigation.Navigator;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class dashboardTaskController implements Initializable {
    public Button continue_task;
    public Label Course;
    public Label Quiz;
    Navigator nav = new Navigator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         route();
    }

    public void route(){
        Navigator nav = new Navigator();
        //continue_task.setOnMouseClicked(nav::taskRoute);
    }

    public void setTaskInformation(String course, String quiz ){
        Course.setText(course);
        Quiz.setText(quiz);
    }

    public void quizLink(int quizID,int chapterID, HBox taskBtn){
        taskBtn.setOnMouseClicked(e->nav.navigateTo("/fxml/Student/Quiz/quizPage.fxml", chapterID,quizID));
    }
}
