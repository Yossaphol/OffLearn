package Teacher.quizDetail;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class quiz_choiceController implements Initializable {
    @FXML
    public Label quiz_name;
    @FXML
    public Label quiz_correct_chioce;
    @FXML
    public  Label quiz_most_choice;
    @FXML
    public ImageView quiz_image;
    @FXML
    public  Label quiz_percent_correct;
    @FXML
    public Label quiz_percent_wrong;
    @FXML
    public HBox searhbar_container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
