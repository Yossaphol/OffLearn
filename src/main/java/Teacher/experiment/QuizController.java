package Teacher.experiment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

    @FXML
    private VBox showProblem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (int i = 1; i < 5; i ++){
                showProblem.getChildren().add(addProblem());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HBox addProblem() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/problemContent.fxml"));
        HBox problemContent = fxmlLoader.load();
        return problemContent;
    }
}
