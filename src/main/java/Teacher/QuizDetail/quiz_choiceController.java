package Teacher.QuizDetail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class quiz_choiceController implements Initializable {
    public Label quiz_name;
    public Label quiz_correct_chioce;
    public  Label quiz_most_choice;
    public ImageView quiz_image;
    public  Label quiz_percent_correct;
    public Label quiz_percent_wrong;
    public HBox searhbar_container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
