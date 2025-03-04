package client.learningPage;

import client.FontLoader.FontLoader;
import client.HomeAndNavigation.HomeController;
import client.HomeAndNavigation.Navigator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;

public class learningPageController implements Initializable {
    public VBox leftWrapper;
    public HBox rootpage;
    public HBox searhbar_container;
    public MediaView mediaView;
    public Button btnPlay;
    public Button btnPause;
    public Button btnStop;
    public Slider sliderTime;
    public Label lblTime;
    public Label subject_name;
    public Label ep;
    public HBox content;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();

        //HomeController method_home = new HomeController();

        //สำหรับใครที่จะเทสหน้านี้ให้เอาไฟล์คลิป .mp4 ของตัวเองไปใส่ใน /resource/videos/ ตั้งชื่อเป็น Test.mp4 แนะนำให้เป็นคลิปแนวนอน 16:9
        String videoPath = getClass().getResource("/videos/Test.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // ตั้งค่าให้เล่นใน MediaView
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        displayNavbar();
        displaySearchBar();

        subject_name.setText("Test Subject");
        ep.setText("Test Episode : 0");
        // ปรับ Seekbar ตามเวลาวิดีโอ
        mediaPlayer.setOnReady(() -> {
            sliderTime.setMax(mediaPlayer.getTotalDuration().toSeconds());
            lblTime.setText("00:00 / " + formatTime(mediaPlayer.getTotalDuration()));
        });

        // อัปเดต Seekbar เมื่อเล่นวิดีโอ
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            sliderTime.setValue(newTime.toSeconds());
            lblTime.setText(formatTime(newTime) + " / " + formatTime(mediaPlayer.getTotalDuration()));
        });

        // ควบคุมปุ่ม
        btnPlay.setOnAction(e -> mediaPlayer.play());
        btnPause.setOnAction(e -> mediaPlayer.pause());
        btnStop.setOnAction(e -> mediaPlayer.stop());

        // ให้ Seekbar ปรับตำแหน่งวิดีโอ
        sliderTime.setOnMouseReleased(e -> {
            mediaPlayer.seek(Duration.seconds(sliderTime.getValue()));
        });
    }

    private String formatTime(Duration newTime) {
        int minutes = (int) newTime.toMinutes();
        int seconds = (int) newTime.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/client/NavAndSearchbar/navBar.fxml"));
            VBox navContent = calendarLoader.load();
            leftWrapper.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displaySearchBar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/client/NavAndSearchbar/searchBar.fxml"));
            HBox searchbarContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
