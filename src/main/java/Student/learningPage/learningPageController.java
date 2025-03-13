package Student.learningPage;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.Slider;

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
    public Button btnSound;
    public Button btnFullscreen;
    private ContextMenu settingsMenu;
    public Button btnSetting;
    public Slider sliderVolume;
    private Image volumeLow;
    private Image volumeMed;
    private Image volumeHigh;
    public StackPane videocontainer;
    public AnchorPane controlPane;
    private Timeline hideTimeline;


    private boolean isMouseInStackPane = false;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();
        settingsMenu = new ContextMenu();
        HomeController method_home = new HomeController();
        Navigator method_navigator = new Navigator();
        String videoPath = getClass().getResource("/videos/Test.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        volumeLow  = new Image(getClass().getResource("/img/icon/volume-solid-stage1.png").toExternalForm());
        volumeMed  = new Image(getClass().getResource("/img/icon/volume-solid-stage2.png").toExternalForm());
        volumeHigh = new Image(getClass().getResource("/img/icon/volume-solid-stage3.png").toExternalForm());

        sliderVolume.setMin(0.0);
        sliderVolume.setMax(1.0);
        sliderVolume.setValue(0.5);

        mediaPlayer.setVolume(0.5);

        sliderVolume.valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue();
            mediaPlayer.setVolume(volume);
            updateVolumeIcon(volume);
        });


        // ตั้งค่าให้เล่นใน MediaView
        mediaView.setMediaPlayer(mediaPlayer);
        // play/pause on video click
        videocontainer.setOnMouseClicked(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        });
//        mediaPlayer.setAutoPlay(true);

        // settings
        settingsMenu = new ContextMenu();
        Menu menuQuality = new Menu("Quality");
        RadioMenuItem q1 = new RadioMenuItem("tbd1");
        RadioMenuItem q2 = new RadioMenuItem("tbd2");
        RadioMenuItem q3 = new RadioMenuItem("tbd3");
        RadioMenuItem q4 = new RadioMenuItem("tbd4");

        ToggleGroup qualityGroup = new ToggleGroup();
        q1.setToggleGroup(qualityGroup);
        q2.setToggleGroup(qualityGroup);
        q3.setToggleGroup(qualityGroup);
        q4.setToggleGroup(qualityGroup);
        q2.setSelected(true);
        q1.setOnAction(e -> {
            if (q1.isSelected()) {
                System.out.println("Switched to __");
                // e.g., tbd
            }
        });
        q2.setOnAction(e -> {
            if (q2.isSelected()) {
                System.out.println("Switched to __");
                // ... tbd
            }
        });
        q3.setOnAction(e -> {
            if (q3.isSelected()) {
                System.out.println("Switched to __");
                // ... tbd
            }
        });
        q4.setOnAction(e -> {
            if (q4.isSelected()) {
                System.out.println("Switched to __");
                // ... tbd
            }
        });
        menuQuality.getItems().addAll(q1, q2, q3, q4);

        Menu menuSpeed = new Menu("PlaySpeed");
        RadioMenuItem speed05 = new RadioMenuItem("0.5x");
        RadioMenuItem speed1 = new RadioMenuItem("1.0x");
        RadioMenuItem speed15 = new RadioMenuItem("1.5x");
        ToggleGroup speedGroup = new ToggleGroup();
        speed05.setToggleGroup(speedGroup);
        speed1.setToggleGroup(speedGroup);
        speed15.setToggleGroup(speedGroup);
        speed05.setOnAction(e -> { if (speed05.isSelected()) { mediaPlayer.setRate(0.5); }});
        speed1.setOnAction(e -> { if (speed1.isSelected()) { mediaPlayer.setRate(1.0); }});
        speed15.setOnAction(e -> { if (speed15.isSelected()) { mediaPlayer.setRate(1.5); }});
        speed1.setSelected(true);
        menuSpeed.getItems().addAll(speed05, speed1, speed15);
        settingsMenu.getItems().addAll(menuQuality, menuSpeed);

        // Setting Menu Position
        btnSetting.setOnAction(ev -> {
            settingsMenu.show(btnSetting, Side.TOP, 0, -25);
        });

        settingsMenu.setOnShown(e -> {
            // The user opened the menu, so definitely keep the controlPane visible
            fadeInControlPane();
            // Also cancel any pending hide
            if (hideTimeline != null) hideTimeline.stop();
        });

        settingsMenu.setOnHidden(ev -> {
            DelayFadeOut();
        });



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
        method_home.hoverEffect(btnSound);
        method_home.hoverEffect(btnFullscreen);
        method_home.hoverEffect(btnPlay);
        method_home.hoverEffect(btnSetting);

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

        // อัปเดต Seekbar
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            sliderTime.setValue(newTime.toSeconds());
            lblTime.setText(formatTime(newTime) + " / " + formatTime(mediaPlayer.getTotalDuration()));
        });

        // Replay
        btnPlay.setOnAction(e -> {
            Duration total = mediaPlayer.getTotalDuration();
            Duration current = mediaPlayer.getCurrentTime();
            if (current.toSeconds() + 0.1 >= total.toSeconds()) {
                mediaPlayer.seek(Duration.ZERO);  // rewind
            }

            mediaPlayer.play();
        });

        // Seekbar ปรับตำแหน่งวิดีโอ
        sliderTime.setOnMousePressed(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        });

        sliderTime.setOnMouseReleased(e -> {
            if (mediaPlayer != null) {
                double newPos = sliderTime.getValue();
                mediaPlayer.seek(Duration.seconds(newPos));
                mediaPlayer.play();
            }
        });

        controlPane.setOpacity(1.0);

        videocontainer.setOnMouseEntered(e -> {
            isMouseInStackPane = true;
            fadeInControlPane();
            if (hideTimeline != null) hideTimeline.stop();
        });

        videocontainer.setOnMouseExited(e -> {
            isMouseInStackPane = false;
            DelayFadeOut(); // start 1s delay
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
    private void updateVolumeIcon(double volume) {
        ImageView iv;
        if (volume < 0.3) {
            iv = new ImageView(volumeLow);
        } else if (volume < 0.7) {
            iv = new ImageView(volumeMed);
        } else {
            iv = new ImageView(volumeHigh);
        }

        iv.setFitWidth(20);
        iv.setFitHeight(20);
        btnSound.setGraphic(iv);
      }
    private void fadeInControlPane() {
        controlPane.setVisible(true);

        FadeTransition ftIn = new FadeTransition(Duration.millis(250), controlPane);
        ftIn.setFromValue(controlPane.getOpacity());
        ftIn.setToValue(1.0);
        ftIn.play();
    }

    private void fadeOutControlPane() {
        FadeTransition ftOut = new FadeTransition(Duration.millis(250), controlPane);
        ftOut.setFromValue(controlPane.getOpacity());
        ftOut.setToValue(0.0);
        ftOut.setOnFinished(ev -> controlPane.setVisible(false));

        ftOut.play();
    }

    private void DelayFadeOut() {
        if (hideTimeline != null) {
            hideTimeline.stop();
        }
        hideTimeline = new Timeline(new KeyFrame(Duration.seconds(0.25), ev -> {
            // Only fade out if:
            // 1) mouse is not back in the stackpane
            // 2) the context menu is NOT showing
            if (!isMouseInStackPane && !settingsMenu.isShowing()) {
                fadeOutControlPane();
            }
        }));
        hideTimeline.play();
    }
    }



