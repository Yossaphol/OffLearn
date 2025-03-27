package Teacher.somethingWithVideo;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ClipManager implements Initializable {

    @FXML private MediaView mediaView;
    @FXML private Button btnPlay;
    @FXML private Slider sliderTime;
    @FXML private Label lblTime;
    @FXML private Button btnSound;
    @FXML private Slider sliderVolume;
    @FXML private StackPane videocontainer;
    @FXML private AnchorPane controlPane;

    private MediaPlayer mediaPlayer;
    private Rectangle clip;
    private Timeline hideTimeline;
    private PauseTransition inactivityTimer;
    private PauseTransition hideDelayTransition;
    private boolean videoEnded = false;
    private boolean isMouseInStackPane = false;
    private boolean isFadeInActive = false;
    private boolean volumeSliderVisible = false;
    private boolean isMuted = false;
    private double previousVolume = 0.5;
    private double originalVolSliderWidth;
    private final boolean[] wasPlaying = new boolean[1];
    private Image volumeMute, volumeLow, volumeMed, volumeHigh;
    private Image playIcon, pauseIcon, replayIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadIcons();
        setupInactivityTimer();
        setupMouseTracking();
    }

    public void setVideoUrl(String url) {
        Media media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        initializePlayer();
    }

    private void initializePlayer() {
        sliderVolume.setMin(0.0);
        sliderVolume.setMax(1.0);
        sliderVolume.setValue(previousVolume);
        mediaPlayer.setVolume(previousVolume);
        updateVolumeSliderFill();
        originalVolSliderWidth = sliderVolume.getPrefWidth();
        sliderVolume.setPrefWidth(0);
        sliderVolume.setVisible(false);
        sliderVolume.getStyleClass().add("slider");

        mediaView.setPreserveRatio(true);
        mediaView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                mediaView.fitWidthProperty().bind(videocontainer.widthProperty());
                mediaView.fitHeightProperty().bind(videocontainer.heightProperty());
            }
        });

        clip = new Rectangle();
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        mediaView.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            clip.setWidth(newBounds.getWidth());
            clip.setHeight(newBounds.getHeight());
        });
        mediaView.setClip(clip);

        videocontainer.setOnMouseClicked(e -> {
            togglePlayPause();
            videocontainer.requestFocus();
        });

        setupControls();
    }

    private void setupControls() {
        btnSound.setOnAction(e -> toggleMute());
        btnSound.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> showVolumeSlider());

        sliderVolume.valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue();
            mediaPlayer.setVolume(volume);
            updateVolumeIcon(volume);
            isMuted = volume == 0;
            updateVolumeSliderFill();
            userIsActive();
        });

        sliderVolume.addEventFilter(MouseEvent.MOUSE_EXITED, this::checkVolumeMouseExit);
        btnSound.addEventFilter(MouseEvent.MOUSE_EXITED, this::checkVolumeMouseExit);

        hideDelayTransition = new PauseTransition(Duration.millis(300));
        hideDelayTransition.setOnFinished(e -> hideVolumeSlider());

        sliderVolume.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> hideDelayTransition.stop());
        sliderVolume.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> hideDelayTransition.stop());
        sliderVolume.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> hideDelayTransition.playFromStart());

        btnPlay.setOnAction(e -> togglePlayPause());

        mediaPlayer.setOnReady(() -> {
            sliderTime.setMax(mediaPlayer.getTotalDuration().toSeconds());
            lblTime.setText("00:00 / " + formatTime(mediaPlayer.getTotalDuration()));
            Platform.runLater(this::updateSliderTimeFill);
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            sliderTime.setValue(newTime.toSeconds());
            lblTime.setText(formatTime(newTime) + " / " + formatTime(mediaPlayer.getTotalDuration()));
            updateSliderTimeFill();

            if (videoEnded && newTime.lessThan(mediaPlayer.getTotalDuration())) {
                videoEnded = false;
                btnPlay.setGraphic(createIconView(pauseIcon));
            }
        });

        sliderTime.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateSliderTimeFill();
            Duration newDur = Duration.seconds(newVal.doubleValue());
            lblTime.setText(formatTime(newDur) + " / " + formatTime(mediaPlayer.getTotalDuration()));
        });

        sliderTime.setOnMousePressed(e -> {
            wasPlaying[0] = mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
            if (wasPlaying[0]) mediaPlayer.pause();
        });

        sliderTime.setOnMouseReleased(e -> {
            mediaPlayer.seek(Duration.seconds(sliderTime.getValue()));
            if (wasPlaying[0]) mediaPlayer.play();
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            btnPlay.setGraphic(createIconView(replayIcon));
            videoEnded = true;
        });

        btnPlay.setGraphic(createIconView(playIcon));
    }

    private void setupInactivityTimer() {
        inactivityTimer = new PauseTransition(Duration.seconds(3.33));
        inactivityTimer.setOnFinished(e -> {
            fadeOutControlPane();
            hideMouseCursor();
        });
    }

    private void showMouseCursor() {
        videocontainer.setCursor(Cursor.DEFAULT);
    }

    private void hideMouseCursor() {
        videocontainer.setCursor(Cursor.NONE);
    }

    private void setupMouseTracking() {
        videocontainer.setOnMouseEntered(e -> {
            isMouseInStackPane = true;
            fadeInControlPane();
            if (hideTimeline != null) hideTimeline.stop();
            inactivityTimer.playFromStart();
        });

        videocontainer.setOnMouseExited(e -> {
            isMouseInStackPane = false;
            delayFadeOut();
            inactivityTimer.stop();
        });

        videocontainer.setOnMouseMoved(e -> {
            inactivityTimer.playFromStart();
            fadeInControlPane();
            showMouseCursor();
        });
    }

    private void delayFadeOut() {
        if (hideTimeline != null) hideTimeline.stop();
        hideTimeline = new Timeline(new KeyFrame(Duration.seconds(0.25), ev -> {
            if (!isMouseInStackPane){
                fadeOutControlPane();
            }
        }));
        hideTimeline.play();
    }

    private void userIsActive() {
        fadeInControlPane();
        inactivityTimer.stop();
        inactivityTimer.playFromStart();
    }

    private void fadeInControlPane() {
        if (!isFadeInActive && (controlPane.getOpacity() < 1.0 || !controlPane.isVisible())) {
            isFadeInActive = true;
            controlPane.setVisible(true);
            FadeTransition ftIn = new FadeTransition(Duration.millis(250), controlPane);
            ftIn.setFromValue(controlPane.getOpacity());
            ftIn.setToValue(1.0);
            ftIn.setOnFinished(ev -> isFadeInActive = false);
            ftIn.play();
        }
    }

    private void togglePlayPause() {
        if (videoEnded) {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
            videoEnded = false;
            btnPlay.setGraphic(createIconView(pauseIcon));
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            btnPlay.setGraphic(createIconView(playIcon));
        } else {
            mediaPlayer.play();
            btnPlay.setGraphic(createIconView(pauseIcon));
        }
    }
    private void updateVolumeSliderFill() {
        Platform.runLater(() -> {
            Node track = sliderVolume.lookup(".track");
            if (track != null) {
                double percent = sliderVolume.getValue() * 100;
                String style = String.format(
                        "-fx-background-color: linear-gradient(to right, #8100cc %.2f%%, #F6E6FF %.2f%%); " +
                                "-fx-background-radius: 5px; -fx-pref-height: 3px;",
                        percent, percent
                );
                track.setStyle(style);
            }
        });
    }
    private void updateSliderTimeFill() {
        double totalSec = sliderTime.getMax();
        if (totalSec <= 0) return;
        double playedSec = sliderTime.isValueChanging() ? sliderTime.getValue() : mediaPlayer.getCurrentTime().toSeconds();
        double bufferSec = mediaPlayer.getBufferProgressTime().toSeconds();
        if (bufferSec < playedSec) bufferSec = playedSec;
        if (bufferSec > totalSec) bufferSec = totalSec;

        double playedPercent = (playedSec / totalSec) * 100.0;
        double bufferPercent = (bufferSec / totalSec) * 100.0;

        String style = String.format(
                "-fx-background-color: linear-gradient(to right, #8100cc %.2f%%, #AAAAAA %.2f%%, #FFFFFF %.2f%%);" +
                        "-fx-background-radius: 5px; -fx-pref-height: 3px;",
                playedPercent, playedPercent, bufferPercent
        );

        Node track = sliderTime.lookup(".track");
        if (track != null) {
            track.setStyle(style);
        }
    }


    private void fadeOutControlPane() {
        FadeTransition ftOut = new FadeTransition(Duration.millis(250), controlPane);
        ftOut.setFromValue(controlPane.getOpacity());
        ftOut.setToValue(0.0);
        ftOut.setOnFinished(ev -> controlPane.setVisible(false));
        ftOut.play();
    }

    public void play() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    public void disposePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    private void toggleMute() {
        if (!isMuted) {
            previousVolume = sliderVolume.getValue();
            sliderVolume.setValue(0.0);
        } else {
            sliderVolume.setValue(previousVolume);
        }
    }

    private void showVolumeSlider() {
        if (!volumeSliderVisible) {
            volumeSliderVisible = true;
            sliderVolume.setVisible(true);
            Timeline show = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(sliderVolume.prefWidthProperty(), 0)),
                    new KeyFrame(Duration.millis(250), new KeyValue(sliderVolume.prefWidthProperty(), originalVolSliderWidth))
            );
            show.play();
        }
    }

    private void hideVolumeSlider() {
        if (volumeSliderVisible) {
            Timeline hide = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(sliderVolume.prefWidthProperty(), sliderVolume.getPrefWidth())),
                    new KeyFrame(Duration.millis(250), new KeyValue(sliderVolume.prefWidthProperty(), 0))
            );
            hide.setOnFinished(e -> {
                sliderVolume.setVisible(false);
                volumeSliderVisible = false;
            });
            hide.play();
        }
    }

    private void checkVolumeMouseExit(MouseEvent e) {
        Bounds btnBounds = btnSound.localToScene(btnSound.getBoundsInLocal());
        Bounds sliderBounds = sliderVolume.localToScene(sliderVolume.getBoundsInLocal());
        double minX = Math.min(btnBounds.getMinX(), sliderBounds.getMinX());
        double minY = Math.min(btnBounds.getMinY(), sliderBounds.getMinY());
        double maxX = Math.max(btnBounds.getMaxX(), sliderBounds.getMaxX());
        double maxY = Math.max(btnBounds.getMaxY(), sliderBounds.getMaxY());

        if (e.getSceneX() < minX || e.getSceneX() > maxX || e.getSceneY() < minY || e.getSceneY() > maxY) {
            hideVolumeSlider();
        }
    }

    private void updateVolumeIcon(double volume) {
        Image icon = volume == 0 ? volumeMute :
                (volume < 0.2 ? volumeLow :
                        (volume < 0.5 ? volumeMed : volumeHigh));
        btnSound.setGraphic(createIconView(icon));
    }

    private String formatTime(Duration duration) {
        int min = (int) duration.toMinutes();
        int sec = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", min, sec);
    }

    private ImageView createIconView(Image icon) {
        ImageView iv = new ImageView(icon);
        iv.setFitWidth(25);
        iv.setFitHeight(25);
        return iv;
    }

    private void loadIcons() {
        playIcon   = new Image(getClass().getResource("/img/icon/play-button-arrowhead-solid.png").toExternalForm());
        pauseIcon  = new Image(getClass().getResource("/img/icon/pause-solid.png").toExternalForm());
        replayIcon = new Image(getClass().getResource("/img/icon/replay-solid.png").toExternalForm());
        volumeMute = new Image(getClass().getResource("/img/icon/volume-solid-stage0.png").toExternalForm());
        volumeLow  = new Image(getClass().getResource("/img/icon/volume-solid-stage1.png").toExternalForm());
        volumeMed  = new Image(getClass().getResource("/img/icon/volume-solid-stage2.png").toExternalForm());
        volumeHigh = new Image(getClass().getResource("/img/icon/volume-solid-stage3.png").toExternalForm());
    }
}
