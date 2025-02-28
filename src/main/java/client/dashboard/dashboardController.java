package client.dashboard;

import client.FontLoader.FontLoader;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import client.HomeAndNavigation.Navigator;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import client.HomeAndNavigation.HomeController;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    public Rectangle userpfp;
    public Circle trophy;
    public ScrollPane mainScrollPane;
    public HBox rootpage;


    //Nav element
    public HBox dashboard;
    public HBox course;
    public HBox inbox;
    public HBox task;
    public HBox roadmap;
    public HBox home;
    public Button yourCoursebtn;
    public VBox calendarContainer;
    public Button logout;
    public Button cart;
    public HBox logo;
    public HBox profile_btn;
    public Button pfp_btn;
    public Button allCoursebtn;
    public ImageView setting_btn;
    public VBox popup1;
    public VBox popup;
    public HBox bg;
    public HBox category_interested;
    public VBox statistics_data;
    public VBox us_st;
    public VBox category;
    public Circle category_pic;
    public Circle category_pic1;
    public Circle inboxP2;
    public Circle inboxP;
    public ProgressBar roadmap_progress;
    public ProgressBar roadmap_progress1;
    public Label roadmap_value;
    public Label roadmap_value1;
    public Pane cat1;
    public Pane cat2;
    public Pane user_profile;
    public VBox quickInbox;
    public VBox quizBox;
    public VBox roadmapProgression;
    public VBox courseProgression;
    public VBox scoreTendency;
    public VBox risk;
    public VBox studyTable;
    public Button btn_continue;
    public Button btn_otherCourse;
    public Label progressCategory;
    public Label progressCategory1;
    public ProgressBar categorybar1;
    public ProgressBar categorybar;
    public Label first_val;
    public Label first_subject_name;
    public Label second_val;
    public Label second_subject_name;
    public Label third_val;
    public Label third_subject_name;
    public BarChart scoreChart;
    public CategoryAxis xAxis_score;
    public NumberAxis yAxis_score;

    @FXML
    private StackedBarChart<String, Number> courseProgressionChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();

        //Routing navbar
        Navigator nav = new Navigator();


        HomeController method_home = new HomeController();

        //Set img container
        method_home.loadAndSetImage(userpfp, "/img/Picture/bg.jpg");
        method_home.loadAndSetImage(trophy,"/img/icon/trophy-star.png" );
        method_home.loadAndSetImage(category_pic,"/img/icon/code.png" );
        method_home.loadAndSetImage(category_pic1,"/img/icon/partners.png");
        method_home.loadAndSetImage(inboxP,"/img/Profile/doctor.png");
        method_home.loadAndSetImage(inboxP2,"/img/Profile/teacher.png");

        //Hover effect
        method_home.hoverEffect(dashboard);
        method_home.hoverEffect(course);
        method_home.hoverEffect(inbox);
        method_home.hoverEffect(task);
        method_home.hoverEffect(roadmap);
        method_home.hoverEffect(home);
        method_home.hoverEffect(yourCoursebtn);
        method_home.hoverEffect(calendarContainer);
        method_home.hoverEffect(allCoursebtn);
        method_home.hoverEffect(cat1);
        method_home.hoverEffect(cat2);
        method_home.hoverEffect(user_profile);
        method_home.hoverEffect(quickInbox);
        method_home.hoverEffect(quizBox);
        method_home.hoverEffect(roadmapProgression);
        method_home.hoverEffect(courseProgression);
        method_home.hoverEffect(scoreTendency);
        method_home.hoverEffect(risk);
        method_home.hoverEffect(studyTable);
        method_home.hoverEffect(btn_continue);
        method_home.hoverEffect(btn_otherCourse);

        method_home.applyHoverEffectToInside(quickInbox);

        route();

        //Adjust size
        mainScrollPane.requestLayout();
        mainScrollPane.prefWidthProperty().bind(rootpage.widthProperty());
        mainScrollPane.prefHeightProperty().bind(rootpage.heightProperty());
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        closePopupAuto();
        calendarDisplay();

        applyHoverEffectToInside(popup);
        applyHoverEffectToInside(popup1);

        us_st.setViewOrder(-1);

        categorybar.setProgress(Double.parseDouble(progressCategory.getText().replace("% completed", "").trim()) / 100);
        categorybar1.setProgress(Double.parseDouble(progressCategory1.getText().replace("% completed", "").trim()) / 100);
        roadmap_progress.setProgress(Double.parseDouble(roadmap_value.getText().replace("%", "").trim()) / 100);
        roadmap_progress1.setProgress(Double.parseDouble(roadmap_value1.getText().replace("%", "").trim()) / 100);

        //Call chart
        courseProgressionChart();
        scoreChart();
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

    private void route(){
        Navigator nav = new Navigator();

        //Home
        home.setOnMouseClicked(nav::homeRoute);
        logo.setOnMouseClicked(nav::homeRoute);

        //Dashboard
        dashboard.setOnMouseClicked(nav::dashboardRoute);

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

        //setting
        setting_btn.setOnMouseClicked(nav::settingRoute);

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
        }
    }

    @FXML
    private void _openPopup(Node popup) {
        popup.toFront();
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);

        if (!popup.isVisible()) {
            popup.setVisible(true);
            popup.setOpacity(0);
            fade.setFromValue(0);
            fade.setToValue(1);
        } else {
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> popup.setVisible(false));
        }
        fade.play();
    }

    public void closePopupAuto(){
        bg.setOnMouseClicked(event -> {
            if (popup.isVisible() && !popup.contains(event.getX() - popup.getLayoutX(), event.getY() - popup.getLayoutY())) {
                closePopup(popup);
            }
            if (popup1.isVisible() && !popup1.contains(event.getX() - popup1.getLayoutX(), event.getY() - popup1.getLayoutY())) {
                closePopup(popup1);
            }
        });
    }

    private void closePopup(Node popup) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> popup.setVisible(false));
        fade.play();
    }


    public void applyHoverEffectToInside(VBox root) {
        for (Node node : root.lookupAll(".explore_roadmapOrAddList")) {
            if (node instanceof HBox box) {
                applyHoverEffect1(box);
            }
        }
    }

    private void applyHoverEffect1(HBox categoryBox) {
        categoryBox.setOnMouseEntered(event -> {
            categoryBox.setStyle("-fx-background-color: #F4F4F4;");
        });
        categoryBox.setOnMouseExited(event -> {
            categoryBox.setStyle("-fx-background-color: transparent;");
        });
    }


    private void courseProgressionChart() {
        if (xAxis == null || yAxis == null || courseProgressionChart == null) {
            System.out.println("FXML components not setup properly!");
            return;
        }

        XYChart.Series<String, Number> module1 = new XYChart.Series<>();
        module1.setName(first_subject_name.getText());
        module1.getData().add(new XYChart.Data<>("Overall course progression (ร้อยละ %)", Double.parseDouble(first_val.getText().replace("%", "")) / 100));

        XYChart.Series<String, Number> module2 = new XYChart.Series<>();
        module2.setName(second_subject_name.getText());
        module2.getData().add(new XYChart.Data<>("Overall course progression (ร้อยละ %)", Double.parseDouble(second_val.getText().replace("%", "")) / 100));

        XYChart.Series<String, Number> module3 = new XYChart.Series<>();
        module3.setName(third_subject_name.getText());
        module3.getData().add(new XYChart.Data<>("Overall course progression (ร้อยละ %)", Double.parseDouble(third_val.getText().replace("%", "")) / 100));

        courseProgressionChart.getData().addAll(module1, module2, module3);

        Platform.runLater(() -> {
            courseProgressionChart.lookup(".default-color2.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(first_val)+ ";"+"-fx-background-radius: 0;");
            courseProgressionChart.lookup(".default-color1.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(second_val)+ ";"+"-fx-background-radius: 0;");
            courseProgressionChart.lookup(".default-color0.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(third_val)+ ";"+"-fx-background-radius: 0;");
        });
    }

    public String getColorLabel(Label label) {
        Color labelColor = (Color) label.getTextFill();
        return String.format("#%02X%02X%02X",
                (int) (labelColor.getRed() * 255),
                (int) (labelColor.getGreen() * 255),
                (int) (labelColor.getBlue() * 255));
    }


    private void scoreChart() {
        XYChart.Series<String, Number> module1 = new XYChart.Series<>();
        module1.setName(first_subject_name.getText());
        module1.getData().add(new XYChart.Data<>("Score Overview", 13000.4));

        XYChart.Series<String, Number> module2 = new XYChart.Series<>();
        module2.setName(second_subject_name.getText());
        module2.getData().add(new XYChart.Data<>("Score Overview",27000.83));

        XYChart.Series<String, Number> module3 = new XYChart.Series<>();
        module3.setName(third_subject_name.getText());
        module3.getData().add(new XYChart.Data<>("Score Overview", 9700));

        scoreChart.getData().addAll(module1, module2, module3);

        Platform.runLater(() -> {
            scoreChart.lookup(".default-color2.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(first_val)+ ";"+"-fx-background-radius: 0;");
            scoreChart.lookup(".default-color1.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(second_val)+ ";"+"-fx-background-radius: 0;");
            scoreChart.lookup(".default-color0.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(third_val)+ ";"+"-fx-background-radius: 0;");
        });
    }

}
