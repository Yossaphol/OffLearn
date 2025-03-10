package Student.leaderboard;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class leaderboardController implements Initializable {

    public HBox searhbar_container;
    public VBox leftWrapper;
    public Label welcomeText;
    public VBox leaderboardTable_container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeText.setText("Want to see your name on the leaderboard?\n" +
                "Join a course and test your skills!");

    displayLeaderboardTable();
    }




    private void displayLeaderboardTable(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/leaderboardTable.fxml"));
            VBox searchbarContent = calendarLoader.load();
            leaderboardTable_container.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
