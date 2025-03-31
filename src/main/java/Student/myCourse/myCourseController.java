package Student.myCourse;

import Database.Category;
import Database.MyCourse;
import Database.MyProgressDB;
import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Student.learningPage.learningPageController;
import a_Session.SessionManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import Database.MyCourseDB;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class myCourseController implements Initializable {

    private VBox leftWrapper;
    private HBox searchbarcontainer;
    private VBox bigcalendarContainer;
    @FXML
    private VBox studyTable;
    @FXML
    private TabPane tabPane;
    @FXML
    private Pane courseContainer;
    @FXML
    private VBox coursecardContainer0;
    @FXML
    private VBox coursecardContainer1;
    @FXML
    private VBox coursecardContainer2;
    @FXML
    private VBox coursecardContainer3;
    @FXML
    private HBox bg;
    private MyProgressDB myProgressDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();

        HomeController homeController = new HomeController();

        myProgressDB = new MyProgressDB();

        MyCourseDB myCourseDB = new MyCourseDB();
        Category category = new Category();
        ArrayList<MyCourse> myCourses = myCourseDB.getallMyCourse();

        for (MyCourse myCourse : myCourses) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/CourseCard.fxml"));
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/CourseCard.fxml"));

                Node courseItem = loader.load();
                Node courseItem1 = loader1.load();

                courseCardController controller = loader.getController();
                courseCardController controller1 = loader1.getController();

                double progress = myProgressDB.sumChapterProgress(myCourse.getCourse_ID(),myCourse.getCat_ID(), SessionManager.getInstance().getUserID());

                controller.setCourseImage(myCourse.getImage());
                controller.setCourseCategory(category.getCatName(myCourse.getCat_ID()));
                controller.setCourseTitle(myCourse.getCourseName());
                controller.setCourseSubtitle(myCourse.getCourseDescription());
                controller.setCourseProgress(progress);

                courseItem.setOnMouseClicked(mouseEvent -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/learningPage.fxml"));
                        HBox learningPage = fxmlLoader.load();
                        bg.getChildren().clear();
                        bg.getChildren().add(learningPage);

                        learningPageController l = fxmlLoader.getController();
                        l.recieveMethod(myCourse.getCourse_ID());
                        Navigator.setCurrentContentController(l);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                controller1.setCourseImage(myCourse.getImage());
                controller1.setCourseCategory(category.getCatName(myCourse.getCat_ID()));
                controller1.setCourseTitle(myCourse.getCourseName());
                controller1.setCourseSubtitle(myCourse.getCourseDescription());
                controller1.setCourseProgress(progress);

                courseItem1.setOnMouseClicked(mouseEvent -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/learningPage.fxml"));
                        HBox learningPage = fxmlLoader.load();
                        bg.getChildren().clear();
                        bg.getChildren().add(learningPage);

                        learningPageController l = fxmlLoader.getController();
                        l.recieveMethod(myCourse.getCourse_ID());
                        Navigator.setCurrentContentController(l);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                homeController.hoverEffect(courseItem);
                homeController.hoverEffect(courseItem1);
                System.out.println(progress);
                if ( progress >= 10 && progress < 80 ) {
                    coursecardContainer1.getChildren().add(courseItem1);
                } else if ( progress >= 80 && progress <= 101 ) {
                    coursecardContainer2.getChildren().add(courseItem1);
                }
                coursecardContainer0.getChildren().add(courseItem);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        displayStudyTable();
        setTabSelectionAnimation();
        setEffect();

    }

    private void setEffect(){
        HomeController ef = new HomeController();
        ef.hoverEffect(studyTable);
        ef.hoverEffectPane(courseContainer);
    }

    public void displayStudyTable(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/studyTable.fxml"));
            VBox searchbarContent = loader.load();
            studyTable.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTabSelectionAnimation() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            int tmp = tabPane.getSelectionModel().getSelectedIndex();
            System.out.println(tabPane.getSelectionModel().getSelectedIndex());
            if (newTab != null) {
                Node content = newTab.getContent();
                if (content != null) {

                    content.setTranslateY(15);
                    content.setOpacity(0);

                    Timeline translateAnimation = new Timeline(
                            new KeyFrame(Duration.millis(450),
                                    new KeyValue(content.translateYProperty(), 0, Interpolator.EASE_OUT)
                            )
                    );

                    Timeline opacityAnimation = new Timeline(
                            new KeyFrame(Duration.millis(550),
                                    new KeyValue(content.opacityProperty(), 1, Interpolator.EASE_OUT)
                            )
                    );

                    translateAnimation.play();
                    opacityAnimation.play();
                }
            }
        });
    }

    public VBox getStudyTable() {
        return studyTable;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Pane getCourseContainer() {
        return courseContainer;
    }

    public VBox getCoursecardContainer0() {
        return coursecardContainer0;
    }

    public VBox getCoursecardContainer1() {
        return coursecardContainer1;
    }

    public VBox getCoursecardContainer2() {
        return coursecardContainer2;
    }

    public VBox getCoursecardContainer3() {
        return coursecardContainer3;
    }

    public HBox getBg() {
        return bg;
    }

    public void setStudyTable(VBox studyTable) {
        this.studyTable = studyTable;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setCourseContainer(Pane courseContainer) {
        this.courseContainer = courseContainer;
    }

    public void setCoursecardContainer0(VBox coursecardContainer0) {
        this.coursecardContainer0 = coursecardContainer0;
    }

    public void setCoursecardContainer1(VBox coursecardContainer1) {
        this.coursecardContainer1 = coursecardContainer1;
    }

    public void setCoursecardContainer2(VBox coursecardContainer2) {
        this.coursecardContainer2 = coursecardContainer2;
    }

    public void setCoursecardContainer3(VBox coursecardContainer3) {
        this.coursecardContainer3 = coursecardContainer3;
    }

    public void setBg(HBox bg) {
        this.bg = bg;
    }
}
