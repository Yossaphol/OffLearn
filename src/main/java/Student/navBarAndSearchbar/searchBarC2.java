package Student.navBarAndSearchbar;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import Student.HomeAndNavigation.*;
import java.net.URL;
import java.util.ResourceBundle;

public class searchBarC2 implements Initializable {
    public Circle pfp;
    public Pane pf_box;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HomeController hm = new HomeController();

        hm.loadAndSetImage(pfp, "/img/Profile/man.png");
        hm.hoverEffect(pf_box);

    }
}
