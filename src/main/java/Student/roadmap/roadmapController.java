package Student.roadmap;

import Student.FontLoader.FontLoader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class roadmapController implements Initializable {
    public HBox MainFrame;
    public VBox LeftWrapper;
    public HBox searhbar_container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();

    }


}
