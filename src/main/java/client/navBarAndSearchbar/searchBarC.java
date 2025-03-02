package client.navBarAndSearchbar;

import client.HomeAndNavigation.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class searchBarC implements Initializable {
    public Button allCoursebtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HomeController hm = new HomeController();
        hm.hoverEffect(allCoursebtn);
        route();
    }


    public void route(){
        Navigator nav = new Navigator();
        allCoursebtn.setOnMouseClicked(nav::courseRoute);

    }


}
