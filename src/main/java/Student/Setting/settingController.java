package Student.Setting;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class settingController implements Initializable {

    @FXML
    private HBox dashboard;

    @FXML
    private HBox home;

    @FXML
    private HBox course;

    @FXML
    private HBox inbox;

    @FXML
    private HBox task;

    @FXML
    private HBox roadmap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        route();
        hover();
    }

    public void hover(){
        HomeController method = new HomeController();
        method.hoverEffect(dashboard);
        method.hoverEffect(home);
        method.hoverEffect(course);
        method.hoverEffect(inbox);
        method.hoverEffect(task);
        method.hoverEffect(roadmap);
    }

    public void route(){
        Navigator nav = new Navigator();
        dashboard.setOnMouseClicked(nav::dashboardRoute);
        home.setOnMouseClicked(nav::homeRoute);
        course.setOnMouseClicked(nav::courseRoute);
        inbox.setOnMouseClicked(nav::inboxRoute);
        task.setOnMouseClicked(nav::taskRoute);
        roadmap.setOnMouseClicked(nav::roadmapRoute);
    }
}
