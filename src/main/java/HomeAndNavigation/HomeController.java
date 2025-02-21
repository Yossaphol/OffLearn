package HomeAndNavigation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML
    private VBox calendarContainer;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressValue();
        calendarDisplay();
        setupBarChart();
        setImgContainer();
    }

    private void setImgContainer(){
        // For image containers, since I can't set its radius directly
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

        Image category_pic1 = new Image(getClass().getResource("/img/icon/code.png").toExternalForm());
        category_pic.setStroke(Color.TRANSPARENT);
        category_pic.setFill(new ImagePattern(category_pic1));
    }

    private void progressValue(){
        progress1.setProgress(Double.parseDouble(progressValue1.getText().replace("%", "").trim()) / 100);
        progress2.setProgress(Double.parseDouble(progressValue2.getText().replace("%", "").trim()) / 100);
        continueProgress.setProgress(Double.parseDouble(progressOfConValue.getText().replace("%", "").trim()) / 100);
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
