package Student.mainPage;

import Student.navBarAndSearchbar.navBarController;
import a_Session.SessionManager;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

        //ได้แล้ววุ้ววววว เดี๋ยวหา label มาใส่ username แล้วถ้ามีรูปโปรไฟล์ก็ไปดึงมาจากdbโดยหาจากusername เน้อออ
//        String username = SessionManager.getInstance().getUsername();
//        if (username != null) {
//            user.setText(username + "!");
//        } else {
//            user.setText("Guest!");
//        }
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

    public Navigator getNavigator() {
        return navigator;
    }
}
