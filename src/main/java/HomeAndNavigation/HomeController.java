package HomeAndNavigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import com.sun.tools.javac.Main;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.w3c.dom.html.HTMLObjectElement;
import com.sun.tools.javac.Main;
import javafx.scene.layout.HBox;

public class HomeController implements Initializable {

    @FXML

    public ProgressBar progress1;
    public ProgressBar progress2;
    public Label progressValue2;
    public Label progressValue1;
    public ProgressBar continueProgress;
    public Label progressOfConValue;
    public Rectangle imgContainer;
    public Circle teacherBanner;
    public Circle pfp;
    public Circle pfp_statistic;
    public Circle teacher_pfp;
    public Circle leaderboard_pfp;
    public Circle leaderboard_pfp1;
    public Circle leaderboard_pfp2;
    public Circle leaderboard_pfp3;
    public Rectangle course_pic;
    public Circle category_pic;
    public VBox popup;
    public Label progressCategory;
    public ProgressBar categorybar;
    public HBox forAddL;
    public HBox hideIfpopupOpen;
    public HBox bg;
    public Circle category_pic1;
    public Circle category_pic2;
    public Label progressCategory1;
    public ProgressBar categorybar1;
    public ProgressBar categorybar2;
    public Label progressCategory2;
    public VBox popup1;
    public VBox popup2;

    public HBox dashboard;
    public HBox course;
    public HBox inbox;
    public HBox task;
    public HBox roadmap;


    @FXML
    private VBox calendarContainer;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private HBox MainFrame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressValue();
        calendarDisplay();
        setupBarChart();
        setImgContainer();
        route();

