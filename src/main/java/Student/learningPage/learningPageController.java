package Student.learningPage;

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
import java.util.ResourceBundle;

public class learningPageController implements Initializable {

    public VBox leftWrapper;
    public HBox searhbar_container;
    public VBox mediacontainer;

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
    public Button btnEP;
    public Button btnEP1;
    public Label playlistcount;
    public Label commentcount;

    private int countLike = 224;
    private int countDisLike = 17;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();
        loadVideoPlayer();
        HomeController method_home = new HomeController();
        Navigator method_navigator = new Navigator();

        subject_name.setText("Test Subject");
        ep.setText("Test Episode : 0");
        teacherName.setText("Wirayabovorn Boonpriaw");
        role.setText("ศาสตราจารย์");
        clipDescription.setText("ความรักกันไม่ได้หรอก ฉันชอบแอบมานานแล้ว พี่พรรลบ เขาเทอไม่รักฉัน เขาไม่แย่งเธอหรอก. 7 yrs. 2. ยะศิษย์ แดน เพ็งเซ้ง. คนอื่นไม่รู้ แต่กรูดูจนจบฮ่าๆๆ แหวงเเป๊ก เย๊กกะไฟฟ้า");
        method_home.loadAndSetImage(teacherImg, "/img/Profile/user.png");

        btnEP.setText("Episode : 69");
        btnEP1.setText("Episode : 70");

        playlistcount.setText("("+2+")");
        labelPercent.setText("69%");

        nextCourseName.setText("DSA");
        nextTeacherName.setText("อาจารย์ขิม ใจดี");
        method_home.loadAndSetImage(nextImgCourse, "/img/Profile/user.png");

        btnLike.setText(String.valueOf(countLike));
        btnDislike.setText(String.valueOf(countDisLike));

        commentcount.setText("2");
        method_home.hoverEffect(btnContectTeacher);
        method_home.hoverEffect(btnGloblalChat);
        method_home.hoverEffect(btnLike);
        method_home.hoverEffect(btnDislike);
        method_home.hoverEffect(btnOffLoad);
        method_home.hoverEffect(btnEP);
        method_home.hoverEffect(nextCourse);
        method_home.hoverEffect(btnEP1);
    }
    
    private void loadVideoPlayer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/videoPlayer.fxml"));
            StackPane videoRoot = loader.load(); // The root is <StackPane> from videoPlayerManager.fxml
            mediacontainer.getChildren().setAll(videoRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
