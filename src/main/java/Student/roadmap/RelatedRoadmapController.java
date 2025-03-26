package Student.roadmap;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RelatedRoadmapController implements Initializable {
    public Label name;
    public Label description;
    public Label duration;
    public Label contentProgression;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void setRelatedRoadmapName(String n){
        name.setText(n);
    }
    public void setRelatedRoadmapDescription(String n){
        description.setText(n);
    }public void setContentProgression(int n, int total){
        contentProgression.setText("เนื้อหา "+String.valueOf(n)+"/"+String.valueOf(total));
    }public void setRelatedRoadmapDuration(int n){
        duration.setText("ระยะเวลา "+String.valueOf(n)+" ชม.");
    }
}
