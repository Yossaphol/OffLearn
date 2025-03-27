package Teacher.showBalance;

import Student.HomeAndNavigation.HomeController;
import Teacher.dashboard.dashboardController;
import Teacher.navigator.Navigator;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class withdrawDetailController implements Initializable {
    public Label back;
    public VBox changeAccount;
    public Button withdrawBtn;
    public VBox currentAccount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        route();
        setEffect();
    }
    
    private void setEffect(){
        HomeController ef = new HomeController();
        ef.hoverEffect(withdrawBtn);
        dashboardController d = new dashboardController();
        d.hoverEffect(currentAccount);
        d.hoverEffect(changeAccount);
    }

    private void route(){
        Navigator nav = new Navigator();
        back.setOnMouseClicked(nav::withdrawRoute);
        changeAccount.setOnMouseClicked(nav::settingRoute);
    }
}
