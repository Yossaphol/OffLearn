package Student.roadmap;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionalCourseController implements Initializable {
    public VBox optional_course_container;
    public HBox optional_course_namebutton_container;
    public Label optional_course_name;
    public ImageView optional_course_button;
    public Label optional_course_description;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void setOptional_Course_Name(String txt){optional_course_name.setText(txt);}
    public void setOptional_Course_Description(String txt){optional_course_description.setText(txt);}
}
