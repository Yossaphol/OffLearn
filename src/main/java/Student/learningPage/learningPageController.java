package Student.learningPage;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
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
    public Circle teacherImg;
    public Label teacherName;
    public Label clipDescription;
    public Label role;
    public ScrollPane mainScrollPane;
    public Button btnLike;
    public Button btnDislike;
    public Button btnContectTeacher;
    public Button btnGloblalChat;
    public Label countLike;
    public Label countDislike;
    public Button btnOffLoad;
    public Label countPlaylist;
    public Button bntEp;
    public Label labelPercent;
    public ProgressBar progressBar;
    public Label btnRoadmap;
    public Button nextCourse;
    public Label category;
    public Label nextCourseName;
    public Label nextTeacherName;
    public ProgressBar nextCourseProgressBar;
    public Rectangle nextImgCourse;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();

        HomeController method_home = new HomeController();
        Navigator method_navigator = new Navigator();

        //สำหรับใครที่จะเทสหน้านี้ให้เอาไฟล์คลิป .mp4 ของตัวเองไปใส่ใน /resource/videos/ ตั้งชื่อเป็น Test.mp4 แนะนำให้เป็นคลิปแนวนอน 16:9
        String videoPath = getClass().getResource("/videos/Test.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // ตั้งค่าให้เล่นใน MediaView
        mediaView.setMediaPlayer(mediaPlayer);
//        mediaPlayer.setAutoPlay(true);

        displayNavbar();
        displaySearchBar();

        subject_name.setText("Test Subject");
        ep.setText("Test Episode : 0");
        teacherName.setText("Puwanas Chaichitchaem");
        role.setText("ศาสตราจารย์");
        clipDescription.setText("ความรักกันไม่ได้หรอก ฉันชอบแอบมานานแล้ว พี่พรรลบ เขาเทอไม่รักฉัน เขาไม่แย่งเธอหรอก. 7 yrs. 2. ยะศิษย์ แดน เพ็งเซ้ง. คนอื่นไม่รู้ แต่กรูดูจนจบฮ่าๆๆ แหวงเเป๊ก เย๊กกะไฟฟ้า");
        method_home.loadAndSetImage(teacherImg, "/img/Profile/user.png");

        method_home.hoverEffect(btnContectTeacher);
        method_home.hoverEffect(btnGloblalChat);
        method_home.hoverEffect(btnLike);
        method_home.hoverEffect(btnDislike);
        method_home.hoverEffect(btnOffLoad);
        method_home.hoverEffect(bntEp);
        method_home.hoverEffect(nextCourse);

        bntEp.setText("Episode : 69");

        labelPercent.setText("69%");

        nextCourseName.setText("DSA");
        nextTeacherName.setText("อาจารย์ขิม ใจดี");
        category.setText("</>coding");
        method_home.loadAndSetImage(nextImgCourse, "/img/Profile/user.png");

        countLike.setText("0");
        countDislike.setText("0");



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
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/navBar.fxml"));
            VBox navContent = calendarLoader.load();
            leftWrapper.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displaySearchBar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/searchBar.fxml"));
            HBox searchbarContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
