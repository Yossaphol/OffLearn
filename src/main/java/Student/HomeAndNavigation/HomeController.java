package Student.HomeAndNavigation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import Database.*;
import Student.HomeAndNavigation.*;
import Student.courseManage.courseInfoController;
import Student.dashboard.CourseScore;
import Student.learningPage.learningPageController;
import Student.myCourse.courseCardController;
import Teacher.courseManagement.CourseItem;
import a_Session.SessionHandler;
import a_Session.SessionManager;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.io.InputStream;

public class HomeController implements Initializable, SessionHandler {

    @FXML

    public ProgressBar progress1;
    public ProgressBar progress2;
    public Label progressValue2;
    public Label progressValue1;
    public ProgressBar continueProgress;
    public Label progressOfConValue;
    public Rectangle imgContainer;

    public Circle teacherBanner;

    public Text subjectName;

    public Label teacherName;

    public Circle pfp;
    public Circle pfp_statistic;
    public Circle teacher_pfp;
    public Circle leaderboard_pfp;
    public Circle leaderboard_pfp1;
    public Circle leaderboard_pfp2;
    public Circle leaderboard_pfp3;
    public Rectangle course_pic;
    public Circle category_pic;
    public VBox popup;
    public Label progressCategory;
    public ProgressBar categorybar;
    public HBox forAddL;
    public HBox hideIfpopupOpen;
    public HBox bg;
    public Circle category_pic1;
    public Circle category_pic2;
    public Label progressCategory1;
    public ProgressBar categorybar1;
    public ProgressBar categorybar2;
    public Label progressCategory2;
    public VBox popup1;
    public VBox popup2;
    public HBox dashboard;
    public HBox course;
    public HBox inbox;
    public HBox task;
    public HBox roadmap;
    public HBox home;
    public ProgressBar codingProgress;
    public VBox categoryPopup;
    public HBox category_recommend;
    public Button seeAll;
    public ProgressBar businessProgress;
    public ProgressBar aiProgress;
    public ProgressBar mathProgress;
    public Button yourCoursebtn;
    public Button allCoursebtn;
    public HBox setting;
    public HBox logout;
    public Pane topLeaderboard;
    public Pane smallStatistic;
    public Button btn_dashboard_atStatistics;
    public Pane smallUserProfile;
    public Pane cat1;
    public Pane cat2;
    public Pane cat3;
    public HBox learn_now;
    public HBox home_nav;
    public AnchorPane slider;
    public AnchorPane slide1;
    public AnchorPane slide2;
    public Button next;
    public Button previous;
    public ProgressBar continueProgressOOP;
    public Label progressOfConValueOOP;
    public Label progressOfConValueData;
    public ProgressBar continueProgressData;
    public Circle teacher_pfp_Data;
    public Circle teacher_pfp_OOP;
    public Rectangle course_pic_Data;
    public Rectangle course_pic_OOP;
    public Button cart;
    public HBox profile_btn;
    public Button pfp_btn;
    public ScrollPane mainScrollPane;
    public HBox rootpage;
    public VBox top4Container;
    private List<AnchorPane> slides;
    private int slideIndex = 0;
    public Label setname;

    public Circle imgTopOne;
    public Circle imgTopTwo;
    public Circle imgTopThree;
    public Circle imgTopFour;
    public Label nameTopOne;
    public Label nameTopTwo;
    public Label nameTopThree;
    public Label nameTopFour;
    public Label scoreTopOne;
    public Label scoreTopTwo;
    public Label scoreTopThree;
    public Label scoreTopFour;
    public Button previousButton;
    public Button nextButton;
    public HBox container;
    @FXML
    private VBox calendarContainer;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    MyCourseDB myCourseDB = new MyCourseDB();

    private int currentPage = 0;
    private final int coursesPerPage = 2;
    private ArrayList<CourseItem> courseList;
    private int userID;
    ArrayList<MyCourse> myCourses = myCourseDB.getallMyCourse();

    String username = SessionManager.getInstance().getUsername();
    UserDB userDB = new UserDB();
    CourseDB courseDB = new CourseDB();
    MyProgressDB myProgressDB = new MyProgressDB();
    Category category = new Category();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        progressValue();
        setupBarChart();
        setImgContainer();
        route();
        loadStd();
        if (username != null) {
            setname.setText(username);
        } else {
            setname.setText("Guest");
        }

