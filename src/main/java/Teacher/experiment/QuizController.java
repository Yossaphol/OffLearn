package Teacher.experiment;

import Teacher.courseManagement.CourseEditController;
import com.beust.ah.A;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

    @FXML
    private VBox problemSpace;

    @FXML
    private Label addProblem;

    @FXML
    private Label back;

    @FXML
    private Button saveAll;

    private ScrollPane wrapper;
    private VBox courseManagement;
    private HBox problemContent;
    private ArrayList<QuizItem> quizItemsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProblemButton();
        backButton();
        quizItemsList = new ArrayList<QuizItem>();
        saveAllButton();
    }

    public void addProblemButton(){
        addProblem.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/problemContent.fxml"));
                problemContent = fxmlLoader.load();

                ProblemContent p = fxmlLoader.getController();
                p.setParentContainer(problemSpace);
                p.setProblemContent(problemContent);
                problemSpace.getChildren().add(problemContent);
                passQuizItemList(p);
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

    public void passQuizItemList(ProblemContent p){
        p.recieveQuizItemList(quizItemsList);
    }

    public void saveAllButton(){
        saveAll.setOnAction(actionEvent -> {
            for (QuizItem i : quizItemsList){
                System.out.println(i.toString());
            }

            wrapper.setContent(courseManagement);
        });
    }




}

