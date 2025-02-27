package client.HomeAndNavigation;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import client.FontLoader.FontLoader;

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
    public Pane topLeaderboard;
    public Pane smallStatistic;
    public Button btn_dashboard_atStatistics;
    public Pane smallUserProfile;
    public Pane cat1;
    public Pane cat2;
    public Pane cat3;
    public HBox learn_now;
    public HBox home_nav;
    public AnchorPane slider;
    public AnchorPane slide1;
    public AnchorPane slide2;
    public Button next;
    public Button previous;
    public ProgressBar continueProgressOOP;
    public Label progressOfConValueOOP;
    public Label progressOfConValueData;
    public ProgressBar continueProgressData;
    public Circle teacher_pfp_Data;
    public Circle teacher_pfp_OOP;
    public Rectangle course_pic_Data;
    public Rectangle course_pic_OOP;
    public Button cart;
    public HBox profile_btn;
    public Button pfp_btn;
    public ScrollPane mainScrollPane;
    public HBox rootpage;
    private List<AnchorPane> slides;
    private int slideIndex = 0;


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
        applyHoverEffectToInside(slide1);
        applyHoverEffectToInside(slide2);
        closePopupAuto();
        callSlider();

        mainScrollPane.requestLayout();
        mainScrollPane.prefWidthProperty().bind(rootpage.widthProperty());
        mainScrollPane.prefHeightProperty().bind(rootpage.heightProperty());
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


    }





    public void callSlider(){
        slides = new ArrayList<>();
        slides.add(slide1);
        slides.add(slide2);

        updateSlide();
        next.setOnAction(event -> showNext());
        previous.setOnAction(event -> showPrevious());
    }

    private void updateSlide(){
        for(int i=0; i<slides.size(); i++){
            slides.get(i).setVisible(i == slideIndex);
        }
    }

    private void showNext(){
        slideIndex = (slideIndex + 1)%slides.size();
        slideTransition(slides.get(slideIndex));
        updateSlide();
    }

    private void showPrevious(){
        slideIndex = (slideIndex-1+slides.size())%slides.size();
        slideTransition(slides.get(slideIndex));
        updateSlide();
    }

    private void slideTransition(Node slide) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), slide);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
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
        dropShadow.setColor(Color.TRANSPARENT);
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
        scaleUp.setToX(1.07);
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

        //Home
        home.setOnMouseClicked(nav::homeRoute);
        home_nav.setOnMouseClicked(nav::homeRoute);

        //Dashboard
        dashboard.setOnMouseClicked(nav::dashboardRoute);
        btn_dashboard_atStatistics.setOnMouseClicked(nav::dashboardRoute);
        profile_btn.setOnMouseClicked(nav::dashboardRoute);
        pfp_btn.setOnMouseClicked(nav::dashboardRoute);

        //Course
        course.setOnMouseClicked(nav::courseRoute);
        allCoursebtn.setOnMouseClicked(nav::courseRoute);

        //My Course
        yourCoursebtn.setOnMouseClicked(nav::myCourseRoute);
        calendarContainer.setOnMouseClicked(nav::myCourseRoute);

        //cart
        cart.setOnMouseClicked(nav::cartRoute);

        //Inbox
        inbox.setOnMouseClicked(nav::inboxRoute);

        //Task
        task.setOnMouseClicked(nav::taskRoute);

        //Roadmap
        roadmap.setOnMouseClicked(nav::roadmapRoute);

        //Leaderboard
        topLeaderboard.setOnMouseClicked(nav::leaderboardRoute);

        //setting
        setting.setOnMouseClicked(nav::settingRoute);

        //Logout
        logout.setOnMouseClicked(nav::logoutRoute);
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

    private void setImgContainer() {
        loadAndSetImage(imgContainer, "/img/Picture/bg.jpg");
        loadAndSetImage(teacherBanner, "/img/Picture/อาจารย์ แบงค์.jpg");

        loadAndSetImage(pfp, "/img/Profile/doctor.png");
        loadAndSetImage(pfp_statistic, "/img/Profile/doctor.png");
        loadAndSetImage(teacher_pfp, "/img/Profile/man.png");

        String studentPfpPath = "/img/Profile/student.png";
        loadAndSetImage(leaderboard_pfp, studentPfpPath);
        loadAndSetImage(leaderboard_pfp1, studentPfpPath);
        loadAndSetImage(leaderboard_pfp2, studentPfpPath);
        loadAndSetImage(leaderboard_pfp3, studentPfpPath);

        loadAndSetImage(course_pic, "/img/Picture/Python.png");
        loadAndSetImage(category_pic, "/img/icon/code.png");
        loadAndSetImage(category_pic1, "/img/icon/partners.png");
        loadAndSetImage(category_pic2, "/img/icon/artificial-intelligence.png");

        loadAndSetImage(teacher_pfp_OOP, "/img/Profile/man.png");
        loadAndSetImage(teacher_pfp_Data, "/img/Profile/teacher.png");
        loadAndSetImage(course_pic_Data, "/img/Picture/DSA.jpg");
        loadAndSetImage(course_pic_OOP, "/img/Picture/bg.jpg");
    }

    public void loadAndSetImage(Shape shape, String path) {
        Image img = new Image(getClass().getResource(path).toExternalForm());
        shape.setStroke(Color.TRANSPARENT);
        shape.setFill(new ImagePattern(img));
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
        continueProgressData.setProgress(Double.parseDouble(progressOfConValueData.getText().replace("%", "").trim()) / 100);
        continueProgressOOP.setProgress(Double.parseDouble(progressOfConValueOOP.getText().replace("%", "").trim()) / 100);
    }



    private void calendarDisplay(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/client/HomePage/CustomCalendar.fxml"));
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

    public void applyHoverEffectToInside(AnchorPane root) {
        for (Node node : root.lookupAll(".continueCourse")) {
            if (node instanceof Pane p) {
                hoverEffect(p);
            }
        }
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