package Student.dashboard;

import Database.QuizDB;
import Database.StudentScoreDB;
import Database.User;
import Database.UserDB;
import Student.task.taskCardController;
import Teacher.quiz.QuizItem;
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

public class dashboardController implements Initializable {
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
        method_home.hoverEffect(scoreTendency);
//        method_home.hoverEffect(risk);
        method_home.hoverEffect(studyTable);
        method_home.hoverEffect(btn_continue);
        method_home.hoverEffect(btn_otherCourse);


        displayScore();
        displayStudyTable();

        us_st.setViewOrder(-1);

        categorybar.setProgress(Double.parseDouble(progressCategory.getText().replace("% completed", "").trim()) / 100);
        roadmap_progress.setProgress(Double.parseDouble(roadmap_value.getText().replace("%", "").trim()) / 100);
        roadmap_progress1.setProgress(Double.parseDouble(roadmap_value1.getText().replace("%", "").trim()) / 100);

        displayProfileBox();
        //Call chart
        courseProgressionChart();
        scoreChart();

        //handle studyTable
       studyTable.setOnMouseClicked(event -> handleStudyTableClick());
    }

    public void route(){
        Navigator nav = new Navigator();
        saved_roadmap.setOnMouseClicked(nav::myRoadmapRoute);
        allMyCourse.setOnMouseClicked(nav::myCourseRoute);
        studyTable.setOnMouseClicked(nav::myCourseRoute);
        btn_continue.setOnMouseClicked(nav::myCourseRoute);
        btn_otherCourse.setOnMouseClicked(nav::courseRoute);
    }

    private void displayProfileBox() {
        loadWithTransition("/fxml/Student/statistics/dashboardProfile.fxml");
    }

    private void loadWithTransition(String fxmlPath) {
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
    private void openPopup(ActionEvent event){
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

//    private void displayTask() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/dashboardTaskElement.fxml"));
//            HBox searchbarContent = loader.load();
//            taskContainer.getChildren().setAll(searchbarContent);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    @FXML
    private void selectSubject(ActionEvent event) {
        Button clickedBtn = (Button) event.getSource();
        if (clickedBtn.getId().equals("selected_subject")) {
            _openSubject(subjectSelector);
            return;
        }
        selected_subject.setText(clickedBtn.getText());
    }

    @FXML
    private void _openSubject(Node popup) {
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



    private void displayScore() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/scoreDetail.fxml"));
            HBox searchbarContent = loader.load();
            scoreDetail.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void _openPopup(Node popup) {
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

    private void closePopup(Node popup) {
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

    private void applyHoverEffect1(HBox categoryBox) {
        categoryBox.setOnMouseEntered(event -> {
            categoryBox.setStyle("-fx-background-color: #F4F4F4;");
        });
        categoryBox.setOnMouseExited(event -> {
            categoryBox.setStyle("-fx-background-color: transparent;");
        });
    }


    private void courseProgressionChart() {
        if (xAxis == null || yAxis == null || courseProgressionChart == null) {
            System.out.println("FXML components not setup properly!");
            return;
        }

        XYChart.Series<String, Number> module1 = new XYChart.Series<>();
        module1.setName(first_subject_name.getText());
        module1.getData().add(new XYChart.Data<>("Overall course progression (ร้อยละ %)", Double.parseDouble(first_val.getText().replace("%", "")) / 100));

        XYChart.Series<String, Number> module2 = new XYChart.Series<>();
        module2.setName(second_subject_name.getText());
        module2.getData().add(new XYChart.Data<>("Overall course progression (ร้อยละ %)", Double.parseDouble(second_val.getText().replace("%", "")) / 100));

        XYChart.Series<String, Number> module3 = new XYChart.Series<>();
        module3.setName(third_subject_name.getText());
        module3.getData().add(new XYChart.Data<>("Overall course progression (ร้อยละ %)", Double.parseDouble(third_val.getText().replace("%", "")) / 100));

        courseProgressionChart.getData().addAll(module1, module2, module3);

        Platform.runLater(() -> {
            courseProgressionChart.lookup(".default-color2.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(first_val)+ ";"+"-fx-background-radius: 0;");
            courseProgressionChart.lookup(".default-color1.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(second_val)+ ";"+"-fx-background-radius: 0;");
            courseProgressionChart.lookup(".default-color0.chart-bar").setStyle("-fx-bar-fill: " +getColorLabel(third_val)+ ";"+"-fx-background-radius: 0;");
        });
    }

    public String getColorLabel(Label label) {
        Color labelColor = (Color) label.getTextFill();
        return String.format("#%02X%02X%02X",
                (int) (labelColor.getRed() * 255),
                (int) (labelColor.getGreen() * 255),
                (int) (labelColor.getBlue() * 255));
    }


    private void scoreChart() {
        ArrayList<Double> Score = new ArrayList<Double>();
        Score.add(13000.4);
        Score.add(27000.83);
        Score.add(9700.0);

        XYChart.Series<String, Number> module1 = new XYChart.Series<>();
        module1.setName(first_subject_name.getText());
        module1.getData().add(new XYChart.Data<>("Score Overview", Score.get(0)));

        XYChart.Series<String, Number> module2 = new XYChart.Series<>();
        module2.setName(second_subject_name.getText());
        module2.getData().add(new XYChart.Data<>("Score Overview", Score.get(1)));

        XYChart.Series<String, Number> module3 = new XYChart.Series<>();
        module3.setName(third_subject_name.getText());
        module3.getData().add(new XYChart.Data<>("Score Overview", Score.get(2)));


        //Set Avg and Min score
        Double total = 0.0;
        for(int i = 0; i< Score.size(); i++){
            total+=Score.get(i);
        }

        Double lowest = 0.0;
        for(int i = 0; i< Score.size(); i++){
            if(lowest == 0.0){
                lowest = Score.get(0);
            }
            if(Score.get(i) < lowest){
                lowest = Score.get(i);
            }
        }
        Double average = total / Score.size();

        avg.setText(Double.toString(average));
        min.setText(Double.toString(lowest));


        scoreChart.getData().addAll(module1, module2, module3);

        Platform.runLater(() -> {
            String color1 = getColorLabel(first_val);
            String color2 = getColorLabel(second_val);
            String color3 = getColorLabel(third_val);
            scoreChart.lookup(".default-color2.chart-bar").setStyle("-fx-bar-fill: "+color1+"; -fx-background-radius: 0;");
            scoreChart.lookup(".default-color1.chart-bar").setStyle("-fx-bar-fill: "+color2+"; -fx-background-radius: 0;");
            scoreChart.lookup(".default-color0.chart-bar").setStyle("-fx-bar-fill: "+color3+"; -fx-background-radius: 0;");

            Node legend1 = scoreChart.lookup(".default-color2.chart-legend-item-symbol");
            Node legend2 = scoreChart.lookup(".default-color1.chart-legend-item-symbol");
            Node legend3 = scoreChart.lookup(".default-color0.chart-legend-item-symbol");

            if (legend1 != null) legend1.setStyle("-fx-background-color: "+color3+";");
            if (legend2 != null) legend2.setStyle("-fx-background-color: "+color2+";");
            if (legend3 != null) legend3.setStyle("-fx-background-color: "+color1 + ";");
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
    private void handleStudyTableClick() {
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
