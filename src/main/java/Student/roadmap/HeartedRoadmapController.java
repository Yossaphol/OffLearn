package Student.roadmap;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HeartedRoadmapController implements Initializable {
    public Label roadmapName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setHearted_roadmap(String txt){roadmapName.setText(txt);}
}
