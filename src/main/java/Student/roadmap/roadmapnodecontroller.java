package Student.roadmap;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class roadmapnodecontroller implements Initializable {
    public VBox roadmap_node;
    public HBox roadmap_node_node_box;
    public Label roadmap_node_header;
    public Label roadmap_node_discription;
    public HBox roadmap_node_button_box;
    public Button roadmap_node_button;
    public Label subjectName;
    public Label subjectDescription;
    public Label orderNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setOrderNumber(int num){
        orderNumber.setText(String.valueOf(num));
    }

    public void setDescription(String txt){
        subjectName.setText(txt);
    }

    public void setSubjectName(String name){
        subjectName.setText(name);
    }
}
