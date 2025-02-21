package HomeAndNavigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Navigator {

    public void dashboardRoute(HBox hBox) throws IOException {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
            hBox.getChildren().setAll(newPane);
    }

    public void courseRoute(HBox hBox) throws IOException {
        Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        hBox.getChildren().setAll(newPane);
    }

    public void inboxRoute(HBox hBox) throws IOException {
        Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/pChat.fxml"));
        hBox.getChildren().setAll(newPane);
    }

    public void taskRoute(HBox hBox) throws IOException {
        Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/task.fxml"));
        hBox.getChildren().setAll(newPane);
    }

    public void roadmapRoute(HBox hBox) throws IOException {
        Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/roadmap.fxml"));
        hBox.getChildren().setAll(newPane);
    }

}

