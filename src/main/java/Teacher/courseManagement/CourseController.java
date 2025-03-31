package Teacher.courseManagement;

import Database.CourseDB;
import Database.EnrollDB;
import Database.UserDB;
import Student.HomeAndNavigation.HomeController;
import Teacher.dashboard.dashboardController;
import a_Session.SessionHandler;
import a_Session.SessionHandler;
import a_Session.SessionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class CourseController implements Initializable, SessionHandler {

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

    @FXML
    private Label percent;

    @FXML
    private BarChart barChart;

    @FXML
    private Label currCount;

    @FXML
    private Label lastMonthCount;

    private VBox courseEdit;
    private CourseDB courseDB;
    private int userID;
    private String userName;
    private ArrayList<CourseItem> courseList;
    private EnrollDB enrollDB;
    private int[] enrollCount;

    private boolean increase = true;

    dashboardController d = new dashboardController();
    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.handleSession();

        System.out.println(this.userID);
        displayNavbar();
        newCourseButton();
        showCourseOnListview();
        setupBarChartEnroll();

        setArrow();
        setEffect();
    }

    @Override
    public void handleSession() {
        this.userName = SessionManager.getInstance().getUsername();

        UserDB userDB = new UserDB();
        this.userID = userDB.getUserId(userName);
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


    public void setupBarChartEnroll() {
        enrollDB = new EnrollDB();
        Map<String, Integer> topCourses = enrollDB.getTop3CoursesByEnrollment(this.userID);

        enrollCount = enrollDB.countEnrollmentsForCurrentAndLastMonth(userID);

        double percent = calculatePercentageIncrease(enrollCount[0], enrollCount[1]);
        currCount.setText(enrollCount[0] + "");
        lastMonthCount.setText(enrollCount[1] + "");

        this.percent.setText(percent + "%");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Enrollment Courses");


        for (Map.Entry<String, Integer> entry : topCourses.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.setLegendVisible(false);
        barChart.getData().clear();
        barChart.getData().add(series);

    }

    public double calculatePercentageIncrease(int thisMonth, int lastMonth) {
        if (lastMonth == 0) {
            return thisMonth > 0 ? 100.0 : 0.0;
        }
        return ((double)(thisMonth - lastMonth) / lastMonth) * 100;
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
        courseEditController.recieveCourseManagement(courseManagement);
    }

    public void passWrapperMyCourse(CourseListController courseListController){
        courseListController.recieveWrapper(wrapper);
    }

    public void passCourseManagementMyCourse(CourseListController courseListController){
        courseListController.recieveCourseManagement(courseManagement);
    }

    public void passMyMethod(CourseEditController courseEditController){courseEditController.recieveMethod(this);}

    public void passMyMethodToCourseList(CourseListController courseListController){courseListController.recieveMethod(this);}

    public void passCourseListView(CourseListController courseListController){
        courseListController.recieveCourseListView(courseListView);
    }

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
                wrapper.setVvalue(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showCourseOnListview() {
        courseListView.getChildren().clear();
        this.courseDB = new CourseDB();
        courseList = courseDB.getMyCourse(userID);
        enrollDB = new EnrollDB();

        count.setText(courseList.size() + "");

        if (courseList.isEmpty()) {
            Label noCourseLabel = new Label("ไม่มีหลักสูตรที่จะแสดง");
            courseListView.getChildren().add(noCourseLabel);
            return;
        }

        for (CourseItem c : courseList) {
            System.out.println(c.getCourseId());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseList.fxml"));
                Parent courseItemParent = fxmlLoader.load();

                CourseListController controller = fxmlLoader.getController();
                int[] enrollCount = enrollDB.getRevenueAndEnrollCountByCourseId(c.getCourseId());

                controller.setCourseItem(c);
                controller.setCourseDisplay(enrollCount[1]);
                controller.setCourseId(c.getCourseId());

                passCourseListView(controller);
                passCourseManagementMyCourse(controller);
                passWrapperMyCourse(controller);
                passMyMethodToCourseList(controller);

                d.hoverEffect(courseListView);

                courseListView.getChildren().add(courseItemParent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
