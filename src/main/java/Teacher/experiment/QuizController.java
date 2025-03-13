package Teacher.experiment;

import Teacher.courseManagement.CourseEditController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

    @FXML
    private VBox problemSpace;

    @FXML
    private Label addProblem;

    @FXML
    private Label back;

    private ScrollPane wrapper;
    private VBox courseManagement;
    private HBox problemContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProblemButton();
        backButton();
    }

    public void addProblemButton(){
        addProblem.setOnMouseClicked(mouseEvent -> {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/problemContent.fxml"));
            problemContent = fxmlLoader.load();
                problemSpace.getChildren().add(problemContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void backButton(){
        back.setOnMouseClicked(mouseEvent -> {
            wrapper.setContent(courseManagement);
        });
    }

    public void recieveWrapper(ScrollPane wrapper){
        this.wrapper = wrapper;
    }

    public void recieveCourseManagement(VBox courseManagement){
        this.courseManagement = courseManagement;
    }
}

