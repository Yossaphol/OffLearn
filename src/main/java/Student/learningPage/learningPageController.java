package Student.learningPage;

import Database.*;
import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXMLLoader;
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

    public VBox leftWrapper;
    public HBox searhbar_container;
    public VBox mediacontainer;
    public VBox playlistcontainer;

    public Label subject_name;
    public Label ep;
    public Circle teacherImg;
    public Label teacherName;
    public Text clipDescription;
    public Label role;
    public Button btnLike;
    public Button btnDislike;
    public Button btnContectTeacher;
    public Button btnGloblalChat;
    public Button btnOffLoad;
    public Button btnQuiz;
    public Label countPlaylist;
    public Label labelPercent;
    public ProgressBar progressBar;
    public Label btnRoadmap;
    public Button nextCourse;
    public Label category;
    public Label nextCourseName;
    public Label nextTeacherName;
    public ProgressBar nextCourseProgressBar;
    public Rectangle nextImgCourse;
    public Label playlistcount;
    public Label commentcount;
    private VideoPlayerManager videoManager;
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
        Navigator method_navigator = new Navigator();

        ep.setText("Test Episode : 0");
        teacherName.setText("Wirayabovorn Boonpriaw");
        role.setText("ศาสตราจารย์");
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
        String[] details = chapterDB.getChapterDetailsByID(chapterID); // You’ll write this new method

        if (details != null) {
            String chapterName = details[0];
            String chapterDesc = details[1];

            subject_name.setText(chapterName != null ? chapterName : ""); // title
            clipDescription.setText(chapterDesc != null ? chapterDesc : ""); // description
        }
    }

    private void loadTeacherInfo() {
    }

    private void loadRecommendedCourses() {
    }

    private void loadProgress() {
    }

    private void loadPlaylist() {
        playlistcontainer.getChildren().clear();

        String forcedCourseID = "136";
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
        this.courseID = "136"; // hardcoded override for test (too lazy to enroll course into mycourse)

        ChapterDB chapterDB = new ChapterDB();
        ArrayList<String[]> chapters = chapterDB.getChaptersByCourseID(this.courseID);

        if (!chapters.isEmpty()) {
            this.chapterID = chapters.get(0)[0];
            System.out.println("Default chapter set: " + chapterID);

            loadVideoPlayer();
            loadPlaylist();
            loadChapterContent();
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
