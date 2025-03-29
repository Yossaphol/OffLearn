package Student.Quiz;

import Teacher.quiz.QuestionItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionCardController implements Initializable {

    @FXML
    private VBox choicesSpace;

    @FXML
    private Label QuestionName;

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

        for (String choice : questionItem.getChoices()){
            RadioButton radioButton = new RadioButton(choice);
            radioButton.setToggleGroup(group);

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
