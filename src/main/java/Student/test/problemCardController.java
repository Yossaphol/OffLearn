package Student.test;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class problemCardController implements Initializable {
    public Label questionAndNumber;
    public RadioButton ans1;
    public RadioButton ans2;
    public RadioButton ans3;
    public RadioButton ans4;
    public ToggleButton ans2_1;
    public ToggleButton ans2_2;
    public ToggleButton ans2_3;
    public ToggleButton ans2_4;
    public Label questionAndNumber2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //ยังไม่ได้ทำ Multiple choice

    public void setProblemCard1(int number,String question, String choice1, String choice2, String choice3, String choice4){
        questionAndNumber.setText(number+". "+question);
        ans1.setText(choice1);
        ans2.setText(choice2);
        ans3.setText(choice3);
        ans4.setText(choice4);
        ToggleGroup ansGroup = new ToggleGroup();
        ans1.setToggleGroup(ansGroup);
        ans2.setToggleGroup(ansGroup);
        ans3.setToggleGroup(ansGroup);
        ans4.setToggleGroup(ansGroup);
    }

    public void setProblemCard2(int number,String question, String choice1, String choice2, String choice3, String choice4){
        questionAndNumber2.setText(number+". "+question);
        ans2_1.setText(choice1);
        ans2_2.setText(choice2);
        ans2_3.setText(choice3);
        ans2_4.setText(choice4);
        ToggleGroup ansGroup = new ToggleGroup();
        ans2_1.setToggleGroup(ansGroup);
        ans2_2.setToggleGroup(ansGroup);
        ans2_3.setToggleGroup(ansGroup);
        ans2_4.setToggleGroup(ansGroup);
    }

}
