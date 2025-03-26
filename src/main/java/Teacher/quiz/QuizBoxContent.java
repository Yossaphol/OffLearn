package Teacher.quiz;

import Database.QuizDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuizBoxContent implements Initializable {

    @FXML
    private Label count;

    @FXML
    private Label minScore;

    @FXML
    private Label maxScore;

    @FXML
    private Label quizName;

    @FXML
    private VBox delete;

    @FXML
    private HBox quizBox;

    private ScrollPane wrapper;
    private VBox courseManagement;
    private VBox courseSpace;

    private VBox parentContainer;
    private ArrayList<ArrayList<QuestionItem>> lqg;
    private ArrayList<QuestionItem> questionItemsList;
    private QuizItem quizItem;
    private QuizDB quizDB;
    private QuizController quizController;
    private QuizBoxItem quizBoxItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        deleteButton();
        shadow();
        quizEdit();
    }

    public void setDisplay(){
        this.quizName.setText(quizBoxItem.getName());
        this.count.setText(quizBoxItem.getQuestionCount() + "");
        this.maxScore.setText(quizBoxItem.getMaxScore() + "");
        this.minScore.setText(quizBoxItem.getMinScore() + "");
    }

    public void setQuizBoxItem(QuizBoxItem quizBoxItem){
        this.quizBoxItem = quizBoxItem;
    }

    public HBox getQuizBox(){ return this.quizBox; }

    public QuizItem getQuizItem(){ return this.quizItem;}

    public void setMinScore(String minScore){
        this.minScore.setText(minScore);
    }

    public void setQuizItem(QuizItem q) { this.quizItem = q;}

    public void deleteButton(){
        quizDB = new QuizDB();
        delete.setOnMouseClicked(mouseEvent -> {
            System.out.println("Delete button clicked.");

            if (quizItem == null) {
                System.out.println("quizItem is null, cannot delete.");
                return;
            }

            quizDB.deleteQuiz(quizItem.getQuizID());
            System.out.println("Quiz deleted from database.");

            if (parentContainer != null && quizBox != null) {
                System.out.println("Removing from UI...");
                parentContainer.getChildren().remove(quizBox);
            } else {
                System.out.println("Cannot remove: parentContainer or quizBox is null.");
            }

            mouseEvent.consume();
        });
    }


    public void shadow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(2.5);
        dropShadow.setOffsetY(2.5);
        dropShadow.setColor(Color.GRAY);

        quizBox.setEffect(dropShadow);
    }

    public void quizEdit(){
        quizBox.setOnMouseClicked(mouseEvent -> {
            System.out.println(quizBoxItem.toString());
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/quiz/Quiz.fxml"));
                    VBox quizContent = fxmlLoader.load();
                    wrapper.setContent(quizContent);

                    QuizController quizController = fxmlLoader.getController();

                    quizController.loadQuestion(this.quizItem, quizController, this);
                    quizController.setQuizItem(quizBoxItem);

                    System.out.println();
                    passCourseManagement(quizController);
                    passCourseSpace(quizController);
                    passCourseManagement(quizController);
                    passCourseSpace(quizController);
                    passWrapper(quizController);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    public void setQuizController(QuizController quizController){this.quizController = quizController;}

    public void passCourseManagement(QuizController quizController){
        quizController.recieveCourseManagement(courseManagement);
    }

    public void passWrapper(QuizController quizController){ quizController.recieveWrapper(wrapper); }

    public void passCourseSpace(QuizController q){ q.recieveCourseSpace(courseSpace);}

    public void setCourseManagement(VBox courseManagement){ this.courseManagement = courseManagement; }

    public void setCourseSpace(VBox courseSpace){ this.courseSpace = courseSpace; }

    public void setParentContainer(VBox parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setProblemContent(HBox quizBox) {
        this.quizBox = quizBox;
    }

    public void recieveLQG(ArrayList<ArrayList<QuestionItem>> lqg){ this.lqg = lqg;}

    public void setWrapper(ScrollPane wrapper){ this.wrapper = wrapper;}

    public void recieveQuizItemList(ArrayList<QuestionItem> questionItemsList){ this.questionItemsList = questionItemsList;}
}
