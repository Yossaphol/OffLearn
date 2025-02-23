package HomeAndNavigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Navigator {



    public void homeRoute(MouseEvent event, HBox hBox){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/HomePage/home.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void dashboardRoute(MouseEvent event, HBox hBox){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/statistics/dashboard.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void courseRoute(MouseEvent event,HBox hBox){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/course.fxml"));
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
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/task.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void roadmapRoute(MouseEvent event,HBox hBox){
        try{
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/roadmap.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void myCourseRoute(MouseEvent event,HBox hBox){
        try{
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/myCourse.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cartRoute(MouseEvent event,HBox hBox){
        try{
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/courseManage/cart.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leaderboardRoute(MouseEvent event,HBox hBox){
        try{
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/statistics/leaderboard.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void settingRoute(MouseEvent event,HBox hBox){
        try{
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/setting/setting.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoutRoute(MouseEvent event,HBox hBox){
        try{
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/LoginSingup/logout.fxml"));
            hBox.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

