package HomeAndNavigation;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Navigator {



    public void homeRoute(MouseEvent event, HBox hBox){
        new Thread(() -> {
            try {
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/HomePage/home.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e){
                e.printStackTrace();
            }
        }).start();

    }

    public void dashboardRoute(MouseEvent event, HBox hBox){
        new Thread(() -> {
            try {
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/statistics/dashboard.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public void courseRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try {
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/course.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public void inboxRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try {
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inbox/pChat.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void taskRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try{
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/task.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public void roadmapRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try{
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/roadmap.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void myCourseRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try{
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/myCourse.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void cartRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try{
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/cart.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void leaderboardRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try{
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/statistics/leaderboard.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void settingRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try{
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/setting/setting.fxml"));
                Platform.runLater(() -> hBox.getChildren().setAll(newPane));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void logoutRoute(MouseEvent event,HBox hBox){
        new Thread(() -> {
            try{
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/LoginSingup/logout.fxml"));
                Platform.runLater(() ->  hBox.getChildren().setAll(newPane));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}