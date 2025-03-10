package Student.dashboard;

import Student.HomeAndNavigation.Navigator;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class dashboardTaskController implements Initializable {
    public Button continue_task;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         route();
    }

    public void route(){
        Navigator nav = new Navigator();
        //continue_task.setOnMouseClicked(nav::taskRoute);
    }
}
