package Teacher.quizDetail;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label; //add
import java.net.URL;
import java.util.ResourceBundle;

public class quiz_choiceController implements Initializable {
    @FXML public Label quiz_name;
    @FXML public Label quiz_correct_choice;
    @FXML public Label quiz_most_choice;
    @FXML public ImageView quiz_image;
    @FXML public Label quiz_percent_correct;
    @FXML public Label quiz_percent_wrong;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // สามารถเพิ่มโค้ดเริ่มต้นได้ที่นี่
    }

    public void updateStatistics(String question, String correctAnswer, String mostAnswer, double correctPercent, double wrongPercent) {
        quiz_name.setText(question);
        quiz_correct_choice.setText("คำตอบที่ถูกต้องคือ: " + correctAnswer);
        quiz_most_choice.setText("คำตอบที่ตอบเยอะที่สุดคือ: " + mostAnswer);
        quiz_percent_correct.setText(String.format("%.1f%%", correctPercent));
        quiz_percent_wrong.setText(String.format("%.1f%%", wrongPercent));
    }
}
