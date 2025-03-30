package Student.learningPage;

import Database.*;
import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXML;
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

    @FXML
    public Button btnQuiz;

    public Button btnEP;
    public Button btnEP1;
    public Label playlistcount;
//    public Label commentcount;
    private VideoPlayerManager videoManager;
    private int countLike = 224;
    private int countDisLike = 17;

    private String courseID = "126"; /// TEST
    private String chapterID;
    private String userID;
    private Navigator navigator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();
        loadVideoPlayer();
        HomeController method_home = new HomeController();
        Navigator method_navigator = new Navigator();
        loadPlaylist();
        toQuizButton();

        subject_name.setText("Test Subject");
        ep.setText("Test Episode : 0");
        teacherName.setText("Wirayabovorn Boonpriaw");
        role.setText("ศาสตราจารย์");
        clipDescription.setText("ความรักกันไม่ได้หรอก ฉันชอบแอบมานานแล้ว พี่พรรลบ เขาเทอไม่รักฉัน เขาไม่แย่งเธอหรอก. 7 yrs. 2. ยะศิษย์ แดน เพ็งเซ้ง. คนอื่นไม่รู้ แต่กรูดูจนจบฮ่าๆๆ แหวงเเป๊ก เย๊กกะไฟฟ้า");
        method_home.loadAndSetImage(teacherImg, "/img/Profile/user.png");

        labelPercent.setText("69%");

        nextCourseName.setText("DSA");
        nextTeacherName.setText("อาจารย์ขิม ใจดี");
        method_home.loadAndSetImage(nextImgCourse, "/img/Profile/user.png");

        btnLike.setText(String.valueOf(countLike));
        btnDislike.setText(String.valueOf(countDisLike));

//        commentcount.setText("2");
        method_home.hoverEffect(btnContectTeacher);
        method_home.hoverEffect(btnGloblalChat);
        method_home.hoverEffect(btnLike);
        method_home.hoverEffect(btnDislike);
        method_home.hoverEffect(btnOffLoad);
        method_home.hoverEffect(nextCourse);

    }

    public void toQuizButton(){
        navigator = new Navigator();
        btnQuiz.setOnAction(actionEvent -> {
            navigator = new Navigator();
            navigator.QuizPage();
        });
    }

    public void receiveData(String courseID, String chapterID, String userID) {
        VideoPathDB videoDB = new VideoPathDB();
        QuizDB quizDB = new QuizDB();
        ChapterDB chapterDB = new ChapterDB();
        CourseDB courseDB = new CourseDB();
        ProgressDB progressDB = new ProgressDB();
        UserDB userDB = new UserDB();
        LearningPageDB learningPagePB = new LearningPageDB();

        this.courseID = courseID;
        this.chapterID = chapterID;
        this.userID = userID;

        loadChapterContent();
        loadTeacherInfo();
        loadPlaylist();
        loadProgress();
        loadRecommendedCourses();
        loadComments(); // optional
    }

    private void loadChapterContent() {
    }

    private void loadTeacherInfo() {
    }

    private void loadComments() { // optional
    }

    private void loadRecommendedCourses() {
    }

    private void loadProgress() {
    }

    private void loadPlaylist() {
        playlistcontainer.getChildren().clear();

        PlaylistDB playlistDB = new PlaylistDB();
        ArrayList<String[]> chapters = playlistDB.getChaptersByCourseID(courseID); // use real courseID

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


    public void recieveMethod(String courseid) {
        // Set the course ID from MyCourse
        this.courseID = courseid;

        // Query chapters for this course
        ChapterDB chapterDB = new ChapterDB();
        ArrayList<String[]> chapters = chapterDB.getChaptersByCourseID(courseid);

        if (!chapters.isEmpty()) {
            // For example, choose the first chapter as default
            this.chapterID = chapters.get(0)[0]; // Chapter_ID is at index 0
            System.out.println("Default chapter set: " + chapterID);

            // Optionally update playlist UI here
            loadPlaylist();

            // Load content (video or quiz) for the selected chapter
            loadChapterContent();
        } else {
            System.err.println("No chapters found for course ID: " + courseid);
        }
    }

    private void loadVideoPlayer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/videoPlayer.fxml"));
            StackPane videoRoot = loader.load();
            videoManager = loader.getController(); // <== save controller
            mediacontainer.getChildren().setAll(videoRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VideoPlayerManager getVideoManager() {
        return videoManager;
    }

}
