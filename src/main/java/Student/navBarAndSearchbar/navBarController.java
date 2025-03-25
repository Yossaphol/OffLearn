package Student.navBarAndSearchbar;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Student.mainPage.mainPageController;
import a_Session.SessionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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

    public String currentPage;
    public ArrayList<Button> buttons = new ArrayList<>();
    private static navBarController instance;
    public HBox logoutBtn;
    public Button logoutBtn1;
    public HBox settingBtn;
    public Button settingBtn1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        HomeController method_home = new HomeController();
        method_home.hoverEffect(dashboard);
        method_home.hoverEffect(course);
        method_home.hoverEffect(inbox);
        method_home.hoverEffect(task);
        method_home.hoverEffect(roadmap);
        method_home.hoverEffect(home);
        method_home.hoverEffect(yourCoursebtn);
        method_home.hoverEffect(settingBtn);
        method_home.hoverEffect(logoutBtn);

        route();
        Navigator.setNavBarController(this);

        buttons.add(home_btn);
        buttons.add(dashboard_btn);
        buttons.add(inbox_btn);
        buttons.add(course_btn);
        buttons.add(task_btn);
        buttons.add(roadmap_btn);
        buttons.add(settingBtn1);

        currentPage = "home_btn";
        changeFontColor();

    }

    public static navBarController getInstance() {
        return instance;
    }

    private void route() {
        Navigator nav = new Navigator();

        // Home
        home.setOnMouseClicked(nav::homeRoute);
        home_btn.setOnMouseClicked(nav::homeRoute);
        logo.setOnMouseClicked(nav::homeRoute);

        // Dashboard
        dashboard.setOnMouseClicked(nav::dashboardRoute);
        dashboard_btn.setOnMouseClicked(nav::dashboardRoute);

        // Course
        course.setOnMouseClicked(nav::courseRoute);
        course_btn.setOnMouseClicked(nav::courseRoute);

        // My Course
        yourCoursebtn.setOnMouseClicked(nav::myCourseRoute);

        // Inbox
        inbox.setOnMouseClicked(nav::inboxRoute);
        inbox_btn.setOnMouseClicked(nav::inboxRoute);

        // Task
        task.setOnMouseClicked(nav::taskRoute);
        task_btn.setOnMouseClicked(nav::taskRoute);

        // Roadmap
        roadmap.setOnMouseClicked(nav::roadmapRoute);
        roadmap_btn.setOnMouseClicked(nav::roadmapRoute);

        //Setting
        settingBtn.setOnMouseClicked(nav::settingRoute);
        settingBtn1.setOnMouseClicked(nav::settingRoute);

    }


    public void handleLogout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to sign out?");

        if (alert.showAndWait().get() == ButtonType.OK){
            SessionManager.getInstance().clearSession();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/LoginSingup/login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) logoutBtn1.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Login Page");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void changeFontColor() {
        if (!buttons.isEmpty()) {
            for (Button btn : buttons) {
                if (btn.getId().equals(currentPage)) {
                    btn.setTextFill(Color.web("#8100cc"));
                } else {
                    btn.setTextFill(Color.BLACK);
                }
            }
        }
    }

    public void setCurrentPage(String id) {
        currentPage = id;
        changeFontColor();
    }

}
