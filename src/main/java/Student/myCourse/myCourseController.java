package Student.myCourse;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class myCourseController implements Initializable {

    public VBox leftWrapper;
    public HBox searchbarcontainer;
    public VBox bigcalendarContainer;
    public VBox studyTable;
    public TabPane tabPane;
    public Pane courseContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();

        Navigator nav = new Navigator();
        calendarDisplay();
        displayStudyTable();
        setTabSelectionAnimation();
        setEffect();
    }

    private void setEffect(){
        HomeController ef = new HomeController();
        ef.hoverEffect(studyTable);
        ef.hoverEffectPane(courseContainer);
    }

    private void calendarDisplay(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/customBigCalendar.fxml"));
            VBox calendarContent = calendarLoader.load();
            bigcalendarContainer.getChildren().setAll(calendarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
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


}
