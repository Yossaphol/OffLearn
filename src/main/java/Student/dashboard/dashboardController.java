package Student.dashboard;

import Database.*;
import Student.task.taskCardController;
import Teacher.quiz.QuizItem;
import a_Session.SessionHandler;
import a_Session.SessionManager;
import Student.FontLoader.FontLoader;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import Student.HomeAndNavigation.Navigator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import Student.HomeAndNavigation.HomeController;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class dashboardController implements Initializable, SessionHandler {
    public Rectangle userpfp;
    public Circle trophy;
    public ScrollPane mainScrollPane;
    public HBox rootpage;

    //Nav element
    public HBox dashboard;
    public HBox course;
    public HBox inbox;
    public HBox task;
    public HBox roadmap;
    public HBox home;
    public Button yourCoursebtn;
    public VBox calendarContainer;
    public Button logout;
    public Button cart;
    public HBox logo;
    public HBox profile_btn;
    public Button pfp_btn;
    public Button allCoursebtn;
    public ImageView setting_btn;
    public VBox popup1;
    public VBox popup;
    public HBox bg;
    public HBox category_interested;
    public VBox statistics_data;
    public VBox us_st;
    public VBox category;
    public Circle category_pic;
    public Circle category_pic1;
    public Circle inboxP2;
    public Circle inboxP;
    public ProgressBar roadmap_progress;
    public ProgressBar roadmap_progress1;
    public Label roadmap_value;
    public Label roadmap_value1;
    public Pane cat1;
    public Pane cat2;
    public Pane user_profile;
    public VBox quickInbox;
    public VBox quizBox;
    public VBox roadmapProgression;
    public VBox courseProgression;
    public VBox scoreTendency;
//    public VBox risk;
    public VBox studyTable;
    public Button btn_continue;
    public Button btn_otherCourse;
    public Label progressCategory;
    public Label progressCategory1;
    public ProgressBar categorybar1;
    public ProgressBar categorybar;
    public Label first_val;
    public Label first_subject_name;
    public Label second_val;
    public Label second_subject_name;
    public Label third_val;
    public Label third_subject_name;
    public BarChart scoreChart;
    public CategoryAxis xAxis_score;
    public NumberAxis yAxis_score;
    public Label min;
    public Label avg;
    public VBox leftWrapper;
    public HBox searhbar_container;
    public ScrollPane interest_cat;
    public VBox interestC;
    public Button save_edit;
    public Button edit;
    public Button saved_roadmap;
    public Label allMyCourse;
    public VBox taskContainer;
    public Button selected_subject;
    public HBox scoreDetail;
    public Pane subjectSelector;
    public Label selectedName;
    public Label subjectName1;
    public Label subjectName0;
    public DatePicker date;

    @FXML
    private StackedBarChart<String, Number> courseProgressionChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    StudentScoreDB dataDB = new StudentScoreDB();
    QuizDB dtaDB = new QuizDB();
    String username = SessionManager.getInstance().getUsername();
    UserDB userDB = new UserDB();
    int userID = userDB.getUserId(username);
    ArrayList<QuizItem> undoneQuiz = (ArrayList<QuizItem>) dataDB.getUndoneQuizzes(userID);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FontLoader fontLoader = new FontLoader();
        fontLoader.loadFonts();

        setProgressionChart();

        route();
//        displayTask();
        setUpComing();

        HomeController method_home = new HomeController();

        //Set img container
        method_home.loadAndSetImage(category_pic,"/img/icon/code.png" );

        //Hover effect
        method_home.hoverEffect(cat1);
        method_home.hoverEffect(user_profile);
        method_home.hoverEffect(interestC);
        method_home.hoverEffect(quizBox);
        method_home.hoverEffect(roadmapProgression);
        method_home.hoverEffect(courseProgression);
        method_home.hoverEffect(studyTable);
        method_home.hoverEffect(btn_continue);
        method_home.hoverEffect(btn_otherCourse);


        displayStudyTable();

        us_st.setViewOrder(-1);

        categorybar.setProgress(Double.parseDouble(progressCategory.getText().replace("% completed", "").trim()) / 100);
        roadmap_progress.setProgress(Double.parseDouble(roadmap_value.getText().replace("%", "").trim()) / 100);
        roadmap_progress1.setProgress(Double.parseDouble(roadmap_value1.getText().replace("%", "").trim()) / 100);

        displayProfileBox();
        //Call chart
        scoreChart();

        //handle studyTable
       studyTable.setOnMouseClicked(event -> handleStudyTableClick());
    }

    @Override
    public void handleSession() {
        UserDB userDB = new UserDB();
        this.userID = userDB.getUserId(SessionManager.getInstance().getUsername());
    }

    public void route(){
        Navigator nav = new Navigator();
        saved_roadmap.setOnMouseClicked(nav::myRoadmapRoute);
//        allMyCourse.setOnMouseClicked(nav::myCourseRoute);
        studyTable.setOnMouseClicked(nav::myCourseRoute);
        btn_continue.setOnMouseClicked(nav::myCourseRoute);
        btn_otherCourse.setOnMouseClicked(nav::courseRoute);
    }

    public void displayProfileBox() {
        loadWithTransition("/fxml/Student/statistics/dashboardProfile.fxml");
    }

    public void loadWithTransition(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane newContent = loader.load();

            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), user_profile);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                user_profile.getChildren().setAll(newContent);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), user_profile);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @FXML
    public void openPopup(ActionEvent event){
        Button clickedbtn = (Button) event.getSource();
        switch (clickedbtn.getId()){
            case "btnpopup":
                _openPopup(popup);
                break;
            case "btnpopup1":
                _openPopup(popup1);
                break;
        }
    }
    public void setUpComing() {
        if(undoneQuiz.isEmpty()){
            Label txt = new Label("คุณไม่มีควิซที่ต้องทำในขณะนี้");
            txt.setStyle("-fx-font-size: 20px;");
            taskContainer.getChildren().clear();
            taskContainer.getChildren().add(txt);
            return;
        }
        taskContainer.getChildren().clear();
        for (QuizItem task : undoneQuiz) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/dashboardTaskElement.fxml"));
                HBox quizItem = loader.load();

                dashboardTaskController controller = loader.getController();
                controller.setTaskInformation(task.getHeader(), dtaDB.getCourseNameByQuizID(task.getQuizID()));

                controller.quizLink(task.getQuizID(), dtaDB.getChapterIdByQuizID(task.getQuizID()),quizItem);
                taskContainer.getChildren().add(quizItem);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setProgressionChart() {
        System.out.println("maaaaaa");
        MyProgressDB myProgressDB = new MyProgressDB();
        ArrayList<CourseProgress> courseProgressesList = myProgressDB.getTopThreeCoursesProgress(userID);

        courseProgressionChart.getData().clear();

        courseProgressesList.sort((a, b) -> Double.compare(b.getProgressPercentage(), a.getProgressPercentage()));

        int numberOfCourses = Math.min(courseProgressesList.size(), 3);

        if (numberOfCourses >= 1) {
            this.first_subject_name.setText(courseProgressesList.get(0).getCourseName());
            this.first_val.setText(String.valueOf(Math.round(courseProgressesList.get(0).getProgressPercentage())) + "%"); // แสดงเปอร์เซ็นต์
            this.first_val.setVisible(true);
            this.first_subject_name.setVisible(true);
        } else {
            this.first_val.setVisible(false);
            this.first_subject_name.setVisible(false);
        }

        if (numberOfCourses >= 2) {
            this.second_subject_name.setText(courseProgressesList.get(1).getCourseName());
            this.second_val.setText(String.valueOf(Math.round(courseProgressesList.get(1).getProgressPercentage())) + "%"); // แสดงเปอร์เซ็นต์
            this.second_val.setVisible(true);
            this.second_subject_name.setVisible(true);
        } else {
            this.second_val.setVisible(false);
            this.second_subject_name.setVisible(false);
        }

        if (numberOfCourses >= 3) {
            this.third_subject_name.setText(courseProgressesList.get(2).getCourseName());
            this.third_val.setText(String.valueOf(Math.round(courseProgressesList.get(2).getProgressPercentage())) + "%"); // แสดงเปอร์เซ็นต์
            this.third_val.setVisible(true);
            this.third_subject_name.setVisible(true);
        } else {
            this.third_val.setVisible(false);
            this.third_subject_name.setVisible(false);
        }

        for (int i = 0; i < numberOfCourses; i++) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(courseProgressesList.get(i).getCourseName());

            int roundedPercentage = (int) Math.round(courseProgressesList.get(i).getProgressPercentage());

            series.getData().add(new XYChart.Data<>("Modules", roundedPercentage));
            courseProgressionChart.getData().add(series);
        }

        yAxis.setAutoRanging(true);

        if (numberOfCourses == 0) {
            System.out.println("No courses available to display.");
        }
    }




    public void hoverEffect(Button btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.02);
        scaleUp.setToY(1.02);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.02);
        scaleDown.setFromY(1.02);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
        });
    }