        previousButton.setOnAction(event -> handlePreviousButton());
        nextButton.setOnAction(event -> handleNextButton());

        hoverEffect(topLeaderboard);
        hoverEffect(smallStatistic);
        hoverEffect(btn_dashboard_atStatistics);


//        applyHoverEffectToInside(slide1);
//        applyHoverEffectToInside(slide2);
//        callSlider();
        loadCourses();
        setProfile(userDB.getProfile(username));
    }

    @Override
    public void handleSession() {
        UserDB userDB = new UserDB();
        this.userID = userDB.getUserId(SessionManager.getInstance().getUsername());
    }

    private void loadCourses() {
        container.getChildren().clear();
        ArrayList<CourseItem> courseList = new ArrayList<>();
        ArrayList<MyCourse> myCourses = myCourseDB.getallMyCourse();

        for (MyCourse myCourse : myCourses) {
            double progress = myProgressDB.sumChapterProgress(myCourse.getCourse_ID(), myCourse.getCat_ID(), SessionManager.getInstance().getUserID());
            int courseID = Integer.valueOf(myCourse.getCourse_ID());

            if (progress >= 10 && progress < 80) {
                CourseItem courseItem = new CourseItem(courseID, myCourse.getImage(), myCourse.getCourseName(), 0, category.getCatName(myCourse.getCat_ID()), myCourse.getCourseDescription(), progress);
                courseItem.setTeacherName(courseDB.getTeacherNameByCourseName(myCourse.getCourseName()));
                courseList.add(courseItem);
            }
        }

        int start = currentPage * coursesPerPage;
        int end = Math.min(start + coursesPerPage, courseList.size());

        if (courseList.isEmpty()) {
            Label txt = new Label("ยังไม่มีคอร์สที่กำลังเรียนอยู่");
            txt.setStyle("-fx-font-size: 20px;");
            container.getChildren().add(txt);
            return;
        }

        for (int i = start; i < end; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/courseInfo.fxml"));
                Node courseNode = loader.load();
                courseInfoController controller = loader.getController();
                controller.setCourseInformation(courseList.get(i));
                hoverEffect(courseNode);
                container.getChildren().add(courseNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handlePreviousButton() {
        if (currentPage > 0) {
            currentPage--;
            loadCourses();
        }
    }

    @FXML
    private void handleNextButton() {
        if ((currentPage + 1) * coursesPerPage < courseList.size()) {
            currentPage++;
            loadCourses();
        }
    }

    public void callSlider(){
        slides = new ArrayList<>();
        slides.add(slide1);
        slides.add(slide2);

        updateSlide();
        next.setOnAction(event -> showNext());
        previous.setOnAction(event -> showPrevious());
    }

    private void updateSlide(){
        for(int i=0; i<slides.size(); i++){
            slides.get(i).setVisible(i == slideIndex);
        }
    }

    private void showNext(){
        slideIndex = (slideIndex + 1)%slides.size();
        slideTransition(slides.get(slideIndex));
        updateSlide();
    }

    private void showPrevious(){
        slideIndex = (slideIndex-1+slides.size())%slides.size();
        slideTransition(slides.get(slideIndex));
        updateSlide();
    }

    private void slideTransition(Node slide) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), slide);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }


    public void hoverEffect(Button btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.05);
        scaleDown.setFromY(1.05);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
        });
    }

