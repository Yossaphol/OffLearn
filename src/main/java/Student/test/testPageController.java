package Student.test;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class testPageController implements Initializable {
    public Label testType;
    public Button timeCounter;
    public HBox profile_btn;
    public Button submitTest;
    public Pane smallUserProfile;
    public VBox problemContainer;
    private int remainingTime;
    private Timeline timeline;
    Navigator nav = new Navigator();
    List<Question> questions = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testType.setText("Pre-test");
        setTimeCounter(90); // รับเป็นนาที
        setEffect();


        questions.add(new Question(1,"What is Love?", "Chemical reaction", "Feeling", "Money", "Understanding each other", "ถูก", "Feeling", "Feeling"));
        questions.add(new Question(2,"What is 2 + 2?", "1", "2", "3", "4", "ผิด", "1", "4"));
        questions.add(new Question(1,"What is Java?", "Programming language", "Coffee", "Planet", "Game" , "ถูก", "Planet", "Planet"));

        setProblems(questions);
    }
    public void setProblems(List<Question> questions) {
        problemContainer.getChildren().clear();

        for (int i = 0; i < questions.size(); i++) {
            try {
                Question question = questions.get(i);
                FXMLLoader loader;

                if (question.getType() == 1) {
                    loader = new FXMLLoader(getClass().getResource("/fxml/Student/test/problemCard.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("/fxml/Student/test/problemCard2.fxml"));
                }

                VBox problemCard = loader.load();
                problemCardController q = loader.getController();
                if(question.getType() == 1){
                    q.setProblemCard1(i + 1, question.getQuestionText(), question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionD());
                }
                else{
                    q.setProblemCard2(i + 1, question.getQuestionText(), question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionD());
                }


                problemContainer.getChildren().add(problemCard);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setTimeCounter(int minutes) {
        remainingTime = minutes * 60;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int displayHours = remainingTime / 3600;
            int displayMinutes = (remainingTime % 3600) / 60;
            int displaySeconds = remainingTime % 60;

            if (displayHours > 0) {
                timeCounter.setText(String.format("%02d:%02d:%02d", displayHours, displayMinutes, displaySeconds));
            } else {
                timeCounter.setText(String.format("%02d:%02d", displayMinutes, displaySeconds));
            }

            if (remainingTime <= 0) {
                timeCounter.setText("00:00");
                endTest();
                timeline.stop();
            } else {
                remainingTime--;
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void endTest(ActionEvent e){
        showAlert("Alert", "คุณไม่สามารถดำเนินการใดๆกับข้อสอบได้ หากส่ง\nแต่สามารถกลับมาทำย้อนหลังได้เสมอ :)", Alert.AlertType.INFORMATION);
    }

    public void endTest(){
        showAlert("Alert", "คุณไม่สามารถดำเนินการใดๆกับข้อสอบได้ หากส่ง\nแต่สามารถกลับมาทำย้อนหลังได้เสมอ :)", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                nav.testResult();
            }
        });
    }

    public void setEffect(){
        HomeController ef = new HomeController();
        ef.hoverEffect(smallUserProfile);
        ef.hoverEffect(submitTest);
    }



}
