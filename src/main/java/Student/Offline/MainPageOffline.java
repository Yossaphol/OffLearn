package Student.Offline;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.Navigator;
import Student.navBarAndSearchbar.navBarOffline;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageOffline implements Initializable {

    public VBox leftWrapper;
    public ScrollPane mainScrollPane;
    public HBox searchbar_container;
    public HBox content;
    private navBarOffline navCtrl;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Navigator.setOfflineController(this);
        displayNavbar();
        displaySearchBar();
        displayContent("/fxml/Student/Offline/OfflineMyCourse.fxml");
    }




    public void displayNavbar() {
        try {
            if (navCtrl == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/NavBarOffline.fxml"));
                VBox navContent = loader.load();

                navCtrl = loader.getController();
                leftWrapper.getChildren().setAll(navContent);
            }
            navCtrl.changeFontColor();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySearchBar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/searchBarOffline.fxml"));
            HBox searchbarContent = loader.load();
            searchbar_container.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hideNavbar() {
        leftWrapper.setVisible(false);
        leftWrapper.setManaged(false);
    }

    public void hideSearchBar() {
        searchbar_container.setVisible(false);
        searchbar_container.setManaged(false);
    }

    public void stopHideNavbar() {
        leftWrapper.setVisible(true);
        leftWrapper.setManaged(true);
    }

    public void stopHideSearchBar() {
        searchbar_container.setVisible(true);
        searchbar_container.setManaged(true);
    }

    public void displayContent(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            HBox contentComponent = loader.load();
            Object newController = loader.getController();
            Navigator.setCurrentContentController(newController);

            FontLoader font = new FontLoader();
            font.loadFonts();
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), content);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                content.getChildren().setAll(contentComponent);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), content);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public <T> T displayContent(String path, Class<T> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            HBox contentComponent = loader.load();
            T newController = loader.getController();

            Navigator.setCurrentContentController(newController);

            FontLoader font = new FontLoader();
            font.loadFonts();
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), content);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                content.getChildren().setAll(contentComponent);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), content);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();

            return newController;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



//    public Navigator getNavigator() {
//        return navigator;
//    }
}