//    public void hoverEffect(Pane pane) {
//        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), pane);
//        scaleUp.setFromX(1);
//        scaleUp.setFromY(1);
//        scaleUp.setToX(1.03);
//        scaleUp.setToY(1.03);
//
//        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), pane);
//        scaleDown.setFromX(1.03);
//        scaleDown.setFromY(1.03);
//        scaleDown.setToX(1);
//        scaleDown.setToY(1);
//
//        DropShadow dropShadow = new DropShadow();
//        dropShadow.setRadius(10);
//        pane.setOnMouseEntered(mouseEvent -> {
//            scaleUp.play();
//            dropShadow.setColor(Color.web("#8100CC", 0.25));
//            pane.setEffect(dropShadow);
//        });
//
//        pane.setOnMouseExited(mouseEvent -> {
//            scaleDown.play();
//            dropShadow.setColor(Color.web("#c6c6c6", 0.25));
//            pane.setEffect(dropShadow);
//        });
//    }

    public void hoverEffect(VBox vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT);
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#8100CC", 0.25)))
            );
            timeline.play();
        });

        vBox.setOnMouseExited(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), dropShadow.getColor())),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT))
            );
            timeline.play();
        });
    }

    public void hoverEffectPane(Pane vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT);
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#8100CC", 0.25)))
            );
            timeline.play();
        });

        vBox.setOnMouseExited(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), dropShadow.getColor())),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT))
            );
            timeline.play();
        });
    }

    public void hoverEffect(Node vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT);
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#8100CC", 0.25)))
            );
            timeline.play();
        });

        vBox.setOnMouseExited(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), dropShadow.getColor())),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT))
            );
            timeline.play();
        });
    }


    public void hoverEffect(HBox hBox) {
        // Scale transition
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), hBox);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.07);
        scaleUp.setToY(1.07);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), hBox);
        scaleDown.setFromX(1.07);
        scaleDown.setFromY(1.07);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        DropShadow glow = new DropShadow();
        glow.setRadius(20);
        glow.setSpread(0.5);
        glow.setColor(Color.web("#8100CC", 0.6));

        hBox.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
            switch (hBox.getId()) {
                case "logoutBtn":
                    hBox.setStyle("-fx-background-color: #FFEBEB;");
                    break;
                case "learn_now":
                    hBox.setEffect(glow);
                    hBox.setStyle(
                            "-fx-background-radius: 30; " +
                                    "-fx-background-color: linear-gradient(to right, #8100CC, #A000FF);"
                    );
                    break;
                case "pre_test":
                    hBox.setEffect(glow);
                    hBox.setStyle(
                            "-fx-background-radius: 30; " +
                                    "-fx-background-color:  linear-gradient(from 0% 0% to 100% 100%, #8100cc, #410066);"
                    );
                    break;
                default:
                    hBox.setStyle("-fx-background-color: #F7E9FF;");
                    break;
            }
        });

        hBox.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            switch (hBox.getId()){
                case "learn_now":
                    hBox.setEffect(null);
                    hBox.setStyle("-fx-background-radius: 30;" + "-fx-background-color: #8100CC;");
                    break;
                case "pre_test":
                    hBox.setEffect(null);
                    hBox.setStyle("-fx-background-radius: 30;" + "-fx-background-color:  linear-gradient(from 0% 0% to 100% 100%, #8100cc, #410066);");
                    break;
                default :
                    hBox.setStyle("-fx-background-color: transparent;");
            }

        });
    }


    public void route(){
        Navigator nav = new Navigator();
        //Dashboard
        btn_dashboard_atStatistics.setOnMouseClicked(nav::dashboardRoute);
        //Leaderboard
        topLeaderboard.setOnMouseClicked(nav::leaderboardRoute);

    }




    public void setProfile(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        this.pfp_statistic.setStroke(Color.TRANSPARENT);
        this.pfp_statistic.setFill(new ImagePattern(img));
    }

    public void loadAndSetImage(Shape shape, String path) {
        if (path == null || path.isBlank()) {
            System.out.println("⚠️ รูปภาพไม่ถูกกำหนด (null หรือว่างเปล่า)");
            return;
        }

        Image img = null;

        try {
            if (path.startsWith("/img/")) {
                // โหลดจาก resources (classpath)
                InputStream stream = getClass().getResourceAsStream(path);
                if (stream == null) {
                    System.out.println("❌ ไม่พบ resource path: " + path);
                    return;
                }
                img = new Image(stream);
            } else {
                // โหลดจาก path ภายนอก
                img = new Image("file:" + path);
            }

            shape.setStroke(Color.TRANSPARENT);
            shape.setFill(new ImagePattern(img));
            System.out.println("✅ โหลดภาพสำเร็จ: " + path);

        } catch (Exception e) {
            System.out.println("❌ เกิดข้อผิดพลาดในการโหลดภาพ: " + path);
            e.printStackTrace();
        }
    }


    private void progressValue() {
        //   progress1.setProgress(Double.parseDouble(progressValue1.getText().replace("%", "").trim()) / 100);
//        progress2.setProgress(Double.parseDouble(progressValue2.getText().replace("%", "").trim()) / 100);
//        continueProgress.setProgress(Double.parseDouble(progressOfConValue.getText().replace("%", "").trim()) / 100);
    }

    private void setupBarChart() {
        ScoreDB scoreDB = new ScoreDB();
        ArrayList<CourseScore> topScores = scoreDB.getTopThreeCoursesByScore(userID); // ดึงคะแนน 3 อันดับสูงสุด

        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        if (topScores.isEmpty()) {
            System.out.println("No scores available to display.");
            return;
        }

        for (int i = 0; i < topScores.size(); i++) {
            CourseScore courseScore = topScores.get(i);
            XYChart.Data<String, Number> data = new XYChart.Data<>(courseScore.getCourseName(), courseScore.getScore());
            series.getData().add(data);

            int finalI = i;
            data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    if (finalI % 2 == 0) {
                        newNode.setStyle("-fx-bar-fill: #8100CC;");
                    } else {
                        newNode.setStyle("-fx-bar-fill: #C35BFF;");
                    }
                }
            });
        }

        barChart.getData().add(series);
    }


