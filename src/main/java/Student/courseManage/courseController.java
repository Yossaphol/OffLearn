package Student.courseManage;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class courseController implements Initializable {
    @FXML
    public VBox leftWrapper;
    public HBox rootPage;
    public HBox searchbar_container;
    public Rectangle imgContainer;
    public Circle teacherPic;
    public VBox popup;
    public VBox popup1;
    public VBox popup2;
    public VBox categoryPopup;
    public HBox MainFrame;
    public Button seeAll;
    public HBox category_recommend;
    public Pane cat1;
    public Pane cat2;
    public Pane cat3;
    public Circle category_pic2;
    public Circle category_pic;
    public Circle category_pic1;
    public HBox learn_now;
    public Button explore;
    public Rectangle course_image;
    public Circle teacher_pic;
    public Pane roadmapRecommendContainer;
    public GridPane allCourseContainer;
    public Label course0Val;
    public ProgressBar course0;
    public ProgressBar categorybar;
    public Label progressCategory1;
    public ProgressBar categorybar1;
    public Label progressCategory;
    public Label progressCategory2;
    public ProgressBar categorybar2;
    HomeController ef = new HomeController();

    public void initialize(URL location, ResourceBundle resources) {
        closePopupAuto();
        setImg();
        setEffect();
        route();
        setProgressBar();
        handleCourseItem();
    }


    public void setEffect(){
        ef.applyHoverEffectToInside(categoryPopup);
        ef.applyHoverEffectToInside(popup);
        ef.applyHoverEffectToInside(popup1);
        ef.applyHoverEffectToInside(popup2);
        ef.hoverEffect(learn_now);
        ef.hoverEffect(cat1);
        ef.hoverEffect(cat2);
        ef.hoverEffect(roadmapRecommendContainer);
        ef.hoverEffect(explore);
        ef.hoverEffect(cat3);
        categoryPopup.setViewOrder(-1);
        category_recommend.setViewOrder(0);
        popup.setViewOrder(-1);
        popup1.setViewOrder(-1);
        popup2.setViewOrder(-1);


    }

    public void route(){
        Navigator nav = new Navigator();
        explore.setOnMouseClicked(nav::roadmapRoute);
    }

    public void setImg(){
        HomeController hm = new HomeController();
        hm.loadAndSetImage(imgContainer, "/img/Picture/bg.jpg");

        hm.loadAndSetImage(teacherPic, "/img/Profile/man.png");
        hm.loadAndSetImage(category_pic, "/img/icon/code.png");
        hm.loadAndSetImage(category_pic1, "/img/icon/partners.png");
        hm.loadAndSetImage(category_pic2, "/img/icon/artificial-intelligence.png");
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
                seeAll.setText("ปิด");
                _openPopup(categoryPopup);
        }
    }

    @FXML
    private void _openPopup(Node popup) {
        popup.toFront();
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
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
        } else {
            if(popup.getId().equals("categoryPopup")){
                seeAll.setText("ดูทั้งหมด");
                category_recommend.setVisible(true);
                category_recommend.setOpacity(0);
                fade2.setFromValue(0);
                fade2.setToValue(1);
            }
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> popup.setVisible(false));
        }
        fade.play();
        fade2.play();
    }

    public void closePopupAuto(){
        MainFrame.setOnMouseClicked(event -> {
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

        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> popup.setVisible(false));

        fade.play();
        fade2.play();
    }

    private void setProgressBar() {
        categorybar.setProgress(Double.parseDouble(progressCategory.getText().replace("% completed", "").trim()) / 100);
        categorybar1.setProgress(Double.parseDouble(progressCategory1.getText().replace("% completed", "").trim()) / 100);
        categorybar2.setProgress(Double.parseDouble(progressCategory2.getText().replace("% completed", "").trim()) / 100);

    }

    public void handleCourseItem(){
        try {
            List<String> courseList = new ArrayList<>();
            courseList.add("Math");
            courseList.add("Science");
            courseList.add("History");
            courseList.add("English");

            List<String> description = new ArrayList<>();
            description.add("Math for IT");
            description.add("Science for IT");
            description.add("History for IT");
            description.add("English for IT");

            int col = 0, row = 0;
            for (String courseName : courseList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/enrollCourseItem.fxml"));
                Node courseItem = loader.load();
                enrollCourseItem controller = loader.getController();

                //Set detail
                controller.setCourseName(courseName);
                controller.setShortDescription(description.get(col));
                controller.setTeacherName("ผศ.ดร. วิรยบวร บุญเปรี่ยม");
                controller.setTeacherImg("/img/Profile/man.png");
                controller.setCourseImg("/img/Picture/bg.jpg");


                ef.hoverEffect(courseItem);
                allCourseContainer.add(courseItem, col, row);
                GridPane.setMargin(courseItem, new Insets(10, 30, 10, 30));


                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
