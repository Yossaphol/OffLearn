package Teacher.courseManagement;

import Student.FontLoader.FontLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseEditController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();
    }

    @FXML
    private void displayNavBar() {
        try {
            FXMLLoader navBarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            Pane navBarContent = navBarLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Rectangle myRectangle; // เชื่อมกับ fx:id ของ Rectangle

    public void initialize() {
        myRectangle.setFill(Color.TRANSPARENT); // ทำให้โปร่งใส
    }
}
