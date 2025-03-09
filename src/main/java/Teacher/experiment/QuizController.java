package Teacher.experiment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

    @FXML
    private VBox showProblem;

    @FXML
    private Label addProblem;

    private HBox newProblemContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addProblem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProblem() throws IOException {
        addProblem.setOnMouseClicked(mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/problemContent.fxml"));
            try {
                newProblemContent = fxmlLoader.load();
                VBox.setVgrow(newProblemContent, Priority.ALWAYS);
                showProblem.getChildren().add(newProblemContent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
