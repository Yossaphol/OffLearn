package Student.myCourse;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class myCourseController implements Initializable {

    public VBox leftWrapper;
    public HBox searchbarcontainer;
    public VBox bigcalendarContainer;
    public VBox studyTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();

        Navigator nav = new Navigator();
        calendarDisplay();
        displayStudyTable();
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
}
