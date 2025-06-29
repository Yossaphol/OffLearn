package Student.Quiz;

import Teacher.quiz.QuestionItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionCardController implements Initializable {

    @FXML
    private VBox choicesSpace;

    @FXML
    private Label QuestionName;

    @FXML
    private ImageView questionImg;

    private QuestionItem questionItem;
    private ToggleGroup group;
    private Button sendButton;
    private int point;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDisplay(){
        group = new ToggleGroup();

        this.QuestionName.setText(questionItem.getQuizName());

        if (questionItem.getImg() != null){
            questionImg.setImage(new Image(questionItem.getImg()));
        }

        for (String choice : questionItem.getChoices()){
            RadioButton radioButton = new RadioButton(choice);
            radioButton.setToggleGroup(group);
            radioButton.setStyle("-fx-font-size: 18px;");

            this.choicesSpace.getChildren().add(radioButton);
        }
    }

    public void setDisplay(String corrAns){
        group = new ToggleGroup();

        this.QuestionName.setText(questionItem.getQuizName());

        for (String choice : questionItem.getChoices()){
            RadioButton radioButton = new RadioButton(choice);
            radioButton.setToggleGroup(group);
            if (choice.equals(corrAns)){
                radioButton.setStyle("-fx-text-fill: #39F077; -fx-font-size: 18px;");
            } else {
                radioButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #e0e0e0;");
            }

            this.choicesSpace.getChildren().add(radioButton);
        }
    }

    public void setQuestionItem(QuestionItem q){
        this.questionItem = q;
    }

    public void setSendButton(Button sendButton){
        this.sendButton = sendButton;
    }

    public void checkAnswer(){
        Toggle selectedToggle = group.getSelectedToggle();
        if (selectedToggle != null) {
            RadioButton selectedRadio = (RadioButton) selectedToggle;
            if (selectedRadio.getText().equals(questionItem.getCorrectChoice())){
                this.point = questionItem.getPoint();
            } else {
                this.point = 0;
            }
        } else {
            this.point = -1;
        }
    }


    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public int getPoint(){
        return this.point;
    }
}
