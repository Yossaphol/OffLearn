package Teacher.experiment;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class quizBoxContent implements Initializable {

    @FXML
    private Label count;

    @FXML
    private Label minScore;

    @FXML
    private Label maxScore;

    @FXML
    private Label quizName;

    @FXML
    private ImageView delete;

    @FXML
    private HBox quizBox;

    private VBox parentContainer;
    private ArrayList<ArrayList<QuizItem>> lqg;
    private ArrayList<QuizItem> quizItemsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteButton();
    }

    public void setQuizName(String name){
        this.quizName.setText(name);
    }

    public void setCount(String count){
        this.count.setText(count);
    }

    public void setMinScore(String minScore){
        this.minScore.setText(minScore);
    }

    public void setMaxScore(String maxScore){
        this.maxScore.setText(maxScore);
    }

    public void deleteButton(){
        delete.setOnMouseClicked(mouseEvent -> {
            lqg.remove(this.quizItemsList);
            if (parentContainer != null && quizBox != null) {
                parentContainer.getChildren().remove(quizBox);
            }
        });
    }

    public void setParentContainer(VBox parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setProblemContent(HBox quizBox) {
        this.quizBox = quizBox;
    }

    public void recieveLQG(ArrayList<ArrayList<QuizItem>> lqg){ this.lqg = lqg;}

    public void recieveQuizItemList(ArrayList<QuizItem> quizItemsList){ this.quizItemsList = quizItemsList;}
}
