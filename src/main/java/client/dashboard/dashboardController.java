package client.dashboard;

import client.FontLoader.FontLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();
    }
}
