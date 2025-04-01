package Student.courseManage;

import Database.*;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Student.roadmap.myRoadmapController;
import a_Session.SessionManager;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Teacher.courseManagement.CourseItem;

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
    public Label teacherName;
    public Text subjectName;
    HomeController ef = new HomeController();
    private int currentPage = 0;
    private final int coursesPerPage = 9;
    private ArrayList<CourseItem> courseList;

    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label pageLabel;
    @FXML
    private Label rm1;
    @FXML
    private Label rm2;
    @FXML
    private Label rm3;


    UserDB userDB = new UserDB();
    CourseDB courseDB = new CourseDB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setImgContainer();
        closePopupAuto();
        setEffect();
        route();
        setProgressBar();
        handleCourseItem();
        updatePageLabel();
        setupRoadmapButtons();

        previousButton.setOnAction(event -> handlePreviousButton());
        nextButton.setOnAction(event -> handleNextButton());
    }

    public void setEffect() {
        if (popup != null) ef.applyHoverEffectToInside(popup);
        if (popup1 != null) ef.applyHoverEffectToInside(popup1);
        if (popup2 != null) ef.applyHoverEffectToInside(popup2);
        if (categoryPopup != null) ef.applyHoverEffectToInside(categoryPopup);

        if (learn_now != null) ef.hoverEffect(learn_now);
        if (cat1 != null) ef.hoverEffect(cat1);
        if (cat2 != null) ef.hoverEffect(cat2);
        if (cat3 != null) ef.hoverEffect(cat3);
        if (roadmapRecommendContainer != null) ef.hoverEffect(roadmapRecommendContainer);
        if (explore != null) ef.hoverEffect(explore);

        if (categoryPopup != null) categoryPopup.setViewOrder(-1);
        if (popup != null) popup.setViewOrder(-1);
        if (popup1 != null) popup1.setViewOrder(-1);
        if (popup2 != null) popup2.setViewOrder(-1);
        if (category_recommend != null) category_recommend.setViewOrder(0);
    }



    @FXML
    private void openPopup(ActionEvent event) {
        Button clickedbtn = (Button) event.getSource();
        switch (clickedbtn.getId()) {
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
            if (popup.getId().equals("categoryPopup")) {
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
            if (popup.getId().equals("categoryPopup")) {
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

    public void closePopupAuto() {
        MainFrame.setOnMouseClicked(event -> {
            if (popup.isVisible()) closePopup(popup);
            if (popup1.isVisible()) closePopup(popup1);
            if (popup2.isVisible()) closePopup(popup2);
            if (categoryPopup.isVisible()) {
                seeAll.setText("ดูทั้งหมด");
                closePopup(categoryPopup);
            }
        });
    }

    private void closePopup(Node popup) {
        FadeTransition fade2 = new FadeTransition(Duration.millis(300), category_recommend);
        if (popup.getId().equals("categoryPopup")) {
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
        if (progressCategory != null) {
            categorybar.setProgress(Double.parseDouble(progressCategory.getText().replace("% completed", "").trim()) / 100);
        }
        if (progressCategory1 != null) {
            categorybar1.setProgress(Double.parseDouble(progressCategory1.getText().replace("% completed", "").trim()) / 100);
        }
        if (progressCategory2 != null) {
            categorybar2.setProgress(Double.parseDouble(progressCategory2.getText().replace("% completed", "").trim()) / 100);
        }
    }


    public void handleCourseItem() {
        CourseDB courseDB = new CourseDB();
        courseList = courseDB.getAllCourses();
        renderCourses();
    }

    private void renderCourses() {
        allCourseContainer.getChildren().clear();
        int start = currentPage * coursesPerPage;
        int end = Math.min(start + coursesPerPage, courseList.size());

        int col = 0, row = 0;
        for (int i = start; i < end; i++) {
            CourseItem courseItem = courseList.get(i);
            courseObject course = courseItem.toCourseObject();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/enrollCourseItem.fxml"));
                Node courseItemNode = loader.load();

                EnrollCourseItemController controller = loader.getController();
                controller.setData(course);
                controller.setCourseName(course.getName());
                controller.setShortDescription("");
                controller.setTeacherName(course.getTeacherName());
                controller.setTeacherImg("/img/Profile/man.png");
                controller.setCategoryName(course.getCategoryName());
                String imageUrl = course.getPicture();
                if (imageUrl == null || imageUrl.trim().isEmpty()) {
                    imageUrl = "/img/Picture/bg.jpg";
                }
                controller.setCourseImg(imageUrl);

                ef.hoverEffect(courseItemNode);
                allCourseContainer.add(courseItemNode, col, row);
                GridPane.setMargin(courseItemNode, new Insets(30, 30, 30, 30));

                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePageLabel() {
        int totalPages = (int) Math.ceil((double) courseList.size() / coursesPerPage);
        pageLabel.setText((currentPage + 1) + "/" + totalPages);
    }

    private void setupRoadmapButtons() {
        rm1.setOnMouseClicked(event -> handleRoadmapClick("Frontend Development"));
        rm2.setOnMouseClicked(event -> handleRoadmapClick("Backend Development"));
        rm3.setOnMouseClicked(event -> handleRoadmapClick("Web Security"));
    }

    private void handleRoadmapClick(String roadmapName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/myroadmap.fxml"));
            Parent root = loader.load();

            // ดึง controller ของ myRoadmapController
            myRoadmapController controller = loader.getController();

            // ส่ง roadmapID ที่ตรงกับชื่อ
            if (roadmapName.equals("Frontend Development")) {
                controller.setRoadmapID("00000002"); // แก้ให้ตรงกับ ID ใน DB
            } else if (roadmapName.equals("Backend Development")) {
                controller.setRoadmapID("00000003");
            } else if (roadmapName.equals("Web Security")) {
                controller.setRoadmapID("00000004");
            }

            Stage stage = new Stage();
            stage.setTitle("Roadmap - " + roadmapName);
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void route() {
        Navigator nav = new Navigator();
        explore.setOnMouseClicked(nav::roadmapRoute1);
    }




    private void handlePreviousButton() {
        if (currentPage > 0) {
            currentPage--;
            renderCourses();
            updatePageLabel();
        }
    }

    private void handleNextButton() {
        if ((currentPage + 1) * coursesPerPage < courseList.size()) {
            currentPage++;
            renderCourses();
            updatePageLabel();
        }
    }
    public void setImgContainer() {
        CourseItem courseItem = courseDB.getLatestCourse();

        String profile = userDB.getUserNameProfileAndSpecByCourseID(courseItem.getCourseId())[1];
        String banner ;
        if (courseItem.getCourseImg() == null){
            banner = "/img/Picture/florian-olivo-4hbJ-eymZ1o-unsplash.jpg";
        } else {
            banner = courseItem.getCourseImg();
        }

        setImageToShape(teacherPic, profile);
        setImageToShape(imgContainer, banner);

        subjectName.setText(courseItem.getCourseName());

        teacherName.setText(userDB.getUserNameProfileAndSpecByCourseID(courseItem.getCourseId())[0]);


    }
    public boolean isURL(String path) {
        return path != null && (path.startsWith("http://") || path.startsWith("https://"));
    }

    public boolean isResource(String path) {
        return getClass().getResource(path) != null;
    }

    public void setImageToShape(Shape shape, String path) {
        Image image;

        if (isURL(path)) {
            image = new Image(path);
        } else if (isResource(path)) {
            image = new Image(getClass().getResource(path).toExternalForm());
        } else {
            System.out.println("Invalid path: " + path);
            return;
        }

        shape.setFill(new ImagePattern(image));
    }
}
