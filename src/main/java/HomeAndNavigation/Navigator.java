package HomeAndNavigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Navigator {

    public void homeRoute(MouseEvent event, HBox hBox){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void dashboardRoute(MouseEvent event, HBox hBox){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void courseRoute(MouseEvent event,HBox hBox){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/course.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void inboxRoute(MouseEvent event,HBox hBox){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inbox/pChat.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void taskRoute(MouseEvent event,HBox hBox){
        try{
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/task.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void roadmapRoute(MouseEvent event,HBox hBox){
        try{
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/roadmap.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

