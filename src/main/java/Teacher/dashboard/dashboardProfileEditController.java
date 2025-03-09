package Teacher.dashboard;

import Student.HomeAndNavigation.HomeController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
import Student.HomeAndNavigation.HomeController;
public class dashboardProfileEditController implements Initializable {
    public Button deletePic;
    public Button changePic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HomeController effect = new HomeController();
        effect.hoverEffect(changePic);
        effect.hoverEffect(deletePic);
    }
}
