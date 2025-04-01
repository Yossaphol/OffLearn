package Student.learningPage;

 // TEST IN MYCOURSE
import Utili.*;
import com.google.gson.Gson;
import Database.*;
import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Teacher.courseManagement.CourseItem;
import Student.HomeAndNavigation.Navigator;
import a_Session.SessionManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class learningPageController extends ChapterProgress implements Initializable, DisposableController {

    public VBox mediacontainer;
    public VBox playlistcontainer;
    public HBox rootpage;
    public VBox attachmentcontainer;
    public Label subject_name;
    public Label ep;
    public Label teacherName;
    public Label role;
    public Label labelPercent;
    public Label btnRoadmap;
    public Label catName;
    public Label nextCourseName;
    public Label nextTeacherName;
    public Label playlistcount;
    public Circle teacherImg;
    public Rectangle nextImgCourse;
    public Text clipDescription;
    @FXML private Button btnQuiz;
    public Button btnLike;
    public Button btnDislike;
    public Button btnContactTeacher;
    public Button btnGloblalChat;
    public Button btnOffLoad;
    public ProgressBar progressBar;
    public Button nextCourse;
    public ProgressBar nextCourseProgressBar;
    private EPButtonController currentlyActiveEPController = null;

    private VideoPlayerManager videoManager;
    private Navigator navigator;
    private int countLike;
    private int countDisLike;
    private int courseID;
    private int chapterID;
    private int userID;
    private int quizID;
    private QuizDB quizDB;
    private Category categoryDB;
    private ChapterDB chapterDB;
    String sessionUserID = SessionManager.getInstance().getUserID();
    private ScheduledExecutorService scheduler ;


    // Helper: run tasks on a background daemon thread
    private <T> void runBackgroundTask(Task<T> task) {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Apply CSS and load fonts
        rootpage.getStylesheets().add(getClass().getResource("/css/learningPage.css").toExternalForm());
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();
        HomeController method_home = new HomeController();

        method_home.loadAndSetImage(teacherImg, "/img/Profile/user.png");
        labelPercent.setText("69%");
        nextCourseName.setText("DSA");
        nextTeacherName.setText("อาจารย์ขิม ใจดี");
        method_home.loadAndSetImage(nextImgCourse, "/img/Profile/user.png");

        initializeVidDownloadHandler();
        toQuizButton();
        method_home.hoverEffect(btnContactTeacher);
        method_home.hoverEffect(btnGloblalChat);
        method_home.hoverEffect(btnLike);
        method_home.hoverEffect(btnDislike);
        method_home.hoverEffect(btnOffLoad);
        method_home.hoverEffect(nextCourse);
        method_home.hoverEffect(btnQuiz);

        btnContactTeacher.setOnAction(event -> {
            try {
                Navigator navigator = new Navigator();
                navigator.inboxRouteWithTeacher(teacherName.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnGloblalChat.setOnAction(event -> {
            try {
                Navigator navigator = new Navigator();
                // route to global chat here
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeScheduler() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (videoManager != null && videoManager.getVideoMediaPlayer() != null) {
                    updateProgress(videoManager.getVideoMediaPlayer(), chapterID);
                    double tmp = loadChapterProgress(String.valueOf(chapterID), sessionUserID);
                    double normalized = tmp / 100.0;
                    Platform.runLater(() -> {
                        progressBar.setProgress(normalized);
                        labelPercent.setText(String.format("%.2f%%", tmp));
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void toQuizButton(){
        btnQuiz.setOnAction(actionEvent -> {
            navigator = new Navigator();
            navigator.QuizPage(chapterID, quizID);
        });
    }

    public void receiveData(int courseID, int chapterID, int userID) {
        quizDB = new QuizDB();
        this.courseID = courseID;
        this.chapterID = chapterID;
        this.userID = userID;
        this.quizID = quizDB.getQuizIdByChapterId(this.chapterID);

        // Create a latch to wait for both tasks (set count = 2)
        CountDownLatch latch = new CountDownLatch(3);
        AtomicReference<String[]> chapterDetailsRef = new AtomicReference<>();
        AtomicReference<Boolean> quizAvailableRef = new AtomicReference<>();
        AtomicReference<boolean[]> userReactionRef = new AtomicReference<>();
        AtomicReference<int[]> totalsRef = new AtomicReference<>();

        btnLike.setDisable(true);
        btnDislike.setDisable(true);

        Task<Void> reactionTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                ChapterFavDB favDB = new ChapterFavDB();
                int[] totals = favDB.getChapterReactionTotals(chapterID);
                boolean[] userReaction = favDB.getUserReaction(Integer.parseInt(sessionUserID), chapterID);
                totalsRef.set(totals);
                userReactionRef.set(userReaction);
                latch.countDown();
                return null;
            }
        };
        runBackgroundTask(reactionTask);

        Task<String[]> chapterTask = new Task<>() {
            @Override
            protected String[] call() {
                chapterDB = new ChapterDB();
                return chapterDB.getChapterDetailsByID(chapterID);
            }
        };
        chapterTask.setOnSucceeded(e -> {
            chapterDetailsRef.set(chapterTask.getValue());
            latch.countDown();
        });
        chapterTask.setOnFailed(e -> {
            System.err.println("Error loading chapter content:");
            chapterTask.getException().printStackTrace();
            latch.countDown();
        });

        Task<Boolean> quizTask = new Task<>() {
            @Override
            protected Boolean call() {
                QuizDB quizDB = new QuizDB();
                return quizDB.isQuizAvailableForChapter(chapterID);
            }
        };
        quizTask.setOnSucceeded(e -> {
            quizAvailableRef.set(quizTask.getValue());
            latch.countDown();
        });
        quizTask.setOnFailed(e -> {
            System.err.println("Error checking quiz availability:");
            quizTask.getException().printStackTrace();
            latch.countDown();
        });

        runBackgroundTask(chapterTask);
        runBackgroundTask(quizTask);

        Task<Void> combinedTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                latch.await();
                return null;
            }
        };
        combinedTask.setOnSucceeded(e -> {
            String[] details = chapterDetailsRef.get();
            if (details != null) {
                subject_name.setText(details[0] != null ? details[0] : "");
                clipDescription.setText(details[1] != null ? details[1] : "");
                int[] totals = totalsRef.get();
                boolean[] reaction = userReactionRef.get();

                btnLike.setText(String.valueOf(totals[0]));
                btnDislike.setText(String.valueOf(totals[1]));
                setButtonIcon(btnLike, reaction[0] ? "/img/icon/likeactive.png" : "/img/icon/like.png");
                setButtonIcon(btnDislike, reaction[1] ? "/img/icon/dislikeactive.png" : "/img/icon/dislike.png");
                initializeReactionHandlers(userReactionRef.get(), totalsRef.get());
                btnLike.setDisable(false);
                btnDislike.setDisable(false);
            }
            boolean quizAvailable = quizAvailableRef.get() != null && quizAvailableRef.get();
            btnQuiz.setVisible(quizAvailable);
            btnQuiz.setDisable(!quizAvailable);
            disposePlayer();
            loadVideoPlayer();
            loadPlaylist();
            loadProgress();
            loadAttachment();
            setLoadingState(false);
        });
        combinedTask.setOnFailed(e -> {
            System.err.println("Error in combined task:");
            combinedTask.getException().printStackTrace();
        });
        runBackgroundTask(combinedTask);
    }


    // Load chapter content asynchronously
    private void loadChapterContent() {
        Task<String[]> task = new Task<>() {
            @Override
            protected String[] call() {
                chapterDB = new ChapterDB();
                return chapterDB.getChapterDetailsByID(chapterID);
            }
        };
        task.setOnSucceeded(e -> {
            String[] details = task.getValue();
            if (details != null) {
                subject_name.setText(details[0] != null ? details[0] : "");
                clipDescription.setText(details[1] != null ? details[1] : "");
            }
        });
        task.setOnFailed(e -> {
            System.err.println("Error loading chapter content:");
            task.getException().printStackTrace();
        });
        runBackgroundTask(task);
    }

//     Load teacher info asynchronously
    private void loadTeacherInfo() {
        Task<String[]> task = new Task<>() {
            @Override
            protected String[] call() {
                UserDB userDB = new UserDB();
                return userDB.getUserNameProfileAndSpecByCourseID(courseID);
            }
        };
        task.setOnSucceeded(e -> {
            String[] teacherInfo = task.getValue();
            System.out.println("Fetching teacher info for course ID: " + courseID);
            if (teacherInfo != null) {
                String teacherUsername = teacherInfo[0];
                String profilePath = teacherInfo[1];
                String description = teacherInfo[2];
                System.out.println("Teacher Username: " + teacherUsername);
                System.out.println("Profile Path: " + profilePath);
                System.out.println("Description: " + description);
                teacherName.setText(teacherUsername);
                role.setText(description);
                loadTeacherImage(teacherImg, profilePath);
            } else {
                System.err.println("No teacher info found for course ID: " + courseID);
            }
        });
        task.setOnFailed(e -> {
            System.err.println("Exception in loadTeacherInfo:");
            task.getException().printStackTrace();
        });
        runBackgroundTask(task);
    }

    // Load playlist asynchronously
    private void loadPlaylist() {
        Task<ArrayList<String[]>> task = new Task<>() {
            @Override
            protected ArrayList<String[]> call() {
                int forcedCourseID = courseID;
                PlaylistDB playlistDB = new PlaylistDB();
                return playlistDB.getChaptersByCourseID(forcedCourseID);
            }
        };
        task.setOnSucceeded(e -> {
            ArrayList<String[]> chapters = task.getValue();
            playlistcontainer.getChildren().clear();
            for (int i = 0; i < chapters.size(); i++) {
                int chapterId = Integer.parseInt(chapters.get(i)[0]);
                String chapterTitle = chapters.get(i)[1];
                int epNumber = i + 1;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/EPbutton.fxml"));
                    Button EPbtn = loader.load();
                    EPButtonController controller = loader.getController();
                    controller.setActive(chapterId == chapterID);
                    if (chapterId == chapterID) {
                        currentlyActiveEPController = controller;
                    }
                    controller.setText("EP" + epNumber + " : " + chapterTitle);
                    controller.setActive(chapterId == chapterID);
                    EPbtn.setOnAction(e2 -> {
                        if (chapterId == chapterID) {
                            System.out.println("You clicked on the currently active chapter. Ignoring.");
                            return;
                        }

                        // Update active button UI *immediately*
                        if (currentlyActiveEPController != null) {
                            currentlyActiveEPController.setActive(false); // deactivate old one
                        }
                        controller.setActive(true); // activate new one
                        currentlyActiveEPController = controller;

                        rootpage.setDisable(true);
                        EPbtn.getScene().setCursor(Cursor.WAIT);

                        if (videoManager != null) {
                            videoManager.disposePlayer();
                        }

                        receiveData(courseID, chapterId, userID); // start loading chapter
                    });
                    playlistcontainer.getChildren().add(EPbtn);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            playlistcount.setText("(" + chapters.size() + ")");
        });
        task.setOnFailed(e -> {
            System.err.println("Error loading playlist:");
            task.getException().printStackTrace();
        });
        runBackgroundTask(task);
    }

    // Load video player asynchronously (including the DB call for video URL)
    private void loadVideoPlayer() {
        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                chapterDB = new ChapterDB();
                return chapterDB.getVideoURLByChapterID(chapterID);
            }
        };
        task.setOnSucceeded(e -> {
            String videoURL = task.getValue();
            File localVideo = FileStorageHelper.getChapterVideoFile(chapterID);
            String mediaSource;
            boolean isOffline = false;
            if (localVideo.exists()) {
                mediaSource = localVideo.toURI().toString();
                isOffline = true;
                System.out.println("Playing offline video: " + mediaSource);
            } else {
                mediaSource = videoURL;
                System.out.println("Playing online video: " + mediaSource);
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/videoPlayer.fxml"));
                StackPane videoRoot = loader.load();
                videoManager = loader.getController();
                mediacontainer.getChildren().setAll(videoRoot);
                if (mediaSource != null && !mediaSource.isEmpty()) {
                    videoManager.setVideoPath(mediaSource);
                }
                if (isOffline) {
                    System.out.println("This is an offline video.");
                } else {
                    System.out.println("This is an online video.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        task.setOnFailed(e -> {
            System.err.println("Error loading video player:");
            task.getException().printStackTrace();
        });
        runBackgroundTask(task);
    }

    // Synchronous (or nearly synchronous) method for loading teacher image (could be refactored similarly)
    private void loadTeacherImage(Shape shape, String imagePath) {
        try {
            Image image;
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                image = new Image(imagePath, true);
            } else {
                URL url = getClass().getResource(imagePath);
                if (url == null) {
                    throw new IllegalArgumentException("Local image not found: " + imagePath);
                }
                image = new Image(url.toExternalForm(), true);
            }
            image.progressProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() >= 1.0) {
                    Platform.runLater(() -> {
                        shape.setFill(new ImagePattern(image));
                        System.out.println("Image loaded " + imagePath);
                    });
                }
            });
        } catch (Exception e) {
            System.err.println("❌ Failed to load image into shape: " + imagePath);
            e.printStackTrace();
        }
    }

    // Stub methods for future implementations

    private void loadProgress() {
        Task<Double> progressTask = new Task<>() {
            @Override
            protected Double call() {
                MyProgressDB progressDB = new MyProgressDB();
                return progressDB.loadChapterProgress(String.valueOf(chapterID), sessionUserID);
            }
        };

        progressTask.setOnSucceeded(e -> {
            double progress = progressTask.getValue();
            if (progress < 0) {
                progressBar.setProgress(0);
                labelPercent.setText("0%");
            } else {
                double normalized = progress / 100.0;
                progressBar.setProgress(normalized);
                labelPercent.setText(String.format("%.2f%%", progress));
            }
        });

        progressTask.setOnFailed(e -> {
            System.err.println("Error loading chapter progress:");
            progressTask.getException().printStackTrace();
        });

        runBackgroundTask(progressTask);
    }

    @Override
    public void disposePlayer() {
        System.out.println("Disposing video from learningPageController...");
        if (videoManager != null) {
            videoManager.disposePlayer();
        }
    }

    // For forced test—overriding courseID and chapterID
    public void recieveMethod(String courseID) {
        this.courseID = Integer.parseInt(courseID);
        chapterDB = new ChapterDB();
        categoryDB = new Category();
        CourseDB courseDB = new CourseDB();

        ArrayList<String[]> chapters = chapterDB.getChaptersByCourseID(this.courseID);
        String category = categoryDB.getCategoryByCourseID(this.courseID);
        CourseItem courseInfo = courseDB.getCourseByID(this.courseID);

        if (chapters != null && !chapters.isEmpty()) {
            this.chapterID = Integer.parseInt(chapters.get(0)[0]);
            setLoadingState(true);
            if (courseInfo != null) {
                subject_name.setText(courseInfo.getCourseName());
                catName.setText(category != null ? category : "unknown");
            }
            loadTeacherInfo();
            receiveData(this.courseID, this.chapterID, Integer.parseInt(sessionUserID));
        } else {
            System.err.println("No chapters found for forced course ID: " + this.courseID);
        }
    }

    public VideoPlayerManager getVideoManager() {
        return videoManager;
    }

    private void loadAttachment() {
        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                chapterDB = new ChapterDB();
                return chapterDB.getChapterMaterialByID(chapterID);
            }
        };

        task.setOnSucceeded(e -> {
            String materialURL = task.getValue();
            attachmentcontainer.getChildren().clear();

            if (materialURL != null && !materialURL.trim().isEmpty()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/attachment.fxml"));
                    HBox attachmentNode = loader.load();
                    AttachmentController controller = loader.getController();

                    // Extract filename from URL (or use fallback)
                    String fileName = materialURL.substring(materialURL.lastIndexOf('/') + 1);
                    controller.setFilename(fileName);
                    controller.setDownloadURL(materialURL);

                    attachmentcontainer.getChildren().add(attachmentNode);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        task.setOnFailed(e -> {
            System.err.println("Failed to load attachment:");
            task.getException().printStackTrace();
        });

        runBackgroundTask(task);
    }
    private void updateReactionCounts() {
        ChapterFavDB favDB = new ChapterFavDB();
        int[] totals = favDB.getChapterReactionTotals(chapterID); // totals[0]: likes, totals[1]: dislikes
        btnLike.setText(String.valueOf(totals[0]));
        btnDislike.setText(String.valueOf(totals[1]));
        System.out.println("Updated Reaction Totals -> Likes: " + totals[0] + ", Dislikes: " + totals[1]);
    }

    private void initializeReactionHandlers(boolean[] userReaction, int[] totals) {
        int userId = Integer.parseInt(sessionUserID);

        Platform.runLater(() -> {
            btnLike.setText(String.valueOf(totals[0]));
            btnDislike.setText(String.valueOf(totals[1]));
            setButtonIcon(btnLike, userReaction[0] ? "/img/icon/likeactive.png" : "/img/icon/like.png");
            setButtonIcon(btnDislike, userReaction[1] ? "/img/icon/dislikeactive.png" : "/img/icon/dislike.png");
        });

        // Like Button
        btnLike.setOnAction(e -> {
            btnLike.setDisable(true);
            btnDislike.setDisable(true);
            btnLike.getScene().setCursor(Cursor.WAIT);
            runBackgroundTask(new Task<>() {
                @Override
                protected Void call() {
                    ChapterFavDB favDB = new ChapterFavDB();
                    favDB.toggleReaction(userId, chapterID, true);
                    refreshReactions(userId);
                    Platform.runLater(() -> {
                        btnLike.setDisable(false);
                        btnDislike.setDisable(false);
                        btnLike.getScene().setCursor(Cursor.DEFAULT);
                    });
                    return null;
                }
            });
        });

        // Dislike Button
        btnDislike.setOnAction(e -> {
            btnLike.setDisable(true);
            btnDislike.setDisable(true);
            btnDislike.getScene().setCursor(Cursor.WAIT);
            runBackgroundTask(new Task<>() {
                @Override
                protected Void call() {
                    ChapterFavDB favDB = new ChapterFavDB();
                    favDB.toggleReaction(userId, chapterID, false); // fixed this!
                    refreshReactions(userId);
                    Platform.runLater(() -> {
                        btnLike.setDisable(false);
                        btnDislike.setDisable(false);
                        btnLike.getScene().setCursor(Cursor.DEFAULT);
                    });
                    return null;
                }
            });
        });
    }

    private void refreshReactions(int userId) {
        ChapterFavDB favDB = new ChapterFavDB();
        int[] totals = favDB.getChapterReactionTotals(chapterID);
        boolean[] userReaction = favDB.getUserReaction(userId, chapterID);

        setButtonIcon(btnLike, userReaction[0] ? "/img/icon/likeactive.png" : "/img/icon/like.png");
        setButtonIcon(btnDislike, userReaction[1] ? "/img/icon/dislikeactive.png" : "/img/icon/dislike.png");
        Platform.runLater(() -> {
            btnLike.setText(String.valueOf(totals[0]));
            btnDislike.setText(String.valueOf(totals[1]));
        });
    }

    private void setButtonIcon(Button button, String iconPath) {
        Image img = new Image(getClass().getResource(iconPath).toExternalForm());
        ImageView icon = new ImageView(img);
        icon.setFitWidth(22);
        icon.setFitHeight(22);
        button.setGraphic(icon);
    }

    private void setLoadingState(boolean isLoading) {
        Scene scene = rootpage.getScene();
        if (scene != null) {
            scene.setCursor(isLoading ? Cursor.WAIT : Cursor.DEFAULT);
        }
        rootpage.setDisable(isLoading);
    }

    public void initializeVidDownloadHandler() {
        btnOffLoad.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to download the video for offline use?", ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Download Video");
            confirm.setHeaderText(null);
            if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                btnOffLoad.setDisable(true);
                rootpage.getScene().setCursor(Cursor.WAIT);
                downloadChapterVideo();

            }
        });
    }

    private void downloadChapterVideo() {
        String videoURL = new ChapterDB().getVideoURLByChapterID(chapterID);
        if (videoURL == null || videoURL.isEmpty()) {
            showAlert("Error", "Video URL not available.", Alert.AlertType.ERROR);
            btnOffLoad.setDisable(false);
            rootpage.getScene().setCursor(Cursor.DEFAULT);
            return;
        }

        File destination = FileStorageHelper.getChapterVideoFile(chapterID);
        System.out.println("📥 Starting video download...");
        System.out.println("   ➤ URL: " + videoURL);
        System.out.println("   ➤ Destination: " + destination.getAbsolutePath());

        Task<Void> downloadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try (InputStream in = new URL(videoURL).openStream();
                     FileOutputStream out = new FileOutputStream(destination)) {

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        if (isCancelled()) break;
                        out.write(buffer, 0, bytesRead);
                    }
                }
                return null;
            }
        };

        downloadTask.setOnSucceeded(e -> {
            System.out.println("✅ Video downloaded successfully.");
            Platform.runLater(() -> {
                showAlert("Download Completed", "Video downloaded to:\n" + destination.getAbsolutePath(), Alert.AlertType.INFORMATION);
                btnOffLoad.setDisable(false);
                rootpage.getScene().setCursor(Cursor.DEFAULT);

                try {
                    System.out.println("📁 Saving offline chapter metadata...");
                    saveCourseInfoIfNeeded(userID, courseID);

                    ChapterDB chapterDB = new ChapterDB();
                    String[] details = chapterDB.getChapterDetailsByID(chapterID);
                    CourseDB courseDB = new CourseDB();
                    String courseName = courseDB.getCourseNameByID(courseID);
                    String courseDesc = courseDB.getCourseDescriptionByID(courseID);

                    OfflineCourseData offlineData = new OfflineCourseData();
                    offlineData.setUserid(userID);
                    offlineData.setCourseID(courseID);
                    offlineData.setChapterID(chapterID);
                    offlineData.setChapterName(details[0]);
                    offlineData.setChapterDescription(details[1]);
                    offlineData.setCourseCategory(catName.getText());
                    offlineData.setTeacherName(teacherName.getText());
                    offlineData.setVideoPath(destination.getAbsolutePath());
                    offlineData.setCourseName(courseName);
                    offlineData.setCourseDescription(courseDesc);


                    Utili.OfflineCourseManager.saveChapter(userID, offlineData);
                    System.out.println("📝 Offline data saved for Chapter ID: " + chapterID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert("Offline Save Failed", "Failed to save chapter data.", Alert.AlertType.ERROR);
                }
            });
        });

        downloadTask.setOnFailed(e -> {
            System.err.println("❌ Video download failed.");
            downloadTask.getException().printStackTrace();
            Platform.runLater(() -> {
                showAlert("Download Failed", "Failed to download video.", Alert.AlertType.ERROR);
                btnOffLoad.setDisable(false);
                rootpage.getScene().setCursor(Cursor.DEFAULT);
            });
        });

        runBackgroundTask(downloadTask);
    }


    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveCourseInfoIfNeeded(int userID, int courseID) {
        File courseInfoFile = new File(OfflinePathHelper.getCourseFolder(userID, courseID), "course_info.json");
        if (courseInfoFile.exists()) return;

        UserDB userDB = new UserDB();
        String[] teacherInfo = userDB.getUserNameProfileAndSpecByCourseID(courseID);
        Category cat = new Category();
        String category = cat.getCategoryByCourseID(courseID);
        CourseDB courseDB = new CourseDB();
        String courseName = courseDB.getCourseNameByID(courseID);
        String courseDesc = courseDB.getCourseDescriptionByID(courseID);

        OfflineCourseInfo info = new OfflineCourseInfo();
        info.setUserID(userID);
        info.setCourseID(courseID);
        info.setCourseName(courseName); // Optionally get actual name from DB
        info.setCourseDescription(courseDesc);
        info.setCourseCategory(category != null ? category : "Unknown");
        info.setTeacherName(teacherInfo != null ? teacherInfo[0] : "Unknown");// Optional

        try {
            String json = new Gson().toJson(info);
            Files.writeString(courseInfoFile.toPath(), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
