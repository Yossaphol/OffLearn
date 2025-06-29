package Teacher.dashboard;

import Database.CourseDB;
import Database.EnrollDB;
import Database.ScoreDB;
import Database.UserDB;
import Teacher.courseManagement.CourseItem;
import Teacher.courseManagement.CourseListInDash;
import a_Session.SessionHandler;
import a_Session.SessionHandler;
import a_Session.SessionManager;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import Student.HomeAndNavigation.HomeController;
import Teacher.navigator.Navigator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class dashboardController implements Initializable, SessionHandler {
    @FXML
    private HBox searhbar_container;

    @FXML
    private HBox dashboard_profile;

    @FXML
    private BarChart revenueChart;

    @FXML
    private CategoryAxis xAxis_revenue;

    @FXML
    private NumberAxis yAxis_revenue;

    @FXML
    private BarChart topCourse;

    @FXML
    private PieChart pie_chart_std;

    @FXML
    private Button course_manageBtn;

    @FXML
    private Button withdrawBtn;

    @FXML
    private Button course_btn;

    @FXML
    private Button withdraw_btn1;

    @FXML
    private HBox linechart_container;

    @FXML
    private VBox piechartContainer;

    @FXML
    private VBox lowScore_container;

    @FXML
    private VBox revenue_container;

    @FXML
    private VBox overview_revenue_container;

    @FXML
    private VBox month_selector;

    @FXML
    private Button month_select;

    @FXML
    private ScrollPane courseTableContainer;

    @FXML
    private VBox myCourseList;

    @FXML
    private Label currCount;

    @FXML
    private Label lastMonthCount;

    @FXML
    private Label growth_rate;

    @FXML
    private Label revenue;

    @FXML
    private Label total_revenue;

    @FXML
    private Label totalEnroll;

    @FXML
    private Label pass_rate;

    @FXML
    private Label fail_rate;

    @FXML
    private Label lowScore;

    @FXML
    private Button save_change;

    private CourseDB courseDB;
    private int userID;
    private ArrayList<CourseItem> courseItemList;
    private EnrollDB enrollDB;
    private int[] enrollCount;
    private ScoreDB scoreDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handleSession();

        HomeController effectMethod = new HomeController();
        effectMethod.hoverEffect(course_manageBtn);
        effectMethod.hoverEffect(withdrawBtn);
        effectMethod.hoverEffect(course_btn);
        effectMethod.hoverEffect(withdraw_btn1);

        effectMethod.applyHoverEffectToInside(month_selector);

        hoverEffect(linechart_container);
        hoverEffect(piechartContainer);
        hoverEffect(lowScore_container);
        hoverEffect(revenue_container);
        hoverEffect(overview_revenue_container);

        closePopupAuto();

        displayNavbar();
        displayProfileBox();
        displayCourseInDash();
        setupRevenueChart();
        setupLinechartEnroll();
        setupPieChart();
        route();

        Platform.runLater(() -> {
            Stage stage = (Stage) searhbar_container.getScene().getWindow();
            stage.setWidth(stage.getWidth() - 1);
            stage.setHeight(stage.getHeight() - 1);
            stage.setWidth(stage.getWidth() + 1);
            stage.setHeight(stage.getHeight() + 1);
            searhbar_container.getScene().getRoot().requestLayout();
            searhbar_container.getScene().getRoot().applyCss();
        });

    }

    @Override
    public void handleSession() {
        UserDB userDB = new UserDB();
        this.userID = userDB.getUserId(SessionManager.getInstance().getUsername());
    }

    public void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void route(){
        Navigator nav = new Navigator();

        //Course
        course_btn.setOnMouseClicked(nav::courseRoute);
        course_manageBtn.setOnMouseClicked(nav::courseRoute);

        withdrawBtn.setOnMouseClicked(nav::withdrawRoute);
        withdraw_btn1.setOnMouseClicked(nav::withdrawRoute);

    }

    public void displayCourseInDash(){
        courseDB = new CourseDB();
        this.courseItemList = courseDB.getMyCourse(userID);
        enrollDB = new EnrollDB();

        for (CourseItem c : courseItemList){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseListInDash.fxml"));
                HBox courseList = fxmlLoader.load();

                CourseListInDash courseListInDash = fxmlLoader.getController();
                int[] income = enrollDB.getRevenueAndEnrollCountByCourseId(c.getCourseId());

                courseListInDash.setDisplay(c.getCourseImg(), c.getCourseName(), c.getCourseCat(), income[0]);

                myCourseList.getChildren().add(courseList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupRevenueChart() {
        enrollDB = new EnrollDB();
        Map<String, Integer> revenue = enrollDB.getTop3CoursesByEnrollment(this.userID);
        int total = 0;


        if (xAxis_revenue == null || yAxis_revenue == null || revenueChart == null) {
            System.out.println("FXML components not setup properly!");
            return;
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Sales");

        for (Map.Entry<String, Integer> entry : revenue.entrySet()) {
            total += entry.getValue();
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        this.revenue.setText("รายได้ " + total + " บาท");
        revenueChart.setLegendVisible(false);
        revenueChart.getData().add(series);

        this.total_revenue.setText(enrollDB.getTotalRevenueForCurrentMonth(this.userID) + "");
        this.totalEnroll.setText("ยอดการเข้าร่วม " + enrollDB.getTotalEnrollments(this.userID) + " คน");

    }

    public void setupLinechartEnroll() {
        enrollDB = new EnrollDB();
        Map<String, Integer> topCourses = enrollDB.getTop3CoursesByEnrollment(this.userID);

        enrollCount = enrollDB.countEnrollmentsForCurrentAndLastMonth(userID);

        double percent = calculatePercentageIncrease(enrollCount[0], enrollCount[1]);
        currCount.setText(enrollCount[0] + "");
        lastMonthCount.setText(enrollCount[1] + "");

        growth_rate.setText(String.format("%.2f%%", percent));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Enrollment Courses");


        for (Map.Entry<String, Integer> entry : topCourses.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        topCourse.setLegendVisible(false);
        topCourse.getData().clear();
        topCourse.getData().add(series);

    }

    public double calculatePercentageIncrease(int thisMonth, int lastMonth) {
        if (lastMonth == 0) {
            return thisMonth > 0 ? 100.0 : 0.0;
        }
        return ((double)(thisMonth - lastMonth) / lastMonth) * 100;
    }


    public void setupPieChart() {
        scoreDB = new ScoreDB();
        double pass = scoreDB.calculatePercentageAboveMinScore(userID);
        double fail = scoreDB.calculatePercentageBelowMinScore(userID);
        int belowAvg = scoreDB.countStudentsBelowAverage(userID);

        PieChart.Data slice1 = new PieChart.Data("Pass", pass);
        PieChart.Data slice2 = new PieChart.Data("Fail", fail);

        pie_chart_std.getData().addAll(slice1, slice2);
        pie_chart_std.setLegendVisible(false);
        slice1.getNode().setStyle("-fx-pie-color: #3498db;");
        slice2.getNode().setStyle("-fx-pie-color: #e74c3c;");

        this.pass_rate.setText("สอบผ่าน " + pass + "%");
        this.fail_rate.setText("สอบไม่ผ่าน " + fail + "%");

        this.lowScore.setText(belowAvg + "");


    }

    public void hoverEffect(HBox vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT);
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#0675de", 0.25)))
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

    public void hoverEffect(Label vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT);
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#0675de", 0.25)))
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

    public void hoverEffect(VBox vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT);
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#0675de", 0.25)))
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


    @FXML
    public void openPopup(ActionEvent event){
        Button clickedbtn = (Button) event.getSource();
        switch (clickedbtn.getId()){
            case "month_select":
                _openPopup(month_selector);
                break;
        }
    }

    @FXML
    public void month_selected(ActionEvent event){
        Button clickedbtn = (Button) event.getSource();
        String text = clickedbtn.getText();
        month_select.setText(text);
    }

    @FXML
    public void _openPopup(Node popup) {
        popup.setViewOrder(-1);
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

    public void closePopupAuto() {
        month_selector.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    if (month_selector.isVisible() && !month_selector.isHover()) {
                        closePopup(month_selector);
                    }
                });
            }
        });
    }


    public void closePopup(Node popup) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);

        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> popup.setVisible(false));

        fade.play();
    }

    public void displayProfileBox() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Teacher/statistics/dashboardProfile.fxml"));
            Parent root = loader.load();
            dashboardProfileController controller = loader.getController();

            controller.setProfileDetail();
            dashboard_profile.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
