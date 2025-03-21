package Student.learningPage;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
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
    private Image playIcon;
    private Image pauseIcon;
    private Image replayIcon;
    public StackPane videocontainer;
    public AnchorPane controlPane;
    private Timeline hideTimeline;

    private Stage fullscreenStage = null;
    private boolean isFullscreen = false;
    private boolean isMouseInStackPane = false;
    private double originalPrefWidth;
    private double originalPrefHeight;
    private Pane originalParent;
    private int originalIndex;
    private PauseTransition inactivityTimer;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        originalPrefWidth = videocontainer.getPrefWidth();
        originalPrefHeight = videocontainer.getPrefHeight();
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();
        settingsMenu = new ContextMenu();
        HomeController method_home = new HomeController();
        Navigator method_navigator = new Navigator();
        String videoPath = getClass().getResource("/videos/Test.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        playIcon = new Image(getClass().getResource("/img/icon/play-button-arrowhead-solid.png").toExternalForm());
        pauseIcon = new Image(getClass().getResource("/img/icon/pause-solid.png").toExternalForm());
        replayIcon = new Image(getClass().getResource("/img/icon/replay-solid.png").toExternalForm());
        volumeLow = new Image(getClass().getResource("/img/icon/volume-solid-stage1.png").toExternalForm());
        volumeMed = new Image(getClass().getResource("/img/icon/volume-solid-stage2.png").toExternalForm());
        volumeHigh = new Image(getClass().getResource("/img/icon/volume-solid-stage3.png").toExternalForm());

        sliderVolume.setMin(0.0);
        sliderVolume.setMax(1.0);
        sliderVolume.setValue(0.5);

        mediaPlayer.setVolume(0.5);

        // ปรับ volume
        sliderVolume.valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue();
            userIsActive();
            mediaPlayer.setVolume(volume);
            updateVolumeIcon(volume);
        });


        // ตั้งค่าให้เล่นใน MediaView
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                mediaView.fitWidthProperty().bind(videocontainer.widthProperty());
                mediaView.fitHeightProperty().bind(videocontainer.heightProperty());
            }
        });

        // play/pause
        videocontainer.setOnMouseClicked(e -> {
            togglePlayPause(mediaPlayer);
            userIsActive(); // <-- NEW
        });

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
        speed05.setOnAction(e -> {
            if (speed05.isSelected()) {
                mediaPlayer.setRate(0.5);
            }
        });
        speed1.setOnAction(e -> {
            if (speed1.isSelected()) {
                mediaPlayer.setRate(1.0);
            }
        });
        speed15.setOnAction(e -> {
            if (speed15.isSelected()) {
                mediaPlayer.setRate(1.5);
            }
        });
        speed1.setSelected(true);
        menuSpeed.getItems().addAll(speed05, speed1, speed15);
        settingsMenu.getItems().addAll(menuQuality, menuSpeed);

        // Setting Menu Position
        btnSetting.setOnAction(ev -> {
            settingsMenu.show(btnSetting, Side.TOP, 0, -25);
        });

        settingsMenu.setOnShown(e -> {
            fadeInControlPane();
            if (hideTimeline != null) hideTimeline.stop();
        });

        settingsMenu.setOnHidden(ev -> {
            DelayFadeOut();
        });

        btnFullscreen.setOnAction(e -> toggleFullscreen());




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
        btnPlay.setOnAction(e -> togglePlayPause(mediaPlayer));


        // Seekbar ปรับตำแหน่งวิดีโอ
        sliderTime.setOnMousePressed(e -> {
            userIsActive();
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

        setupInactivityTimer();

        videocontainer.setOnMouseEntered(e -> {
            isMouseInStackPane = true;
            fadeInControlPane();
            if (hideTimeline != null) hideTimeline.stop();
            inactivityTimer.playFromStart();
        });

        videocontainer.setOnMouseExited(e -> {
            isMouseInStackPane = false;
            DelayFadeOut();
            inactivityTimer.stop();
        });

        videocontainer.setOnMouseMoved(e -> {
            inactivityTimer.playFromStart();
            fadeInControlPane();
        });
        mediaPlayer.setOnEndOfMedia(() -> {
            btnPlay.setGraphic(createIconView(replayIcon));
        });


        subject_name.setText("Test Subject");
        ep.setText("Test Episode : 0");
        teacherName.setText("Puwanas Chaichitchaem");
        role.setText("ศาสตราจารย์");
        clipDescription.setText("ความรักกันไม่ได้หรอก ฉันชอบแอบมานานแล้ว พี่พรรลบ เขาเทอไม่รักฉัน เขาไม่แย่งเธอหรอก. 7 yrs. 2. ยะศิษย์ แดน เพ็งเซ้ง. คนอื่นไม่รู้ แต่กรูดูจนจบฮ่าๆๆ แหวงเเป๊ก เย๊กกะไฟฟ้า");
        method_home.loadAndSetImage(teacherImg, "/img/Profile/user.png");
        bntEp.setText("Episode : 69");

        labelPercent.setText("69%");

        nextCourseName.setText("DSA");
        nextTeacherName.setText("อาจารย์ขิม ใจดี");
        category.setText("</>coding");
        method_home.loadAndSetImage(nextImgCourse, "/img/Profile/user.png");

        countLike.setText("0");
        countDislike.setText("0");
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

    }

    private void togglePlayPause(MediaPlayer mediaPlayer) {
        Duration total = mediaPlayer.getTotalDuration();
        Duration current = mediaPlayer.getCurrentTime();

        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            btnPlay.setGraphic(createIconView(playIcon));
        } else {
            if (current.toSeconds() + 0.1 >= total.toSeconds()) {
                mediaPlayer.seek(Duration.ZERO);
            }
            mediaPlayer.play();
            btnPlay.setGraphic(createIconView(pauseIcon));
        }
    }


    private void setupInactivityTimer() {
        inactivityTimer = new PauseTransition(Duration.seconds(2));
        inactivityTimer.setOnFinished(e -> fadeOutControlPane());
    }

    private void toggleFullscreen() {
        if (!isFullscreen) {
            goFullscreen();
        } else {
            exitFullscreen();
        }

    }
    private void userIsActive() {
        fadeInControlPane();
        inactivityTimer.stop();
        inactivityTimer.playFromStart();
    }

    private void goFullscreen() {
        originalParent = (Pane) videocontainer.getParent();
        originalIndex = originalParent.getChildren().indexOf(videocontainer);
        originalParent.getChildren().remove(videocontainer);

        StackPane fsRoot = new StackPane();
        mediaView.fitWidthProperty().bind(fsRoot.widthProperty());
        mediaView.fitHeightProperty().bind(fsRoot.heightProperty());

        fsRoot.getChildren().add(videocontainer);

        fullscreenStage = new Stage();
        Scene fsScene = new Scene(fsRoot);
        fullscreenStage.setScene(fsScene);
        fullscreenStage.setFullScreenExitHint("");
        fullscreenStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        fullscreenStage.setFullScreen(true);
        fullscreenStage.show();
        isFullscreen = true;

        fullscreenStage.fullScreenProperty().addListener((obs, wasFull, isNowFull) -> {
            if (isNowFull) {
                videocontainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
                videocontainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
                videocontainer.setMaxWidth(Double.MAX_VALUE);
                videocontainer.setMaxHeight(Double.MAX_VALUE);
            } else {
                exitFullscreen();
            }
        });

        // Also handle close request
        fullscreenStage.setOnCloseRequest(e -> exitFullscreen());
    }


    private void exitFullscreen() {
        if (!isFullscreen) return;
        if (fullscreenStage != null && fullscreenStage.isShowing()) {
            fullscreenStage.close();
        }

        mediaView.fitWidthProperty().unbind();
        mediaView.fitHeightProperty().unbind();
        videocontainer.setPrefWidth(originalPrefWidth);
        videocontainer.setPrefHeight(originalPrefHeight);
        originalParent.getChildren().add(originalIndex, videocontainer);
        fullscreenStage = null;
        isFullscreen = false;
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

    private ImageView createIconView(Image icon) {
        ImageView iv = new ImageView(icon);
        iv.setFitWidth(20);
        iv.setFitHeight(20);
        return iv;
    }

    private void updateVolumeIcon(double volume) {
        Image chosenImage;
        if (volume < 0.3) {
            chosenImage = volumeLow;
        } else if (volume < 0.7) {
            chosenImage = volumeMed;
        } else {
            chosenImage = volumeHigh;
        }

        btnSound.setGraphic(createIconView(chosenImage));
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



