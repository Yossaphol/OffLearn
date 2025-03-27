package Teacher.showBalance;

import Student.HomeAndNavigation.HomeController;
import Teacher.navigator.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class withdrawController implements Initializable {

    @FXML
    public HBox navbarContainer;
    public Button withdrawBtn;
    public Button edit;
    public Button withdrawHistory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayNavbar();

        setEffect();
        route();

    }

    private void route(){
        Navigator nav = new Navigator();
        edit.setOnMouseClicked(nav::settingRoute);
        withdrawBtn.setOnMouseClicked(nav::withdrawDetailRoute);
        withdrawHistory.setOnMouseClicked(nav::withdrawHistoryRoute);
    }

    private void setEffect(){
        HomeController hm = new HomeController();
        hm.hoverEffect(withdrawBtn);
        hm.hoverEffect(withdrawHistory);
    }

    public void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            navbarContainer.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
