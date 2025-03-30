package Student.learningPage;

 // TEST IN MYCOURSE

import Database.*;
import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class learningPageController implements Initializable, DisposableController  {

    public VBox mediacontainer;
    public VBox playlistcontainer;

    public Label subject_name;
    public Label ep;
    public Label teacherName;
    public Label role;
    public Label labelPercent;
    public Label btnRoadmap;
    public Label category;
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

        int episodeNumber = chapterDB.getEpisodeNumber(courseID, chapterID);
        if (episodeNumber != -1) {
            ep.setText("Episode : " + episodeNumber);
        } else {
            ep.setText(""); // fallback
        }
    }

    private void loadTeacherInfo() {
        try {
            UserDB userDB = new UserDB();
            int cid = Integer.parseInt(courseID);  // Ensure it's an int
            System.out.println("🔍 Fetching teacher info for courseID = " + cid);

            String[] info = userDB.getUserNameProfileAndSpecByCourseID(cid);
            if (info != null) {
                String username = info[0];
                String profilePath = info[1];
                String desc = info[2];

                System.out.println("✅ Username: " + username);
                System.out.println("🖼️ Profile Path: " + profilePath);
                System.out.println("📜 Description: " + desc);

                teacherName.setText(username);
                role.setText(desc);

                HomeController method_home = new HomeController();
                method_home.loadAndSetImage(teacherImg, profilePath);
            } else {
                System.out.println("⚠️ No teacher info found for courseID: " + cid);
            }

        } catch (Exception e) {
            System.out.println("❌ Error while loading teacher info:");
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
        ArrayList<String[]> chapters = chapterDB.getChaptersByCourseID(this.courseID);

        if (!chapters.isEmpty()) {
            this.chapterID = chapters.get(0)[0];
            System.out.println("Default chapter set: " + chapterID);

            loadVideoPlayer();
            loadPlaylist();
            loadChapterContent();
            loadTeacherInfo();
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

}
