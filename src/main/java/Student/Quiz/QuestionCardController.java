package Student.Quiz;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionCardController implements Initializable {
    public Label questionAndNumber2;
    public ToggleButton ans2_1;
    public ToggleButton ans2_2;
    public ToggleButton ans2_3;
    public ToggleButton ans2_4;
    public Label questionAndNumber;
    public RadioButton ans1;
    public RadioButton ans2;
    public RadioButton ans3;
    public RadioButton ans4;
    public Label corectness;
    public Label corectness2;
    public Label correctAns;
    public Label correctAns2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //ยังเหลือเช็คเงื่อนไขอื่นๆ และกำหนดสี

    public void setProblemCard1(int number, String question, String choice1, String choice2, String choice3, String choice4, String Correctness, String selectedChoice, String CorrectedAns) {
        questionAndNumber.setText(number + ". " + question);
        ans1.setText(choice1);
        ans2.setText(choice2);
        ans3.setText(choice3);
        ans4.setText(choice4);


        ToggleGroup ansGroup = new ToggleGroup();
        ans1.setToggleGroup(ansGroup);
        ans2.setToggleGroup(ansGroup);
        ans3.setToggleGroup(ansGroup);
        ans4.setToggleGroup(ansGroup);

        corectness.setText(Correctness);
        if (Correctness.equals("ถูก")) {
            corectness.setStyle("-fx-text-fill: green;");
        } else {
            corectness.setStyle("-fx-text-fill: red;");
        }

        if (!selectedChoice.equals("")) {
            if (selectedChoice.equals(ans1.getText())) {
                ans1.setSelected(true);
            } else if (selectedChoice.equals(ans2.getText())) {
                ans2.setSelected(true);
            } else if (selectedChoice.equals(ans3.getText())) {
                ans3.setSelected(true);
            } else if (selectedChoice.equals(ans4.getText())) {
                ans4.setSelected(true);
            }
        }
        correctAns.setText("คำตอบ: "+CorrectedAns);


    }

    public void setProblemCard2(int number, String question, String choice1, String choice2, String choice3, String choice4, String Correctness, String selectedChoice, String CorrectedAns) {
        questionAndNumber2.setText(number + ". " + question);
        ans2_1.setText(choice1);
        ans2_2.setText(choice2);
        ans2_3.setText(choice3);
        ans2_4.setText(choice4);

        ToggleGroup ansGroup = new ToggleGroup();
        ans2_1.setToggleGroup(ansGroup);
        ans2_2.setToggleGroup(ansGroup);
        ans2_3.setToggleGroup(ansGroup);
        ans2_4.setToggleGroup(ansGroup);

        corectness2.setText(Correctness);
        if (Correctness.equals("ถูก")) {
            corectness2.setStyle("-fx-text-fill: green;");
        } else {
            corectness2.setStyle("-fx-text-fill: red;");
        }

        if (!selectedChoice.equals("")) {
            if (selectedChoice.equals(ans2_1.getText())) {
                ans2_1.setSelected(true);
            } else if (selectedChoice.equals(ans2_2.getText())) {
                ans2_2.setSelected(true);
            } else if (selectedChoice.equals(ans2_3.getText())) {
                ans2_3.setSelected(true);
            } else if (selectedChoice.equals(ans2_4.getText())) {
                ans2_4.setSelected(true);
            }
        }

        correctAns2.setText("คำตอบ: "+CorrectedAns);

    }
}
