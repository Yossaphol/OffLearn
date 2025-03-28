package Teacher.dashboard;

import Database.CourseDB;
import Database.UserDB;
import Teacher.courseManagement.CourseItem;
import Teacher.courseManagement.CourseListInDash;
import a_Session.SessionHadler;
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
import java.util.ResourceBundle;

import Student.HomeAndNavigation.HomeController;
import Teacher.navigator.Navigator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class dashboardController implements Initializable, SessionHadler {
    @FXML
    private HBox searhbar_container;

    @FXML
    private HBox dashboard_profile;

    @FXML
    private HBox course_container_table;

    @FXML
    private BarChart revenueChart;

    @FXML
    private CategoryAxis xAxis_revenue;

    @FXML
    private NumberAxis yAxis_revenue;

    @FXML
    private LineChart lineChart_enroll;

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
    private Button save_change;

    private CourseDB courseDB;
    private int userID;
    private ArrayList<CourseItem> courseItemList;

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
        setupStdChart();
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
        for (CourseItem c : courseItemList){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseListInDash.fxml"));
                HBox courseList = fxmlLoader.load();

                CourseListInDash courseListInDash = fxmlLoader.getController();

                courseListInDash.setDisplay(c.getCourseImg(), c.getCourseName(), c.getCourseCat(), 0);

                myCourseList.getChildren().add(courseList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupRevenueChart() {
        if (xAxis_revenue == null || yAxis_revenue == null || revenueChart == null) {
            System.out.println("FXML components not setup properly!");
            return;
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Sales");

        series.getData().add(new XYChart.Data<>("Course A", 50));
        series.getData().add(new XYChart.Data<>("Course B", 70));
        series.getData().add(new XYChart.Data<>("Course C", 30));

        revenueChart.setLegendVisible(false);
        revenueChart.getData().add(series);

        Platform.runLater(() -> {
            series.getData().get(0).getNode().setStyle("-fx-bar-fill: #3498db;");
            series.getData().get(1).getNode().setStyle("-fx-bar-fill: #e74c3c;");
            series.getData().get(2).getNode().setStyle("-fx-bar-fill: #2ecc71;");
        });
    }

    public void setupLinechartEnroll() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Enrollment");


        series.getData().add(new XYChart.Data<>("Course1", 30));
        series.getData().add(new XYChart.Data<>("Course2", 50));
        series.getData().add(new XYChart.Data<>("Course3", 10));
        series.getData().add(new XYChart.Data<>("Course4", 30));
        series.getData().add(new XYChart.Data<>("Course5", 60));

        lineChart_enroll.setLegendVisible(false);
        lineChart_enroll.setCreateSymbols(false);
        lineChart_enroll.getData().clear();
        lineChart_enroll.getData().add(series);

        Platform.runLater(() -> {
            series.getNode().setStyle("-fx-stroke: #0675de; -fx-stroke-width: 2px;"); // Change line color
        });
    }

    public void setupStdChart() {
        PieChart.Data slice1 = new PieChart.Data("Pass", 64);
        PieChart.Data slice2 = new PieChart.Data("Fail", 36);

        pie_chart_std.getData().addAll(slice1, slice2);
        pie_chart_std.setLegendVisible(false);
        slice1.getNode().setStyle("-fx-pie-color: #3498db;");
        slice2.getNode().setStyle("-fx-pie-color: #e74c3c;");

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

            controller.setProfileDetail(
                    "Wirayabovorn Boonpriam",
                    "Junior software developer",
                    "/img/Picture/computer.jpg",
                    674,
                    210,
                    4);

            dashboard_profile.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
