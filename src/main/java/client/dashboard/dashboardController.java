package client.dashboard;

import client.FontLoader.FontLoader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import client.HomeAndNavigation.Navigator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import client.HomeAndNavigation.HomeController;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
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

        //Hover effect
        method_home.hoverEffect(dashboard);
        method_home.hoverEffect(course);
        method_home.hoverEffect(inbox);
        method_home.hoverEffect(task);
        method_home.hoverEffect(roadmap);
        method_home.hoverEffect(home);
        method_home.hoverEffect(yourCoursebtn);
        method_home.hoverEffect(calendarContainer);

        route();

        //Adjust size
        mainScrollPane.requestLayout();
        mainScrollPane.prefWidthProperty().bind(rootpage.widthProperty());
        mainScrollPane.prefHeightProperty().bind(rootpage.heightProperty());
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        calendarDisplay();
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


}
