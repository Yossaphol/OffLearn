package Student.learningPage;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * handles:
 * - Play/Pause/Replay
 * - Volume slider (with a hide/show animation)
 * - Seek bar
 * - Inactivity fade for the control pane
 * - Fullscreen toggling (ESC to exit)
 * - Setting Menus
 */
public class VideoPlayerManager implements Initializable {

    @FXML private HBox controlpanesection; // contain controls
    @FXML private HBox timebox; // contain time label
    @FXML private Region regionpart; // space
    @FXML private MediaView mediaView; // media
    @FXML private Button btnPlay;      // toggles play/pause, also handles replay if ended
    @FXML private Slider sliderTime;   // timeline slider
    @FXML private Label lblTime;       // "00:00 / 00:00"
    @FXML private Button btnSound;     // volume button
    @FXML private Button btnSkipBack;
    @FXML private Button btnSkipFront;
    @FXML private Button btnFullscreen; // fullscreen button
    @FXML private Button btnSetting;   // opens a context menu for speed/quality
    @FXML private Slider sliderVolume; // volume slider
    @FXML private StackPane videocontainer;  // root container for the video
    @FXML private AnchorPane controlPane;     // overlay pane with the controls
    private MediaPlayer mediaPlayer;
    private Rectangle clip;
    private ContextMenu settingsMenu;
    private Timeline hideTimeline;         // for delayed fade
    private PauseTransition inactivityTimer;
    private PauseTransition hideDelayTransition;
    private Node trackNode;

    private Stage fullscreenStage = null;
    private boolean isFullscreen = false;
    private boolean videoEnded   = false;
    private boolean isMouseInStackPane = false;
    private boolean isFadeInActive     = false;
    private boolean volumeSliderVisible= false;
    private boolean isMuted = false;
    private double previousVolume = 0.5;

    private double originalVolSliderWidth;
    private double originalPrefWidth;
    private double originalPrefHeight;
    private Pane originalParent;
    private int originalIndex;

    private final boolean[] wasPlaying = new boolean[1];

    private Image volumeMute, volumeLow, volumeMed, volumeHigh;
    private Image playIcon, pauseIcon, replayIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // setup
        videocontainer.getStylesheets().add(
                getClass().getResource("/css/learningPage.css").toExternalForm()
        );
        originalPrefWidth  = videocontainer.getPrefWidth();
        originalPrefHeight = videocontainer.getPrefHeight();
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();
        settingsMenu = new ContextMenu();
        HomeController method_home = new HomeController();

        // Load icons
        loadIcons();

        // test video, idk how to play vid from DB lol ;w;
        String videoPath = getClass().getResource("/videos/test.mp4").toExternalForm();
        Media media = new Media(videoPath);
        mediaPlayer = new MediaPlayer(media);


        // volume slider
        sliderVolume.setMin(0.0);
        sliderVolume.setMax(1.0);
        sliderVolume.setValue(0.5);
        originalVolSliderWidth = sliderVolume.getPrefWidth();
        sliderVolume.setPrefWidth(0);
        sliderVolume.setVisible(false);
        sliderVolume.getStyleClass().add("slider");

        mediaPlayer.setVolume(0.5);