//    public void applyHoverEffectToInside(AnchorPane root) {
//        for (Node node : root.lookupAll(".continueCourse")) {
//            if (node instanceof Pane p) {
//                hoverEffect(p);
//            }
//        }
//    }

    public void applyHoverEffectToInside(GridPane root) {
        for (Node node : root.lookupAll(".forHover")) {
            if (node instanceof Pane p) {
                hoverEffect(p);
            }
        }
    }

    public void applyHoverEffectToInside(VBox root) {
        for (Node node : root.lookupAll(".explore_roadmapOrAddList")) {
            if (node instanceof HBox box) {
                applyHoverEffect1(box);
            }
        }
        for (Node node : root.lookupAll(".explore_roadmapOrAddList")) {
            if (node instanceof Button box) {
                applyHoverEffect1(box);
            }
        }
        for (Node node : root.lookupAll(".small-category")) {
            if (node instanceof HBox box) {
                applyHoverEffect(box);
            }
        }
    }



    private void applyHoverEffect(HBox categoryBox) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), categoryBox);
        scaleUp.setToX(1.08);
        scaleUp.setToY(1.08);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), categoryBox);
        scaleDown.setToX(1);
        scaleDown.setToY(1);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.web("#c4c4c4", 0.25));
        categoryBox.setEffect(dropShadow);

        categoryBox.setOnMouseEntered(event -> {
            scaleUp.play();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#8100CC", 0.25)))
            );
            timeline.play();
        });
        categoryBox.setOnMouseExited(event -> {
            scaleDown.play();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), dropShadow.getColor())),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#c4c4c4", 0.25)))
            );
            timeline.play();
        });
    }

    private void applyHoverEffect1(HBox categoryBox) {
        categoryBox.setOnMouseEntered(event -> {
            categoryBox.setStyle("-fx-background-color: #F4F4F4;");
        });
        categoryBox.setOnMouseExited(event -> {
            categoryBox.setStyle("-fx-background-color: transparent;");
        });
    }

    private void applyHoverEffect1(Button categoryBox) {
        categoryBox.setOnMouseEntered(event -> {
            categoryBox.setStyle("-fx-background-color: #F4F4F4;");
        });
        categoryBox.setOnMouseExited(event -> {
            categoryBox.setStyle("-fx-background-color: white;");
        });
    }

    EnrollDB enrollDta = new EnrollDB();
    UserDB user = new UserDB();
    StudentScoreDB scoreDB = new StudentScoreDB();
    int std_id = user.getUserId(username);
    List<String> studentInCourse;
    private int courseID;
    List<String> EnrolledCourses = enrollDta.getEnrolledCourseNames(std_id);

    private void loadStd(){
        if(!EnrolledCourses.isEmpty()){
            for(String courseName: EnrolledCourses){
                courseID = scoreDB.getCourseIdByCourseName(courseName);
                if(courseID != -2){
                    loadStd(courseName);
                    return;
                }
            }
        }
        setTopStudentToDefault();
    }

    private void loadStd(String courseName) {
        studentInCourse = enrollDta.getStudentsByCourseName(courseName);

        Map<String, Integer> studentScores = new HashMap<>(); //Get score
        for (String student : studentInCourse) {
            int studentId = user.getUserId(student);
            studentScores.put(student, scoreDB.getStudentScore(studentId, courseID));
        }

        studentInCourse.sort((s1, s2) -> Integer.compare(studentScores.get(s2), studentScores.get(s1))); //Sort

        if (!studentInCourse.isEmpty()) {
            if (studentInCourse.size() >= 4) {
                setFirst(studentInCourse.get(0), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(0)), courseID), user.getProfile(studentInCourse.get(0)));
                setSecond(studentInCourse.get(1), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(1)), courseID), user.getProfile(studentInCourse.get(1)));
                setThird(studentInCourse.get(2), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(2)), courseID), user.getProfile(studentInCourse.get(2)));
                setFourth(studentInCourse.get(3), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(3)), courseID), user.getProfile(studentInCourse.get(3)));
            } else if (studentInCourse.size() == 3) {
                setFirst(studentInCourse.get(0), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(0)), courseID), user.getProfile(studentInCourse.get(0)));
                setSecond(studentInCourse.get(1), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(1)), courseID), user.getProfile(studentInCourse.get(1)));
                setThird(studentInCourse.get(2), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(2)), courseID), user.getProfile(studentInCourse.get(2)));
                setFourth("Fourth", 0, "/img/Profile/user.png");
            } else if (studentInCourse.size() == 2) {
                setFirst(studentInCourse.get(0), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(0)), courseID), user.getProfile(studentInCourse.get(0)));
                setSecond(studentInCourse.get(1), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(1)), courseID), user.getProfile(studentInCourse.get(1)));
                setThird("Third", 0, "/img/Profile/user.png");
                setFourth("Fourth", 0, "/img/Profile/user.png");
            } else if (studentInCourse.size() == 1) {
                setFirst(studentInCourse.get(0), scoreDB.getStudentScore(user.getUserId(studentInCourse.get(0)), courseID), user.getProfile(studentInCourse.get(0)));
                setSecond("Second", 0, "/img/Profile/user.png");
                setThird("Third", 0, "/img/Profile/user.png");
                setFourth("Fourth", 0, "/img/Profile/user.png");
            }
        }else{
            setTopStudentToDefault();
        }
    }

    private void setTopStudentToDefault(){
        setFirst("First", 0, "/img/Profile/user.png");
        setSecond("Second", 0, "/img/Profile/user.png");
        setThird("Third", 0, "/img/Profile/user.png");
        setFourth("Fourth", 0, "/img/Profile/user.png");
    }

    private void setFirst(String name, int point, String imgPath){
        nameTopOne.setText(name);
        scoreTopOne.setText(String.valueOf(point));
        setPic(imgPath, imgTopOne);
    }

    private void setSecond(String name, int point, String imgPath){
        nameTopTwo.setText(name);
        scoreTopTwo.setText(String.valueOf(point));
        setPic(imgPath, imgTopTwo);
    }

    private void setThird(String name, int point, String imgPath){
        nameTopThree.setText(name);
        scoreTopThree.setText(String.valueOf(point));
        setPic(imgPath, imgTopThree);
    }

    private void setFourth(String name, int point, String imgPath){
        nameTopFour.setText(name);
        scoreTopFour.setText(String.valueOf(point));
        setPic(imgPath, imgTopFour);
    }

    public void setPic(String Url, Circle profilePic){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        profilePic.setStroke(Color.TRANSPARENT);
        profilePic.setFill(new ImagePattern(img));
    }

    public void setImgContainer() {
        CourseItem courseItem = courseDB.getLatestCourse();

        String profile = userDB.getUserNameProfileAndSpecByCourseID(courseItem.getCourseId())[1];
        String banner ;
        if (courseItem.getCourseImg() == null){
            banner = "/img/Picture/florian-olivo-4hbJ-eymZ1o-unsplash.jpg";
        } else {
            banner = courseItem.getCourseImg();
        }

        setImageToShape(teacherBanner, profile);
        setImageToShape(imgContainer, banner);

        subjectName.setText(courseItem.getCourseName());

        teacherName.setText(userDB.getUserNameProfileAndSpecByCourseID(courseItem.getCourseId())[0]);


    }
    public boolean isURL(String path) {
        return path != null && (path.startsWith("http://") || path.startsWith("https://"));
    }

    public boolean isResource(String path) {
        return getClass().getResource(path) != null;
    }

    public void setImageToShape(Shape shape, String path) {
        Image image;

        if (isURL(path)) {
            image = new Image(path);
        } else if (isResource(path)) {
            image = new Image(getClass().getResource(path).toExternalForm());
        } else {
            System.out.println("Invalid path: " + path);
            return;
        }

        shape.setFill(new ImagePattern(image));
    }


}
