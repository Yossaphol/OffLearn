package Student.learningPage;

// Test by clicking on course in Mycourse (id is hardcoded for now)

import Database.*;
import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class learningPageController implements Initializable, DisposableController {

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
    public Button nextCourse;
    public ProgressBar progressBar;
    public ProgressBar nextCourseProgressBar;

    private VideoPlayerManager videoManager;
    private Navigator navigator;
    private int countLike = 224;
    private int countDisLike = 17;
    private int courseID;
    private int chapterID;
    private int userID;
    private int quizID;
    private QuizDB quizDB;

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

        toQuizButton();

        btnLike.setText(String.valueOf(countLike));
        btnDislike.setText(String.valueOf(countDisLike));
//        method_home.hoverEffect(btnContectTeacher);
        method_home.hoverEffect(btnGloblalChat);
        method_home.hoverEffect(btnLike);
        method_home.hoverEffect(btnDislike);
        method_home.hoverEffect(btnOffLoad);
        method_home.hoverEffect(nextCourse);
        method_home.hoverEffect(btnQuiz);
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
        CountDownLatch latch = new CountDownLatch(2);
        AtomicReference<String[]> chapterDetailsRef = new AtomicReference<>();
        AtomicReference<Boolean> quizAvailableRef = new AtomicReference<>();

        Task<String[]> chapterTask = new Task<>() {
            @Override
            protected String[] call() {
                ChapterDB chapterDB = new ChapterDB();
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
            }
            boolean quizAvailable = quizAvailableRef.get() != null && quizAvailableRef.get();
            btnQuiz.setVisible(quizAvailable);
            btnQuiz.setDisable(!quizAvailable);
            disposePlayer();
            loadVideoPlayer();
            loadTeacherInfo();
            loadPlaylist();
            loadProgress();
            loadRecommendedCourses();
            loadAttachment();
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
                ChapterDB chapterDB = new ChapterDB();
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

    // Load teacher info asynchronously
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
                int forcedCourseID = 138; // for testing; you might want to use courseID instead
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
                    controller.setText("EP" + epNumber + " : " + chapterTitle);
                    controller.setActive(chapterId == chapterID);
                    EPbtn.setOnAction(e2 -> {
                        System.out.println("Clicked EP: " + epNumber + " (Chapter ID: " + chapterId + ")");
                        if (videoManager != null) {
                            videoManager.disposePlayer();
                        }
                        receiveData(courseID, chapterId, userID);
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
                ChapterDB chapterDB = new ChapterDB();
                return chapterDB.getVideoURLByChapterID(chapterID);
            }
        };
        task.setOnSucceeded(e -> {
            String videoURL = task.getValue();
            System.out.println("Video URL: " + videoURL);
            System.out.println("chapterID passed: " + chapterID);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/videoPlayer.fxml"));
                StackPane videoRoot = loader.load();
                videoManager = loader.getController();
                mediacontainer.getChildren().setAll(videoRoot);
                if (videoURL != null && !videoURL.isEmpty()) {
                    videoManager.setVideoPath(videoURL);
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
    private void loadRecommendedCourses() { }
    private void loadProgress() { }

    @Override
    public void disposePlayer() {
        System.out.println("Disposing video from learningPageController...");
        if (videoManager != null) {
            videoManager.disposePlayer();
        }
    }

    // For forced test—overriding courseID and chapterID
    public void recieveMethod(String ignoredCourseId) {
        this.courseID = 138; // Hardcoded for testing
        ChapterDB chapterDB = new ChapterDB();
        Category categoryDB = new Category();
        ArrayList<String[]> chapters = chapterDB.getChaptersByCourseID(this.courseID);
        String category = categoryDB.getCategoryByCourseID(courseID);
        System.out.println("Fetched category name: " + category);
        if (!chapters.isEmpty()) {
            this.chapterID = Integer.parseInt(chapters.get(0)[0]);
            System.out.println("Default chapter set: " + chapterID);
            QuizDB quizDB = new QuizDB();
            boolean quizAvailable = quizDB.isQuizAvailableForChapter(chapterID);
            btnQuiz.setVisible(quizAvailable);
            btnQuiz.setDisable(!quizAvailable);
            loadVideoPlayer();
            loadPlaylist();
            loadChapterContent();
            loadTeacherInfo();
            if (category != null) {
                catName.setText(category);
            } else {
                catName.setText("unknown");
            }
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
                ChapterDB chapterDB = new ChapterDB();
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
}
