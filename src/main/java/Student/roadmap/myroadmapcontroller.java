package Student.roadmap;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class myroadmapcontroller implements Initializable {
    public VBox sideBar;
    public HBox bg;
    public Pane searchBar;
    public ScrollPane mainScrollPane;
    public HBox rootpage;
    public VBox content_container;
    public HBox content;
    public VBox myroadmap_container;
    public HBox roadmap_header_container;
    public Label roadmap_header_text;
    public HBox hearted_roadmap_container;
    public Pane hearted_roadmap_box_1;
    public VBox hearted_roadmap_nameprobar_container_1;
    public Label hearted_roadmap_1;
    public ProgressBar hearted_roadmap_probar_1;
    public Circle heart_Button_1;
    public Pane hearted_roadmap_box_2;
    public VBox hearted_roadmap_nameprobar_container_2;
    public Label hearted_roadmap_2;
    public ProgressBar hearted_roadmap_probar_2;
    public Circle heart_Button_2;
    public Pane hearted_roadmap_box_3;
    public HBox hearted_roadmap_nameprobar_container_3;
    public Label hearted_roadmap_3;
    public ProgressBar hearted_roadmap_probar_3;
    public Circle heart_Button_3;
    public Pane selected_roadmap_box;
    public Label selected_roadmap_name;
    public Label selected_roadmap_teacher_name;
    public Circle selected_roadmap_teacher_picture;
    public Label selected_roadmap_teacher_title;
    public Label selected_roadmap_createby_text;
    public Label selected_roadmap_description;
    public Label selected_roadmap_progrgressfraction_header;
    public Label selected_roadmap_time_header;
    public Label selected_roadmap_time;
    public Label selected_roadmap_progrgressfraction;
    public Label selected_roadmap_progrgresspercent;
    public Label selected_roadmap_progrgresspercent_header;
    public Pane pretest_container;
    public HBox pretest_box;
    public Button pretest_button_text;
    public ImageView pretest_button_arrow;
    public VBox roadmap_optional_extra_container;
    public HBox roadmap_node_container;
    public VBox raodmap_node_column1;
    public VBox raodmap_node_1;
    public HBox raodmap_node_node_box_1;
    public Label raodmap_node_header_1;
    public Label raodmap_node_discription;
    public HBox raodmap_node_button_box_1;
    public Button raodmap_node_button_1;
    public VBox raodmap_node_column_2;
    public VBox raodmap_node_2;
    public HBox raodmap_node_header_container_2;
    public Label raodmap_node_header_2;
    public Label raodmap_node_discription_2;
    public HBox raodmap_node_button_box_2;
    public Button raodmap_node_button_2;
    public HBox optinal_course_container;
    public Pane optinal_course_box;
    public VBox optinal_course_content_container;
    public Label optinal_course_header;
    public Label optinal_course_name_1;
    public ImageView optinal_course_button;
    public Label optinal_course_description_1;
    public Label extra_roadmap_header;
    public HBox extra_roadmap_button_container;
    public HBox extra_roadmap_button_box_1;
    public Button extra_roadmap_button_1;
    public HBox extra_roadmap_button_box_2;
    public Button extra_roadmap_button_2;
    public VBox related_roadmap_container;
    public HBox related_roadmap_header_container;
    public Label related_roadmap_header;
    public Pane related_roadmap_box_1;
    public Label related_roadmap_description_1;
    public Label related_roadmap_time_1;
    public Label related_roadmap_progressfraction_1;
    public ImageView related_roadmap_button_1;
    public Label related_roadmap_name_1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
