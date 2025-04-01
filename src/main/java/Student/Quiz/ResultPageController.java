package Student.Quiz;

import Database.CourseDB;
import Database.ScoreDB;
import Database.UserDB;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Student.learningPage.DisposableController;
import Teacher.courseManagement.Course;
import Teacher.quiz.QuizItem;
import a_Session.SessionHandler;
import a_Session.SessionHandler;
import a_Session.SessionManager;
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
import java.util.Map;
import java.util.ResourceBundle;

public class ResultPageController implements Initializable, SessionHandler, DisposableController {

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

    @FXML
    private Button seeAnswer;

    @FXML
    private Button startLearning;


    private ScoreDB scoreDB;
    private UserDB userDB;
    private int userID;
    private int chapterID;
    private Navigator navigator;
    private int point;
    private QuizItem quizItem;
    private CourseDB courseDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSeeAnswer();
        setComplete();
    }

    public void loadData(int point, int courseID, QuizItem quizItem, int chapterID){
        this.point = point;
        this.quizItem = quizItem;
        this.chapterID = chapterID;
        scoreDB = new ScoreDB();
        userDB = new UserDB();

        int percent = (point / quizItem.getMaxScore()) * 100;
        this.yourScore.setText(point + "/" + quizItem.getMaxScore());
        this.scorePercent.setText(percent + " %");

        String[] userInfo = userDB.getUserNameProfileAndSpecByCourseID(courseID);
        this.teacherName.setText(userInfo[0]);
        setCircularImage(teacherProfile, userInfo[1]);
        this.teacherDesc.setText(userInfo[2]);

        double scorePercentage = scoreDB.calculateScorePercentage(userID, chapterID);
        this.score_average_testerpercent.setText(
                "มากกว่า " + String.format("%.2f", scorePercentage) + "% ของผู้สอบ"
        );

        setBarChart(quizItem.getQuizID(), quizItem);
    }

    @Override
    public void handleSession() {
        userDB = new UserDB();
        this.userID = userDB.getUserId(SessionManager.getInstance().getUsername());
    }

    public void setCircularImage(Circle circle, String imagePath) {
        Image image = new Image(imagePath, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(circle.getRadius() * 2);
        imageView.setFitHeight(circle.getRadius() * 2);

        Circle clip = new Circle(circle.getRadius());
        clip.setCenterX(circle.getRadius());
        clip.setCenterY(circle.getRadius());
        imageView.setClip(clip);

        teacherProfileContainer.getChildren().clear();
        teacherProfileContainer.getChildren().add(imageView);
    }

    public void setSeeAnswer(){
        courseDB = new CourseDB();
        navigator = new Navigator();
        seeAnswer.setOnAction(actionEvent -> {
            navigator.QuizSummary(point, courseDB.getCourseIDByChapterID(chapterID), quizItem, chapterID, this);
        });
    }

    public void setComplete(){
        navigator = new Navigator();
        startLearning.setOnAction(actionEvent -> navigator.learningPageRoute(actionEvent));
    }

    public void setBarChart(int quizId, QuizItem quizItem) {
        scoreDB = new ScoreDB();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Students");
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(quizItem.getMaxScore());
        int range = quizItem.getMaxScore() / 10;
        yAxis.setTickUnit(range);
        xAxis.setLabel("Score");

        barChart.setTitle("Score Distribution");
        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Scores");

        Map<Integer, Integer> scoreData = scoreDB.getScoreDistribution(quizId);

        for (int score = 0; score <= quizItem.getMaxScore(); score++) {
            int count = scoreData.getOrDefault(score, 0);
            XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(score), count);

            if (score == point) {
                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.setStyle("-fx-bar-fill: #8100CC;");
                    }
                });
            } else {
                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.setStyle("-fx-bar-fill: #8100CC;");
                        newNode.setOpacity(0.32);
                    }
                });
            }

            series.getData().add(data);
        }

        barChart.getData().add(series);
    }


    @Override
    public void disposePlayer() {

    }
}