        bg.setOnMouseClicked(event -> {
            if (popup.isVisible() && !popup.contains(event.getX() - popup.getLayoutX(), event.getY() - popup.getLayoutY())) {
                closePopup(popup);
            }
            if (popup1.isVisible() && !popup1.contains(event.getX() - popup1.getLayoutX(), event.getY() - popup1.getLayoutY())) {
                closePopup(popup1);
            }
            if (popup2.isVisible() && !popup2.contains(event.getX() - popup2.getLayoutX(), event.getY() - popup2.getLayoutY())) {
                closePopup(popup2);
            }
        });
    }

    public void route(){
        Navigator nav = new Navigator();
        dashboard.setOnMouseClicked(mouseEvent -> nav.dashboardRoute(mouseEvent, MainFrame));
        course.setOnMouseClicked(mouseEvent -> nav.courseRoute(mouseEvent, MainFrame));
        inbox.setOnMouseClicked(mouseEvent -> nav.inboxRoute(mouseEvent, MainFrame));
        task.setOnMouseClicked(mouseEvent -> nav.taskRoute(mouseEvent, MainFrame));
        roadmap.setOnMouseClicked(mouseEvent -> nav.roadmapRoute(mouseEvent, MainFrame));
    }

    @FXML
    private void openPopup(ActionEvent event){
        Button clickedbtn = (Button) event.getSource();
        switch (clickedbtn.getId()){
            case "btnpopup":
                _openPopup(popup);
                break;
            case "btnpopup1":
                _openPopup(popup1);
                break;
            case "btnpopup2":
                _openPopup(popup2);
                break;
        }
    }

    @FXML
    private void _openPopup(Node popup) {
        popup.toFront();
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
        FadeTransition fade1 = new FadeTransition(Duration.millis(300), hideIfpopupOpen);

        if (!popup.isVisible()) {
            popup.setVisible(true);
            popup.setOpacity(0);
            fade.setFromValue(0);
            fade.setToValue(1);
            hideIfpopupOpen.setVisible(false);
            hideIfpopupOpen.setOpacity(0);
            fade1.setFromValue(1);
            fade1.setToValue(0);
            fade1.setOnFinished(e -> hideIfpopupOpen.setVisible(false));
        } else {
            hideIfpopupOpen.setVisible(true);
            hideIfpopupOpen.setOpacity(0);
            fade1.setFromValue(0);
            fade1.setToValue(1);

            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> popup.setVisible(false));
        }

        fade1.play();
        fade.play();
    }

    private void closePopup(Node popup) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
        FadeTransition fade1 = new FadeTransition(Duration.millis(300), hideIfpopupOpen);

        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> popup.setVisible(false));

        hideIfpopupOpen.setVisible(true);
        hideIfpopupOpen.setOpacity(0);
        fade1.setFromValue(0);
        fade1.setToValue(1);

        fade1.play();
        fade.play();
    }

    private void setImgContainer(){
        Image imgAtBanner = new Image(getClass().getResource("/img/Picture/bg.jpg").toExternalForm());
        imgContainer.setStroke(Color.TRANSPARENT);
        imgContainer.setFill(new ImagePattern(imgAtBanner));

        Image imgTeacherAtBanner = new Image(getClass().getResource("/img/Picture/อาจารย์ แบงค์.jpg").toExternalForm());
        teacherBanner.setStroke(Color.TRANSPARENT);
        teacherBanner.setFill(new ImagePattern(imgTeacherAtBanner));

        Image profilepic = new Image(getClass().getResource("/img/Profile/doctor.png").toExternalForm());
        pfp.setStroke(Color.TRANSPARENT);
        pfp.setFill(new ImagePattern(profilepic));

        Image profilepic1 = new Image(getClass().getResource("/img/Profile/doctor.png").toExternalForm());
        pfp_statistic.setStroke(Color.TRANSPARENT);
        pfp_statistic.setFill(new ImagePattern(profilepic1));

        Image teacher1 = new Image(getClass().getResource("/img/Profile/man.png").toExternalForm());
        teacher_pfp.setStroke(Color.TRANSPARENT);
        teacher_pfp.setFill(new ImagePattern(teacher1));

        Image user2 = new Image(getClass().getResource("/img/Profile/student.png").toExternalForm());
        leaderboard_pfp.setStroke(Color.TRANSPARENT);
        leaderboard_pfp.setFill(new ImagePattern(user2));
        leaderboard_pfp1.setStroke(Color.TRANSPARENT);
        leaderboard_pfp1.setFill(new ImagePattern(user2));
        leaderboard_pfp2.setStroke(Color.TRANSPARENT);
        leaderboard_pfp2.setFill(new ImagePattern(user2));
        leaderboard_pfp3.setStroke(Color.TRANSPARENT);
        leaderboard_pfp3.setFill(new ImagePattern(user2));

        Image course_pic1 = new Image(getClass().getResource("/img/Picture/Python.png").toExternalForm());
        course_pic.setStroke(Color.TRANSPARENT);
        course_pic.setFill(new ImagePattern(course_pic1));

        Image category_pics = new Image(getClass().getResource("/img/icon/code.png").toExternalForm());
        category_pic.setStroke(Color.TRANSPARENT);
        category_pic.setFill(new ImagePattern(category_pics));

        Image category_pic1s = new Image(getClass().getResource("/img/icon/partners.png").toExternalForm());
        category_pic1.setStroke(Color.TRANSPARENT);
        category_pic1.setFill(new ImagePattern(category_pic1s));

        Image category_pic2s = new Image(getClass().getResource("/img/icon/artificial-intelligence.png").toExternalForm());
        category_pic2.setStroke(Color.TRANSPARENT);
        category_pic2.setFill(new ImagePattern(category_pic2s));
    }

    private void progressValue(){
        progress1.setProgress(Double.parseDouble(progressValue1.getText().replace("%", "").trim()) / 100);
        progress2.setProgress(Double.parseDouble(progressValue2.getText().replace("%", "").trim()) / 100);
        continueProgress.setProgress(Double.parseDouble(progressOfConValue.getText().replace("%", "").trim()) / 100);
        categorybar.setProgress(Double.parseDouble(progressCategory.getText().replace("% completed", "").trim()) / 100);
        categorybar1.setProgress(Double.parseDouble(progressCategory1.getText().replace("% completed", "").trim()) / 100);
        categorybar2.setProgress(Double.parseDouble(progressCategory2.getText().replace("% completed", "").trim()) / 100);
    }


    private void calendarDisplay(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/CustomCalendar.fxml"));
            VBox calendarContent = calendarLoader.load();
            calendarContainer.getChildren().setAll(calendarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupBarChart() {
        List<XYChart.Data<String, Number>> chartData = new ArrayList<>();
        chartData.add(new XYChart.Data<>("DSA", 15400));
        chartData.add(new XYChart.Data<>("PSCP", 18000));
        chartData.add(new XYChart.Data<>("MFIT", 11750));
        chartData.add(new XYChart.Data<>("ICS", 14000));

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (int i = 0; i < chartData.size(); i++) {
            XYChart.Data<String, Number> data = chartData.get(i);
            if (i % 2 == 0) {
                series.getData().add(data);
                data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.setStyle("-fx-bar-fill: #8100CC;");
                    }
                });
            } else {
                series.getData().add(data);
                data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.setStyle("-fx-bar-fill: #C35BFF;");
                    }
                });
            }
        }
        barChart.getData().add(series);
    }


}
