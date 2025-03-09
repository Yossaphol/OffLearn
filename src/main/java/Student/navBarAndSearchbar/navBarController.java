package Student.navBarAndSearchbar;

import Student.HomeAndNavigation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class navBarController implements Initializable {

    @FXML
    public HBox logo;
    public HBox dashboard;
    public HBox home;
    public HBox course;
    public HBox inbox;
    public HBox roadmap;
    public Button yourCoursebtn;
    public VBox calendarContainer;
    public HBox task;
    public Button roadmap_btn;
    public Button task_btn;
    public Button inbox_btn;
    public Button course_btn;
    public Button home_btn;
    public Button dashboard_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nav
        HomeController method_home = new HomeController();
        method_home.hoverEffect(dashboard);
        method_home.hoverEffect(course);
        method_home.hoverEffect(inbox);
        method_home.hoverEffect(task);
        method_home.hoverEffect(roadmap);
        method_home.hoverEffect(home);
        method_home.hoverEffect(yourCoursebtn);
        method_home.hoverEffect(calendarContainer);
        route();
        calendarDisplay();
    }

    private void route(){
        Navigator nav = new Navigator();

        //Home
        home.setOnMouseClicked(nav::homeRoute);
        home_btn.setOnMouseClicked(nav::homeRoute);
        logo.setOnMouseClicked(nav::homeRoute);

        //Dashboard
        dashboard.setOnMouseClicked(nav::dashboardRoute);
        dashboard_btn.setOnMouseClicked(nav::dashboardRoute);

        //Course
        course.setOnMouseClicked(nav::courseRoute);
        course_btn.setOnMouseClicked(nav::courseRoute);

        //My Course
        yourCoursebtn.setOnMouseClicked(nav::myCourseRoute);
        calendarContainer.setOnMouseClicked(nav::myCourseRoute);


        //Inbox
        inbox.setOnMouseClicked(nav::inboxRoute);
        inbox_btn.setOnMouseClicked(nav::inboxRoute);

        //Task
        task.setOnMouseClicked(nav::taskRoute);
        task_btn.setOnMouseClicked(nav::taskRoute);

        //Roadmap
        roadmap.setOnMouseClicked(nav::roadmapRoute);
        roadmap_btn.setOnMouseClicked(nav::roadmapRoute);

    }
    private void calendarDisplay(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/HomePage/CustomCalendar.fxml"));
            VBox calendarContent = calendarLoader.load();
            calendarContainer.getChildren().setAll(calendarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeFontColor(ActionEvent event) {
        Button clickedBtn = (Button) event.getSource();

        List<Button> buttons = Arrays.asList(home_btn, dashboard_btn, course_btn, inbox_btn, task_btn, roadmap_btn);

        for (Button btn : buttons) {
            btn.setTextFill(Color.BLACK);
        }
        clickedBtn.setTextFill(Color.web("#8100cc"));
    }


}
