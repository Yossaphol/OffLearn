package client.courseManage;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class courseController implements Initializable {
    public VBox leftWrapper;
    public HBox rootPage;
    public HBox searhbar_container;
    public void initialize(URL location, ResourceBundle resources) {
        displayNavbar();
        displaySearchBar();
    }
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/client/NavAndSearchbar/navBar.fxml"));
            VBox navContent = calendarLoader.load();
            leftWrapper.getChildren().setAll(navContent);
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
