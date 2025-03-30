package Student.learningPage;

 // TEST IN MYCOURSE

import Database.*;
import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.application.Platform;
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
import java.util.*;

public class learningPageController implements Initializable, DisposableController  {

    public VBox mediacontainer;
    public VBox playlistcontainer;
    public HBox rootpage;

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

    @FXML
    private Button btnQuiz;
    public Button btnLike;
    public Button btnDislike;
    public Button btnContectTeacher;
    public Button btnGloblalChat;
    public Button btnOffLoad;
    public Button nextCourse;

    public ProgressBar progressBar;
    public ProgressBar nextCourseProgressBar;

    private VideoPlayerManager videoManager;
    private Navigator navigator;

    private int countLike = 224;
    private int countDisLike = 17;

    private String courseID;
    private String chapterID;
    private String userID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rootpage.getStylesheets().add(
                getClass().getResource("/css/learningPage.css").toExternalForm()
        );
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();
        HomeController method_home = new HomeController();

        method_home.loadAndSetImage(teacherImg, "/img/Profile/user.png");
        labelPercent.setText("69%");

        nextCourseName.setText("DSA");
        nextTeacherName.setText("อาจารย์ขิม ใจดี");
        method_home.loadAndSetImage(nextImgCourse, "/img/Profile/user.png");

        btnLike.setText(String.valueOf(countLike));
        btnDislike.setText(String.valueOf(countDisLike));

        toQuizButton();

        method_home.hoverEffect(btnContectTeacher);
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
            navigator.QuizPage();
        });
    }

    public void receiveData(String courseID, String chapterID, String userID) {
        QuizDB quizDB = new QuizDB();
        ChapterDB chapterDB = new ChapterDB();
        CourseDB courseDB = new CourseDB();
        ProgressDB progressDB = new ProgressDB();
        UserDB userDB = new UserDB();
        LearningPageDB learningPagePB = new LearningPageDB();

        this.courseID = courseID;
        this.chapterID = chapterID;
        this.userID = userID;

        disposePlayer();        // Stop previous video if switching chapters
        loadVideoPlayer();
        loadChapterContent();
        loadTeacherInfo();
        loadPlaylist();
        loadProgress();
        loadRecommendedCourses();
    }

    private void loadChapterContent() {
        ChapterDB chapterDB = new ChapterDB();
        String[] details = chapterDB.getChapterDetailsByID(chapterID);

        if (details != null) {
            subject_name.setText(details[0] != null ? details[0] : "");
            clipDescription.setText(details[1] != null ? details[1] : "");
        }

//        int episodeNumber = chapterDB.getEpisodeNumber(courseID, chapterID);
//        if (episodeNumber != -1) {
//            ep.setText("Episode : " + episodeNumber);
//        } else {
//            ep.setText(""); // fallback
//        }
    }

    private void loadTeacherInfo() {
        try {
            int courseId = Integer.parseInt(courseID);
            UserDB userDB = new UserDB();
            String[] teacherInfo = userDB.getUserNameProfileAndSpecByCourseID(courseId);

            System.out.println("Fetching teacher info for course ID: " + courseId);

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
                System.err.println("No teacher info found for course ID: " + courseId);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Exception in loadTeacherInfo:");
            e.printStackTrace();
        }
    }

    private void loadRecommendedCourses() {
    }

    private void loadProgress() {
    }

    private void loadPlaylist() {
        playlistcontainer.getChildren().clear();

        String forcedCourseID = "138";
        PlaylistDB playlistDB = new PlaylistDB();
        ArrayList<String[]> chapters = playlistDB.getChaptersByCourseID(forcedCourseID);

        for (int i = 0; i < chapters.size(); i++) {
            String chapterId = chapters.get(i)[0];
            String chapterTitle = chapters.get(i)[1];
            int epNumber = i + 1;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/EPbutton.fxml"));
                Button EPbtn = loader.load();
                EPButtonController controller = loader.getController();

                controller.setText("EP" + epNumber + " : " + chapterTitle);
                controller.setActive(chapterId.equals(chapterID)); // compare with current

                EPbtn.setOnAction(e -> {
                    System.out.println("Clicked EP: " + epNumber + " (Chapter ID: " + chapterId + ")");
                    receiveData(courseID, chapterId, userID); // switch content
                });

                playlistcontainer.getChildren().add(EPbtn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        playlistcount.setText("(" + chapters.size() + ")");
    }

    @Override
    public void disposePlayer() {
        System.out.println("Disposing video from learningPageController...");
        if (videoManager != null) {
            videoManager.disposePlayer();  // or mediaPlayer.stop(), etc.
        }
    }


    public void recieveMethod(String ignoredCourseId) {
        this.courseID = "138"; // hardcoded override for test (too lazy to enroll course into mycourse)

        ChapterDB chapterDB = new ChapterDB();
        Category categoryDB = new Category();
        ArrayList<String[]> chapters = chapterDB.getChaptersByCourseID(this.courseID);
        String category = categoryDB.getCategoryByCourseID(Integer.parseInt(courseID));
        System.out.println("Fetched category name: " + catName);

        if (!chapters.isEmpty()) {
            this.chapterID = chapters.get(0)[0];
            System.out.println("Default chapter set: " + chapterID);

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

    private void loadVideoPlayer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/videoPlayer.fxml"));
            StackPane videoRoot = loader.load();
            videoManager = loader.getController(); // Save reference
            mediacontainer.getChildren().setAll(videoRoot);

            // 👇 Set the video path here, using your ChapterDB or chapterID
            String videoURL = new ChapterDB().getVideoURLByChapterID(chapterID);
            System.out.println("Video URL: " + videoURL);
            System.out.println("chapterID passed: " + chapterID);
            if (videoURL != null && !videoURL.isEmpty()) {
                videoManager.setVideoPath(videoURL);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VideoPlayerManager getVideoManager() {
        return videoManager;
    }



    // for testing
    private void loadTeacherImage(Shape shape, String imagePath) {
        try {
            Image image;
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                image = new Image(imagePath, true); // async loading
            } else {
                URL url = getClass().getResource(imagePath);
                if (url == null) {
                    throw new IllegalArgumentException("Local image not found: " + imagePath);
                }
                image = new Image(url.toExternalForm(), true);
            }

            // Wait for it to finish loading
            image.progressProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() >= 1.0) {
                    Platform.runLater(() -> {
                        shape.setFill(new ImagePattern(image));
                        System.out.println("Image loaded" + imagePath);
                    });
                }
            });

        } catch (Exception e) {
            System.err.println("❌ Failed to load image into shape: " + imagePath);
            e.printStackTrace();
        }
    }
}
