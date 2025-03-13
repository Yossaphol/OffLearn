package Teacher.courseManagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CourseContent implements Initializable {

    @FXML
    private HBox courseContent;

    @FXML
    private TextField chapterName;

    @FXML
    private TextField chapDesc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Map<String, String> getChapterData() {
        Map<String, String> data = new HashMap<>();
        data.put("chapterName", chapterName.getText());
        data.put("chapDesc", chapDesc.getText());
        return data;
    }
}
