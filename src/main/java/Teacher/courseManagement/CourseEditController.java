package Teacher.courseManagement;

import Student.FontLoader.FontLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseEditController implements Initializable {

    public VBox searhbar_container;
    public TextField courseName;
    public ChoiceBox courseCategoty;
    public TextArea courseDetail;
    public TextField coursePrice;
    public Pane navBarContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();
        displayNavbar();
    }

    @FXML
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
