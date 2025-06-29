package Student.mainPage;

import Student.Quiz.ResultPageController;
import Student.courseManage.courseEnrollController;
import Student.courseManage.courseObject;
import Student.navBarAndSearchbar.navBarController;
import a_Session.SessionManager;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Student.HomeAndNavigation.Navigator;
import javafx.util.Duration;
import Student.FontLoader.FontLoader;

public class mainPageController implements Initializable {
    @FXML
    public HBox searchbar_container;
    @FXML
    public VBox leftWrapper;
    @FXML
    public HBox content;

    private navBarController navCtrl;

//    @FXML
//    public Label user;

    private Navigator navigator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Navigator.setController(this);
        displayNavbar();
        displaySearchBar();
        displayContent("/fxml/Student/HomePage/home.fxml");
    }


    public void displayNavbar() {
        try {
            if (navCtrl == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/navBar.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/searchBar.fxml"));
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

    public void displayContent(courseObject course) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/courseEnroll.fxml"));
            HBox contentComponent = loader.load();
            courseEnrollController controller = loader.getController();
            Navigator.setCurrentContentController(controller);
            controller.setCourse(course);
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

    public void displayContent(Parent contentComponent) {
        if (content == null) {
            System.out.println("Error: 'content' container is null. Cannot display content.");
            return;
        }

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



    public Navigator getNavigator() {
        return navigator;
    }
}
