package client.roadmap;

import client.FontLoader.FontLoader;
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
        displayNavbar();
        displaySearchBar();

    }

    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/client/NavAndSearchbar/navBar.fxml"));
            VBox navContent = calendarLoader.load();
            LeftWrapper.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displaySearchBar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/client/NavAndSearchbar/searchBar.fxml"));
            HBox searchbarContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
