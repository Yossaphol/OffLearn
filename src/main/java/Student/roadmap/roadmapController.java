package Student.roadmap;

import Student.FontLoader.FontLoader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class roadmapController implements Initializable {
    public HBox MainFrame;
    public VBox LeftWrapper;
    public HBox searhbar_container;
    public Button yourRoadmap;
    public Label category01;
    public Label category02;
    public Label category03;
    public Label category04;
    public Label category05;
    public Label category06;
    public Label category07;
    public Label category08;
    public Button sub_category_btn01;
    public Button sub_category_btn02;
    public Button sub_category_btn03;
    public Button sub_category_btn04;
    public Button sub_category_btn05;
    public Button sub_category_btn06;
    public Button sub_category_btn07;
    public Button sub_category_btn08;
    public Button sub_category_btn09;
    public Button sub_category_btn10;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();
        displayNavbar();
        displaySearchBar();

    }

    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/navBar.fxml"));
            VBox navContent = calendarLoader.load();
            LeftWrapper.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displaySearchBar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/searchBar.fxml"));
            HBox searchbarContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
