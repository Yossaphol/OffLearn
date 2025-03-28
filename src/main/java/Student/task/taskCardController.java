package Student.task;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class taskCardController implements Initializable {
    public Label taskInformation;
    public Label taskDetail;
    public Label point;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setTaskInformation(String information, String detail, double score){
        taskInformation.setText(information);
        taskDetail.setText(detail);
        point.setText(String.valueOf(score));
    }


}
