package Student.Quiz;

import Database.CourseDB;
import Database.ScoreDB;
import Database.UserDB;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Teacher.quiz.QuizItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultPageController implements Initializable {

    @FXML
    private Label yourScore;

    @FXML
    private Label scorePercent;

    @FXML
    private BarChart barChart;

    @FXML
    private Circle teacherProfile;

    @FXML
    private Label teacherName;

    @FXML
    private Label teacherDesc;

    @FXML
    private HBox teacherProfileContainer;

    @FXML
    private Label score_average_testerpercent;

    private ScoreDB scoreDB;
    private UserDB userDB;
    private int userID = 17;
    private int chapterID = 131;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadData(int point, int courseID, QuizItem quizItem){
        scoreDB = new ScoreDB();

        int percent = (point / quizItem.getMaxScore()) * 100;
        this.yourScore.setText(point + "/" + quizItem.getMaxScore());
        this.scorePercent.setText(percent + " %");

        String[] userInfo = userDB.getUserNameProfileAndSpecByCourseID(courseID);
        this.teacherName.setText(userInfo[0]);
        setCircularImage(teacherProfile, userInfo[1]);
        this.teacherDesc.setText(userInfo[2]);

        double scorePercentage = scoreDB.calculateScorePercentage(userID, chapterID);
        this.score_average_testerpercent.setText("มากกว่า " + scorePercentage + "% ของผู้สอบ");
    }

    public void setCircularImage(Circle circle, String imagePath) {
        Image image = new Image(imagePath, true);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(circle.getRadius() * 2);
        imageView.setFitHeight(circle.getRadius() * 2);

        circle.setCenterX(circle.getRadius());
        circle.setCenterY(circle.getRadius());
        imageView.setClip(circle);

        HBox imageContainer = new HBox(imageView);
        teacherProfileContainer.getChildren().add(imageContainer);
    }


}