//    public void displayTask() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/dashboardTaskElement.fxml"));
//            HBox searchbarContent = loader.load();
//            taskContainer.getChildren().setAll(searchbarContent);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    @FXML
    public void selectSubject(ActionEvent event) {
        Button clickedBtn = (Button) event.getSource();
        if (clickedBtn.getId().equals("selected_subject")) {
            _openSubject(subjectSelector);
            return;
        }
        selected_subject.setText(clickedBtn.getText());
    }

    @FXML
    public void _openSubject(Node popup) {
        popup.setViewOrder(-1);
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);

        if (!popup.isVisible()) {
            popup.setVisible(true);
            popup.setOpacity(0);
            fade.setFromValue(0);
            fade.setToValue(1);


        } else {
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> popup.setVisible(false));
        }

        fade.play();
    }

    @FXML
    public void _openPopup(Node popup) {
        popup.toFront();
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);

        if (!popup.isVisible()) {
            popup.setVisible(true);
            popup.setOpacity(0);
            fade.setFromValue(0);
            fade.setToValue(1);
        } else {
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> popup.setVisible(false));
        }
        fade.play();
    }

    public void closePopupAuto(){
        bg.setOnMouseClicked(event -> {
            if (popup.isVisible() && !popup.contains(event.getX() - popup.getLayoutX(), event.getY() - popup.getLayoutY())) {
                closePopup(popup);
            }
            if (popup1.isVisible() && !popup1.contains(event.getX() - popup1.getLayoutX(), event.getY() - popup1.getLayoutY())) {
                closePopup(popup1);
            }
        });
    }

    public void closePopup(Node popup) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> popup.setVisible(false));
        fade.play();
    }


    public void applyHoverEffectToInside(VBox root) {
        for (Node node : root.lookupAll(".explore_roadmapOrAddList")) {
            if (node instanceof HBox box) {
                applyHoverEffect1(box);
            }
        }
    }

    public void applyHoverEffect1(HBox categoryBox) {
        categoryBox.setOnMouseEntered(event -> {
            categoryBox.setStyle("-fx-background-color: #F4F4F4;");
        });
        categoryBox.setOnMouseExited(event -> {
            categoryBox.setStyle("-fx-background-color: transparent;");
        });
    }

    public String getColorLabel(Label label) {
        Color labelColor = (Color) label.getTextFill();
        return String.format("#%02X%02X%02X",
                (int) (labelColor.getRed() * 255),
                (int) (labelColor.getGreen() * 255),
                (int) (labelColor.getBlue() * 255));
    }


    public void scoreChart() {
        ScoreDB scoreDB = new ScoreDB();
        ArrayList<CourseScore> topScores = scoreDB.getTopThreeCoursesByScore(userID);

        scoreChart.getData().clear();

        if (topScores.isEmpty()) {
            System.out.println("No scores available to display.");
            return;
        }

        for (CourseScore courseScore : topScores) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(courseScore.getCourseName());

            int roundedScore = (int) Math.round(courseScore.getScore());
            series.getData().add(new XYChart.Data<>("Score Overview", roundedScore));

            scoreChart.getData().add(series);
        }

        double total = topScores.stream().mapToDouble(CourseScore::getScore).sum();
        double average = total / topScores.size();
        int lowest = topScores.stream().mapToInt(CourseScore::getScore).min().orElse(0);

        avg.setText(String.valueOf(Math.round(average)));
        min.setText(Integer.toString(lowest));

        Platform.runLater(() -> {
            String color1 = getColorLabel(first_val);
            String color2 = getColorLabel(second_val);
            String color3 = getColorLabel(third_val);
            scoreChart.lookup(".default-color2.chart-bar").setStyle("-fx-bar-fill: " + color1 + "; -fx-background-radius: 0;");
            scoreChart.lookup(".default-color1.chart-bar").setStyle("-fx-bar-fill: " + color2 + "; -fx-background-radius: 0;");
            scoreChart.lookup(".default-color0.chart-bar").setStyle("-fx-bar-fill: " + color3 + "; -fx-background-radius: 0;");
        });
    }


    public void displayStudyTable(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/studyTable.fxml"));
            VBox searchbarContent = loader.load();
            studyTable.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleStudyTableClick() {
        try {
            // Load the .fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/studyTableEdit.fxml"));
            Pane mdiContent = loader.load();

            // Create a new Stage (window) for the MDI form
            Stage mdiStage = new Stage();
            mdiStage.setTitle("Study Table");

            // Set the content in a Scene
            Scene scene = new Scene(mdiContent);
            mdiStage.setScene(scene);

            // Show the MDI form
            mdiStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any loading errors
        }
    }
}
