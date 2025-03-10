package Student.mainPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Student.HomeAndNavigation.Navigator;

public class mainPageController implements Initializable {
    @FXML
    public HBox searchbar_container;
    @FXML
    public VBox leftWrapper;
    @FXML
    public HBox content;

    private Navigator navigator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Navigator.setController(this);
        displayNavbar();
        displaySearchBar();
        displayContent("/fxml/Student/HomePage/home.fxml");
    }

    private void displayNavbar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/navBar.fxml"));
            VBox navContent = loader.load();
            leftWrapper.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displaySearchBar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/searchBar.fxml"));
            HBox searchbarContent = loader.load();
            searchbar_container.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayContent(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            HBox contentComponent = loader.load();
            content.getChildren().setAll(contentComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Navigator getNavigator() {
        return navigator;
    }
}
