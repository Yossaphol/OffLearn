package HomeAndNavigation;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import com.sun.tools.javac.Main;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    public HBox home;
    public ProgressBar codingProgress;
    public VBox categoryPopup;
    public HBox category_recommend;
    public Button seeAll;
    public ProgressBar businessProgress;
    public ProgressBar aiProgress;
    public ProgressBar mathProgress;
    public Button yourCoursebtn;
    public Button allCoursebtn;
    public HBox setting;
    public HBox logout;
    public Pane continueCourse1;
    public Pane topLeaderboard;
    public Pane smallStatistic;
    public Button btn_dashboard_atStatistics;
    public Pane smallUserProfile;
    public Pane cat1;
    public Pane cat2;
    public Pane cat3;
    public HBox learn_now;
    public HBox home_nav;


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
        route();

        hoverEffect(dashboard);
        hoverEffect(course);
        hoverEffect(inbox);
        hoverEffect(task);
        hoverEffect(roadmap);
        hoverEffect(yourCoursebtn);
        hoverEffect(allCoursebtn);
        hoverEffect(setting);
        hoverEffect(logout);
        hoverEffect(continueCourse1);
        hoverEffect(topLeaderboard);
        hoverEffect(smallStatistic);
        hoverEffect(btn_dashboard_atStatistics);
        hoverEffect(smallUserProfile);
        hoverEffect(calendarContainer);
        hoverEffect(cat1);
        hoverEffect(cat2);
        hoverEffect(learn_now);
        hoverEffect(cat3);
        hoverEffect(home_nav);

        applyHoverEffectToInside(categoryPopup);
        applyHoverEffectToInside(popup);
        applyHoverEffectToInside(popup1);
        applyHoverEffectToInside(popup2);
        closePopupAuto();
    }


    public void hoverEffect(Button btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.05);
        scaleDown.setFromY(1.05);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
        });
    }

    public void hoverEffect(Pane pane) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), pane);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.03);
        scaleUp.setToY(1.03);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), pane);
        scaleDown.setFromX(1.03);
        scaleDown.setFromY(1.03);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        pane.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
            dropShadow.setColor(Color.web("#8100CC", 0.25));
            pane.setEffect(dropShadow);
        });

        pane.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            dropShadow.setColor(Color.web("#c6c6c6", 0.25));
            pane.setEffect(dropShadow);
        });
    }

    public void hoverEffect(VBox vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT); // Start with no visible color
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#8100CC", 0.25)))
            );
            timeline.play();
        });

        vBox.setOnMouseExited(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), dropShadow.getColor())),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT))
            );
            timeline.play();
        });
    }

    public void hoverEffect(HBox hBox) {
        // Scale transition
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), hBox);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.07); // Slightly bigger scale for better effect
        scaleUp.setToY(1.07);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), hBox);
        scaleDown.setFromX(1.07);
        scaleDown.setFromY(1.07);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        DropShadow glow = new DropShadow();
        glow.setRadius(20);
        glow.setSpread(0.5);
        glow.setColor(Color.web("#8100CC", 0.6));

        hBox.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
            switch (hBox.getId()) {
                case "logout":
                    hBox.setStyle("-fx-background-color: #FFEBEB;");
                    break;
                case "learn_now":
                    hBox.setEffect(glow);
                    hBox.setStyle(
                            "-fx-background-radius: 30; " +
                                    "-fx-background-color: linear-gradient(to right, #8100CC, #A000FF);"
                    );
                    break;
                default:
                    hBox.setStyle("-fx-background-color: #F7E9FF;");
                    break;
            }
        });

        hBox.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            switch (hBox.getId()){
                case "learn_now":
                    hBox.setEffect(null);
                    hBox.setStyle("-fx-background-radius: 30;" + "-fx-background-color: #8100CC;");
                    break;
                default :
                    hBox.setStyle("-fx-background-color: transparent;");
            }

        });
    }


    public void route(){
        Navigator nav = new Navigator();
        home.setOnMouseClicked(mouseEvent -> nav.homeRoute(mouseEvent, bg));
        dashboard.setOnMouseClicked(mouseEvent -> nav.dashboardRoute(mouseEvent, bg));
        course.setOnMouseClicked(mouseEvent -> nav.courseRoute(mouseEvent, bg));
        inbox.setOnMouseClicked(mouseEvent -> nav.inboxRoute(mouseEvent, bg));
        task.setOnMouseClicked(mouseEvent -> nav.taskRoute(mouseEvent, bg));
        roadmap.setOnMouseClicked(mouseEvent -> nav.roadmapRoute(mouseEvent, bg));
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
            case "seeAll":
                _openPopup(categoryPopup);
        }
    }

    @FXML
    private void _openPopup(Node popup) {
        popup.toFront();
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
        FadeTransition fade1 = new FadeTransition(Duration.millis(300), hideIfpopupOpen);
        FadeTransition fade2 = new FadeTransition(Duration.millis(300), category_recommend);

        if (!popup.isVisible()) {
            if(popup.getId().equals("categoryPopup")){
                seeAll.setText("ปิด");
                category_recommend.setVisible(false);
                category_recommend.setOpacity(0);
                fade2.setFromValue(1);
                fade2.setToValue(0);
                fade2.setOnFinished(e -> category_recommend.setVisible(false));
            }
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
            if(popup.getId().equals("categoryPopup")){
                seeAll.setText("ดูทั้งหมด");
                category_recommend.setVisible(true);
                category_recommend.setOpacity(0);
                fade2.setFromValue(0);
                fade2.setToValue(1);
            }
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
        fade2.play();
    }

    public void closePopupAuto(){
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
            if (categoryPopup.isVisible() && !categoryPopup.contains(event.getX() - categoryPopup.getLayoutX(), event.getY() - categoryPopup.getLayoutY())) {
                seeAll.setText("ดูทั้งหมด");
                closePopup(categoryPopup);
            }
        });
    }

    private void closePopup(Node popup) {
        FadeTransition fade2 = new FadeTransition(Duration.millis(300), category_recommend);
        if(popup.getId().equals("categoryPopup")){
            category_recommend.setVisible(true);
            category_recommend.setOpacity(0);
            fade2.setFromValue(0);
            fade2.setToValue(1);
        }

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
        fade2.play();
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

    private void progressValue() {
        progress1.setProgress(Double.parseDouble(progressValue1.getText().replace("%", "").trim()) / 100);
        progress2.setProgress(Double.parseDouble(progressValue2.getText().replace("%", "").trim()) / 100);
        continueProgress.setProgress(Double.parseDouble(progressOfConValue.getText().replace("%", "").trim()) / 100);

        categorybar.setProgress(Double.parseDouble(progressCategory.getText().replace("% completed", "").trim()) / 100);
        categorybar1.setProgress(Double.parseDouble(progressCategory1.getText().replace("% completed", "").trim()) / 100);
        categorybar2.setProgress(Double.parseDouble(progressCategory2.getText().replace("% completed", "").trim()) / 100);

        codingProgress.setProgress(Double.parseDouble(progressCategory.getText().replace("% completed", "").trim()) / 100);
        businessProgress.setProgress(Double.parseDouble(progressValue1.getText().replace("%", "").trim()) / 100);
        mathProgress.setProgress(Double.parseDouble(progressValue2.getText().replace("%", "").trim()) / 100);
        aiProgress.setProgress(Double.parseDouble(progressValue1.getText().replace("%", "").trim()) / 100);
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

    public void applyHoverEffectToInside(VBox root) {
        for (Node node : root.lookupAll(".explore_roadmapOrAddList")) {
            if (node instanceof HBox box) {
                applyHoverEffect1(box);
            }
        }
        for (Node node : root.lookupAll(".small-category")) {
            if (node instanceof HBox box) {
                    applyHoverEffect(box);
            }
        }
    }



    private void applyHoverEffect(HBox categoryBox) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), categoryBox);
        scaleUp.setToX(1.08);
        scaleUp.setToY(1.08);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), categoryBox);
        scaleDown.setToX(1);
        scaleDown.setToY(1);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.web("#c4c4c4", 0.25));
        categoryBox.setEffect(dropShadow);

        categoryBox.setOnMouseEntered(event -> {
            scaleUp.play();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#8100CC", 0.25)))
            );
            timeline.play();
        });
        categoryBox.setOnMouseExited(event -> {
            scaleDown.play();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), dropShadow.getColor())),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#c4c4c4", 0.25)))
            );
            timeline.play();
        });
    }

    private void applyHoverEffect1(HBox categoryBox) {
        categoryBox.setOnMouseEntered(event -> {
            categoryBox.setStyle("-fx-background-color: #F4F4F4;");
        });
        categoryBox.setOnMouseExited(event -> {
            categoryBox.setStyle("-fx-background-color: transparent;");
        });
    }


}
