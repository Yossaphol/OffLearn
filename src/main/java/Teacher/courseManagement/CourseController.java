package Teacher.courseManagement;

import Database.CourseDB;
import Student.HomeAndNavigation.HomeController;
import Teacher.dashboard.dashboardController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CourseController implements Initializable {

    public LineChart enrollmentStatistics;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    public VBox chartContainer;
    public Rectangle arrow;
    @FXML
    private HBox navBar;

    @FXML
    private Button newCourse;

    @FXML
    private ScrollPane wrapper;

    @FXML
    private VBox courseManagement;

    @FXML
    private VBox courseListView;

    @FXML
    private Label count;

    private VBox courseEdit;
    private CourseDB courseDB;
    private int userID = 00000001;
    private ArrayList<CourseItem> courseList;

    private boolean increase = true;

    dashboardController d = new dashboardController();
    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();
        newCourseButton();
        showCourseOnListview();
        setUpLineChart();
        setEffect();

        setArrow();
    }

    private void setArrow(){
        if (increase){
            ef.loadAndSetImage(arrow, "/img/icon/arrowup.png");
        }else{
            ef.loadAndSetImage(arrow, "/img/icon/arrowdown.png");
        }
    }

    private void setEffect(){
        d.hoverEffect(chartContainer);
        ef.hoverEffect(newCourse);
    }


    //Chart with exmaple data
    public void setUpLineChart() {
        enrollmentStatistics.setLegendVisible(false);

        enrollmentStatistics.getData().clear();

        enrollmentStatistics.setStyle("-fx-background-color: transparent;");
        enrollmentStatistics.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        enrollmentStatistics.lookup(".chart").setStyle("-fx-background-color: transparent;");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Jan", 150));
        series.getData().add(new XYChart.Data<>("Feb", 180));
        series.getData().add(new XYChart.Data<>("Mar", 220));
        series.getData().add(new XYChart.Data<>("Apr", 170));
        series.getData().add(new XYChart.Data<>("May", 250));

        enrollmentStatistics.getData().add(series);

        Platform.runLater(() -> {
            if (series.getNode() != null) {
                series.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #0675DE; -fx-stroke-width: 2px;");
            }

            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-background-color: transparent;");
                }
            }
        });
    }


    @FXML
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            navBar.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void passWrapper(CourseEditController courseEditController){
        courseEditController.recieveWrapper(wrapper);
    }

    public void passCourseManagement(CourseEditController courseEditController){
        courseEditController.recieveCourseList(courseManagement);
    }

    public void passWrapperMyCourse(CourseListController courseListController){
        courseListController.recieveWrapper(wrapper);
    }

    public void passCourseManagementMyCourse(CourseListController courseListController){
        courseListController.recieveCourseManagement(courseManagement);
    }

    public void passMyMethod(CourseEditController courseEditController){courseEditController.recieveMethod(this);}

    public void newCourseButton(){
        newCourse.setOnAction(actionEvent -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseEdit.fxml"));
                courseEdit = fxmlLoader.load();
                wrapper.setContent(courseEdit);

                CourseEditController courseEditController = fxmlLoader.getController();

                passWrapper(courseEditController);
                passCourseManagement(courseEditController);
                passMyMethod(courseEditController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showCourseOnListview() {
        courseListView.getChildren().clear();
        this.courseDB = new CourseDB();
        courseList = courseDB.getMyCourse(userID);

        count.setText(courseList.size() + "");

        if (courseList.isEmpty()) {
            Label noCourseLabel = new Label("ไม่มีหลักสูตรที่จะแสดง");
            courseListView.getChildren().add(noCourseLabel);
            return;
        }

        for (CourseItem c : courseList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseList.fxml"));
                Parent courseItemParent = fxmlLoader.load();

                CourseListController controller = fxmlLoader.getController();

                controller.setCourseItem(c);
                controller.setCourseDisplay();
                controller.setCourseId(c.getCourseId());

                passCourseManagementMyCourse(controller);
                passWrapperMyCourse(controller);

                d.hoverEffect(courseListView);

                courseListView.getChildren().add(courseItemParent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