        btnSound.setOnAction(e -> {
            videocontainer.requestFocus();
            toggleMute();
        });
        btnSound.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> showVolumeSlider());

        // volume icon
        sliderVolume.valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue();

            userIsActive();
            mediaPlayer.setVolume(volume);
            updateVolumeIcon(volume);

            Platform.runLater(() -> {
                Node track = sliderVolume.lookup(".track");
                if (track != null) {
                    double percent = volume * 100;
                    String style = String.format(
                            "-fx-background-color: linear-gradient(to right, #8100cc %.2f%%, #F6E6FF %.2f%%); " +
                                    "-fx-background-radius: 5px; -fx-pref-height: 3px;",
                            percent, percent
                    );
                    track.setStyle(style);
                }
            });
        });


        // binding
        mediaView.setMediaPlayer(mediaPlayer);
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

        // toggles play/pause
        videocontainer.setOnMouseClicked(e -> {
                togglePlayPause(mediaPlayer);
                userIsActive();
                videocontainer.requestFocus();
        });

        // Setup settings menu
        setupSettingsMenu(mediaPlayer);

        settingsMenu.setOnShown(e -> {
            fadeInControlPane();
            if (hideTimeline != null) hideTimeline.stop();
        });
        settingsMenu.setOnHidden(ev -> {
            DelayFadeOut();
        });

        // Fullscreen button
        btnFullscreen.setOnAction(e -> {
            videocontainer.requestFocus();
            toggleFullscreen();
        });
        sliderTime.getStyleClass().add("slider");
        // get duration
        mediaPlayer.setOnReady(() -> {
            // Keep your existing code:
            sliderTime.setMax(mediaPlayer.getTotalDuration().toSeconds());
            lblTime.setText("00:00 / " + formatTime(mediaPlayer.getTotalDuration()));
            Platform.runLater(this::updateSliderTimeFill);

            // Now add a listener for buffering:
            mediaPlayer.bufferProgressTimeProperty().addListener((obs, oldVal, newVal) -> {
                // You can call the same method or a separate method:
                updateSliderTimeFill();
            });
        });

        // Update time
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            sliderTime.setValue(newTime.toSeconds());
            lblTime.setText(formatTime(newTime) + " / " + formatTime(mediaPlayer.getTotalDuration()));
            updateSliderTimeFill(); // update fill
        });
        mediaPlayer.bufferProgressTimeProperty().addListener((obs, oldVal, newVal) -> {
            updateSliderTimeFill();
        });
        sliderTime.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateSliderTimeFill();
            double newSeconds = newVal.doubleValue();
            Duration d = Duration.seconds(newSeconds);
            lblTime.setText(
                    formatTime(d) + " / " + formatTime(mediaPlayer.getTotalDuration())
            );
        });


        btnPlay.setOnAction(e -> {
            togglePlayPause(mediaPlayer);
            videocontainer.requestFocus();
        });

        // Seeking logic
        sliderTime.setOnMousePressed(e -> {
            userIsActive();
            if (mediaPlayer != null) {
                wasPlaying[0] = (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING);
                if (wasPlaying[0]) {
                    mediaPlayer.pause();
                }
            }
        });
        sliderTime.setOnMouseReleased(e -> {
            if (mediaPlayer != null) {
                double newPos = sliderTime.getValue();
                mediaPlayer.seek(Duration.seconds(newPos));
                if (videoEnded) {
                    videoEnded = false;
                    btnPlay.setGraphic(createIconView(pauseIcon));
                }
                if (wasPlaying[0]) {
                    mediaPlayer.play();
                }
            }
            videocontainer.requestFocus();
        });

        controlPane.setOpacity(1.0);

        // Prevent clicks from passing through certain nodes
        consumeClicks(controlpanesection);
        consumeClicks(timebox);
        consumeClicks(regionpart);
        consumeClicks(lblTime);

        // Inactivity fade
        setupInactivityTimer();

        btnSkipBack.setOnAction(e -> {
            skipBackward(5);
            userIsActive();
            videocontainer.requestFocus();
        });
        btnSkipFront.setOnAction(e -> {
            skipForward(5);
            userIsActive();
            videocontainer.requestFocus();
        });
        // Volume slider hide logic
        sliderVolume.setFocusTraversable(false);
        btnSound.addEventFilter(MouseEvent.MOUSE_EXITED, this::checkVolumeMouseExit);
        sliderVolume.addEventFilter(MouseEvent.MOUSE_EXITED, this::checkVolumeMouseExit);
        hideDelayTransition = new PauseTransition(Duration.millis(300));
        hideDelayTransition.setOnFinished(e -> hideVolumeSlider());
        Platform.runLater(() -> {
            videocontainer.setFocusTraversable(true);
            videocontainer.requestFocus();
            Scene scene = btnPlay.getScene();
            if (scene != null) {
                scene.setOnKeyPressed(e -> {
                    e.consume();
                    switch (e.getCode()) {
                        case SPACE:
                            // Toggle play/pause
                            togglePlayPause(mediaPlayer);
                            userIsActive();
                            break;

                        case LEFT:
                            // Skip backward 5s
                            skipBackward(5);
                            userIsActive();
                            break;

                        case RIGHT:
                            // Skip forward 5s
                            skipForward(5);
                            userIsActive();
                            break;
                        case M:
                            // Mute/unmute
                            toggleMute();
                            userIsActive();
                            break;

                        case F:
                            // Toggle fullscreen
                            toggleFullscreen();
                            userIsActive();
                            break;

                        case ESCAPE:
                            // If in fullscreen, exit
                            if (isFullscreen) {
                                exitFullscreen();
                            }
                            break;

                        default:
                            // Do nothing
                    }
                });
            }
        });

        Platform.runLater(() -> {
            Scene scene = btnSound.getScene();
            if (scene != null) {
                scene.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
                    Bounds btnBounds = btnSound.localToScene(btnSound.getBoundsInLocal());
                    Bounds sliderBounds = sliderVolume.localToScene(sliderVolume.getBoundsInLocal());
                    double minX = Math.min(btnBounds.getMinX(), sliderBounds.getMinX());
                    double minY = Math.min(btnBounds.getMinY(), sliderBounds.getMinY());
                    double maxX = Math.max(btnBounds.getMaxX(), sliderBounds.getMaxX());
                    double maxY = Math.max(btnBounds.getMaxY(), sliderBounds.getMaxY());

                    if (e.getSceneX() < minX || e.getSceneX() > maxX ||
                            e.getSceneY() < minY || e.getSceneY() > maxY) {
                        hideDelayTransition.playFromStart();
                    } else {
                        hideDelayTransition.stop();
                    }
                });
            }
        });

        Platform.runLater(() -> {
            Node track = sliderVolume.lookup(".track");
            if (track != null) {
                double volume = sliderVolume.getValue();
                double percent = volume * 100;
                String style = String.format(
                        "-fx-background-color: linear-gradient(to right, #8100cc %.2f%%, #F6E6FF %.2f%%); " +
                                "-fx-background-radius: 5px; -fx-pref-height: 3px;",
                        percent, percent
                );
                track.setStyle(style);
            }
        });
        sliderVolume.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> hideDelayTransition.stop());
        sliderVolume.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> hideDelayTransition.stop());
        sliderVolume.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            hideDelayTransition.playFromStart();
            videocontainer.requestFocus();
        });

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
            showMouseCursor();
        });

        // If video ends => replay icon
        mediaPlayer.setOnEndOfMedia(() -> {
            btnPlay.setGraphic(createIconView(replayIcon));
            videoEnded = true;
        });

        btnPlay.setGraphic(createIconView(playIcon));

        method_home.hoverEffect(btnSound);
        method_home.hoverEffect(btnFullscreen);
        method_home.hoverEffect(btnPlay);
        method_home.hoverEffect(btnSetting);
    }

    /* Toggles play/pause or replay if ended.*/
    private void togglePlayPause(MediaPlayer mediaPlayer) {
        if (videoEnded) {
            // Replay
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
            videoEnded = false;
            btnPlay.setGraphic(createIconView(pauseIcon));
            return;
        }
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            btnPlay.setGraphic(createIconView(playIcon));
        } else {
            mediaPlayer.play();
            btnPlay.setGraphic(createIconView(pauseIcon));
        }
    }

    /* settings menu for speed/quality */
    private void setupSettingsMenu(MediaPlayer mediaPlayer) {
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

        // currently undecided
        q1.setOnAction(e -> System.out.println("Quality tbd1"));
        q2.setOnAction(e -> System.out.println("Quality tbd2"));
        q3.setOnAction(e -> System.out.println("Quality tbd3"));
        q4.setOnAction(e -> System.out.println("Quality tbd4"));
        menuQuality.getItems().addAll(q1, q2, q3, q4);

        // Speed
        Menu menuSpeed = new Menu("PlaySpeed");
        RadioMenuItem speed05  = new RadioMenuItem("0.5x");
        RadioMenuItem speed075 = new RadioMenuItem("0.75x");
        RadioMenuItem speed1   = new RadioMenuItem("1.0x");
        RadioMenuItem speed125 = new RadioMenuItem("1.25x");
        RadioMenuItem speed15  = new RadioMenuItem("1.5x");

        ToggleGroup speedGroup = new ToggleGroup();
        speed05.setToggleGroup(speedGroup);
        speed075.setToggleGroup(speedGroup);
        speed1.setToggleGroup(speedGroup);
        speed125.setToggleGroup(speedGroup);
        speed15.setToggleGroup(speedGroup);

        speed05.setOnAction(e -> mediaPlayer.setRate(0.5));
        speed075.setOnAction(e -> mediaPlayer.setRate(0.75));
        speed1.setOnAction(e -> mediaPlayer.setRate(1.0));
        speed125.setOnAction(e -> mediaPlayer.setRate(1.25));
        speed15.setOnAction(e -> mediaPlayer.setRate(1.5));
        speed1.setSelected(true);

        menuSpeed.getItems().addAll(speed05, speed075, speed1, speed125, speed15);
        settingsMenu.setAutoHide(true);
        settingsMenu.getItems().addAll(menuQuality, menuSpeed);
        btnSetting.setOnAction(e -> {
            if (settingsMenu.isShowing()) {
                settingsMenu.hide();
            } else {
                settingsMenu.show(btnSetting, Side.TOP, -25, -25);
            }
            videocontainer.requestFocus();
        });
    }

    private void updateSliderTimeFill() {
        double totalSec = sliderTime.getMax();
        if (totalSec <= 0) return;
        double playedSec = sliderTime.isValueChanging()
                ? sliderTime.getValue()
                : mediaPlayer.getCurrentTime().toSeconds();

        double bufferSec = mediaPlayer.getBufferProgressTime().toSeconds();
        if (bufferSec < playedSec) bufferSec = playedSec;
        if (bufferSec > totalSec)  bufferSec = totalSec;
        double playedPercent  = (playedSec  / totalSec) * 100.0;
        double bufferPercent  = (bufferSec / totalSec) * 100.0;

        String style = String.format(
                "-fx-background-color: linear-gradient(to right, " +
                        "#8100cc %.2f%%, " +
                        "#AAAAAA %.2f%%, " +
                        "#FFFFFF %.2f%%" +
                        ");" +
                        "-fx-background-radius: 5px; -fx-pref-height: 3px;",
                playedPercent, playedPercent, bufferPercent
        );
        
        Node track = sliderTime.lookup(".track");
        if (track != null) {
            track.setStyle(style);
        }
    }



    /* Inactivity fade logic */
    private void setupInactivityTimer() {
        inactivityTimer = new PauseTransition(Duration.seconds(3.33));
        inactivityTimer.setOnFinished(e -> {
            fadeOutControlPane();
            hideMouseCursor();
        });
    }

    /* Mark user as active, keep controls visible */
    private void userIsActive() {
        fadeInControlPane();
        inactivityTimer.stop();
        inactivityTimer.playFromStart();
    }

    /* Toggle fullscreen */
    private void toggleFullscreen() {
        if (!isFullscreen) {
            goFullscreen();
        } else {
            exitFullscreen();
        }
    }

    private void goFullscreen() {
        mediaView.setClip(null);
        originalParent = (Pane) videocontainer.getParent();
        originalIndex  = originalParent.getChildren().indexOf(videocontainer);
        originalParent.getChildren().remove(videocontainer);

        StackPane fsRoot = new StackPane();
        mediaView.fitWidthProperty().bind(fsRoot.widthProperty());
        mediaView.fitHeightProperty().bind(fsRoot.heightProperty());
        fsRoot.getChildren().add(videocontainer);

        Scene fsScene = new Scene(fsRoot);
        fsScene.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            // same volume logic
            Bounds btnBounds = btnSound.localToScene(btnSound.getBoundsInLocal());
            Bounds sliderBounds = sliderVolume.localToScene(sliderVolume.getBoundsInLocal());
            double minX = Math.min(btnBounds.getMinX(), sliderBounds.getMinX());
            double minY = Math.min(btnBounds.getMinY(), sliderBounds.getMinY());
            double maxX = Math.max(btnBounds.getMaxX(), sliderBounds.getMaxX());
            double maxY = Math.max(btnBounds.getMaxY(), sliderBounds.getMaxY());
            if (e.getSceneX() < minX || e.getSceneX() > maxX ||
                    e.getSceneY() < minY || e.getSceneY() > maxY) {
                hideDelayTransition.playFromStart();
            } else {
                hideDelayTransition.stop();
            }
        });
        fsScene.setOnKeyPressed(e -> {
            e.consume();
            switch (e.getCode()) {
                case SPACE:
                    togglePlayPause(mediaPlayer);
                    userIsActive();
                    break;
                case LEFT:
                    skipBackward(5);
                    userIsActive();
                    break;
                case RIGHT:
                    skipForward(5);
                    userIsActive();
                    break;
                case M:
                    toggleMute();
                    userIsActive();
                    break;
                case ESCAPE:
                    if (isFullscreen) exitFullscreen();
                    break;
            }
        });

        fsRoot.setFocusTraversable(true);
        Platform.runLater(() -> fsRoot.requestFocus());

        fullscreenStage = new Stage();
        fullscreenStage.setScene(fsScene);
        fullscreenStage.setFullScreenExitHint("Press ESC to exit fullscreen");
        fullscreenStage.setFullScreen(true);
        fullscreenStage.show();

        isFullscreen = true;
        btnSetting.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
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

        fullscreenStage.setOnCloseRequest(e -> exitFullscreen());
    }

    private void exitFullscreen() {
        if (!isFullscreen) return;
        if (fullscreenStage != null && fullscreenStage.isShowing()) {
            fullscreenStage.close();
        }
        mediaView.setClip(clip);
        mediaView.fitWidthProperty().unbind();
        mediaView.fitHeightProperty().unbind();

        videocontainer.setPrefWidth(originalPrefWidth);
        videocontainer.setPrefHeight(originalPrefHeight);
        originalParent.getChildren().add(originalIndex, videocontainer);
        fullscreenStage = null;
        isFullscreen   = false;
        btnSetting.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        videocontainer.requestFocus();
    }

    /* simple utility to block mouse clicks */
    private void consumeClicks(Node node) {
        node.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> event.consume());
    }

    /* Format time */
    private String formatTime(Duration newTime) {
        int minutes = (int) newTime.toMinutes();
        int seconds = (int) newTime.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /* Load icons from resources */
    private void loadIcons() {
        playIcon   = new Image(getClass().getResource("/img/icon/play-button-arrowhead-solid.png").toExternalForm());
        pauseIcon  = new Image(getClass().getResource("/img/icon/pause-solid.png").toExternalForm());
        replayIcon = new Image(getClass().getResource("/img/icon/replay-solid.png").toExternalForm());

        volumeMute  = new Image(getClass().getResource("/img/icon/volume-solid-stage0.png").toExternalForm());
        volumeLow  = new Image(getClass().getResource("/img/icon/volume-solid-stage1.png").toExternalForm());
        volumeMed  = new Image(getClass().getResource("/img/icon/volume-solid-stage2.png").toExternalForm());
        volumeHigh = new Image(getClass().getResource("/img/icon/volume-solid-stage3.png").toExternalForm());
    }

    /* resize image */
    private ImageView createIconView(Image icon) {
        ImageView iv = new ImageView(icon);
        iv.setFitWidth(25);
        iv.setFitHeight(25);
        return iv;
    }

    /* update volume icon*/
    private void updateVolumeIcon(double volume) {
        Image chosenImage;
        if (volume == 0) {
            chosenImage = volumeMute;
        } else if (volume < 0.2) {
            chosenImage = volumeLow;
        } else if (volume < 0.5) {
            chosenImage = volumeMed;
        } else {
            chosenImage = volumeHigh;
        }
        btnSound.setGraphic(createIconView(chosenImage));
    }

    /* Fade in the control pane if not already */
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

    /* Fade out the control pane */
    private void fadeOutControlPane() {
        FadeTransition ftOut = new FadeTransition(Duration.millis(250), controlPane);
        ftOut.setFromValue(controlPane.getOpacity());
        ftOut.setToValue(0.0);
        ftOut.setOnFinished(ev -> controlPane.setVisible(false));
        ftOut.play();
    }

    /* Delay fade-out after 0.25s if mouse is not in container */
    private void DelayFadeOut() {
        if (hideTimeline != null) {
            hideTimeline.stop();
        }
        hideTimeline = new Timeline(new KeyFrame(Duration.seconds(0.25), ev -> {
            if (!isMouseInStackPane && !settingsMenu.isShowing()) {
                fadeOutControlPane();
            }
        }));
        hideTimeline.play();
    }

    /* animated slider for volume */
    private void showVolumeSlider() {
        if (!volumeSliderVisible) {
            volumeSliderVisible = true;
            sliderVolume.setVisible(true);
            sliderVolume.setPrefWidth(0);
            Timeline showTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(sliderVolume.prefWidthProperty(), 0)),
                    new KeyFrame(Duration.millis(250), new KeyValue(sliderVolume.prefWidthProperty(), originalVolSliderWidth))
            );
            showTimeline.play();
        }
    }

    private void hideVolumeSlider() {
        if (volumeSliderVisible) {
            Timeline hideTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(sliderVolume.prefWidthProperty(), sliderVolume.getPrefWidth())),
                    new KeyFrame(Duration.millis(250), new KeyValue(sliderVolume.prefWidthProperty(), 0))
            );
            hideTimeline.setOnFinished(e -> {
                sliderVolume.setVisible(false);
                volumeSliderVisible = false;
            });
            hideTimeline.play();
        }
    }

    /* Check if mouse exit => hide volume */
    private void checkVolumeMouseExit(MouseEvent e) {
        Bounds btnBounds    = btnSound.localToScene(btnSound.getBoundsInLocal());
        Bounds sliderBounds = sliderVolume.localToScene(sliderVolume.getBoundsInLocal());

        double minX = Math.min(btnBounds.getMinX(), sliderBounds.getMinX());
        double minY = Math.min(btnBounds.getMinY(), sliderBounds.getMinY());
        double maxX = Math.max(btnBounds.getMaxX(), sliderBounds.getMaxX());
        double maxY = Math.max(btnBounds.getMaxY(), sliderBounds.getMaxY());

        if (e.getSceneX() < minX || e.getSceneX() > maxX ||
                e.getSceneY() < minY || e.getSceneY() > maxY) {
            hideVolumeSlider();
        }
    }
    /* toggle volume mute */
    public void toggleMute() {
        if (!isMuted) {
            previousVolume = sliderVolume.getValue();
            sliderVolume.setValue(0.0);
            isMuted = true;
            btnSound.setGraphic(createIconView(volumeMute));
        } else {
            sliderVolume.setValue(previousVolume);
            isMuted = false;
            // call updateVolumeIcon(...) to show normal stage1/2/3
            updateVolumeIcon(sliderVolume.getValue());
        }
    }

    /* cursor hide helper */
    private void hideMouseCursor() {
        videocontainer.setCursor(Cursor.NONE);  // Hide cursor
    }

    /* cursor show helper */
    private void showMouseCursor() {
        videocontainer.setCursor(Cursor.DEFAULT);  // Restore default cursor
    }

    /* close video when navigating into another scene*/
    public void disposePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /* mini skip forward */
    private void skipForward(double sec) {
        if (mediaPlayer != null) {
            Duration current = mediaPlayer.getCurrentTime();
            Duration total   = mediaPlayer.getTotalDuration();
            Duration next    = current.add(Duration.seconds(sec));
            if (next.greaterThan(total)) {
                next = total;
            }
            mediaPlayer.seek(next);
        }
    }

    /* mini skip backward */
    private void skipBackward(double sec) {
        if (mediaPlayer != null) {
            Duration current = mediaPlayer.getCurrentTime();
            Duration prev    = current.subtract(Duration.seconds(sec));
            if (prev.lessThan(Duration.ZERO)) {
                prev = Duration.ZERO;
            }
            mediaPlayer.seek(prev);
        }
    }
}
