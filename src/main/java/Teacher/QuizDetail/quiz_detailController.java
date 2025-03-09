package Teacher.QuizDetail;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class quiz_detailController implements Initializable {

    public HBox searhbar_container;
    public HBox quiz_container;
    public HBox goback;
    public Label quiz_name;
    public Label min_score;
    public Label max_score;
    public Label quiz_count;
    public BubbleChart quiz_chart;
    public Label quiz_score_top;
    public Label quiz_score_avg;
    public Label quiz_score_min;
    public Label quiz_score_std;
    public ImageView quiz_edit_logo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void quizList(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/Quiz/quiz_choice.fxml"));
            HBox navContent = calendarLoader.load();
            quiz_container.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
