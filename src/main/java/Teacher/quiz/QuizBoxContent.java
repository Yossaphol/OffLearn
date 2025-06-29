package Teacher.quiz;

import Database.QuizDB;
import Teacher.courseManagement.CourseController;
import Teacher.quizDetail.QuizDetailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
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

    @FXML
    private VBox detailGroup;

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
    private CourseController courseController;
    private int courseId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        deleteButton();
        shadow();
        quizEdit();

        detailGroupButton();

    }

    public void setDisplay(){
        this.quizName.setText(quizBoxItem.getName());
        this.count.setText(quizBoxItem.getQuestionCount() + "");
        this.maxScore.setText(quizBoxItem.getMaxScore() + "");
        this.minScore.setText(quizBoxItem.getMinScore() + "");
    }

    public void setDisplay(QuizBoxItem quizBoxItem){
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

            quizDB.deleteFromDB(quizItem.getQuizID());
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
        dropShadow.setColor(Color.web("#c4c4c4", 0.25));

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

                    System.out.println(this.quizItem.toString());
                    quizController.loadQuestion(this.quizItem, quizController, this);
                    quizController.setQuizItem(quizBoxItem);

                    System.out.println();
                    passCourseManagement(quizController);
                    passCourseSpace(quizController);
                    passCourseManagement(quizController);
                    passCourseSpace(quizController);
                    passWrapper(quizController);

                    wrapper.setVvalue(0);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    public void setDetailGroup(boolean isShow){
        this.detailGroup.setVisible(isShow);
    }

    public void detailGroupButton(){
        detailGroup.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/quiz/quiz_detail.fxml"));
                HBox quizDetail = fxmlLoader.load();

                QuizDetailController quizDetailController = fxmlLoader.getController();
                wrapper.setContent(quizDetail);

                quizDetailController.setDetail(quizBoxItem);
                passQuizBoxItem(quizDetailController);
                passCourseContToQuizDetail(quizDetailController);
                passWrapperToQuizDetail(quizDetailController);
                passCourseManagementToQuizDetail(quizDetailController);
                passCourseIDToQuizDetail(quizDetailController);

                wrapper.setVvalue(0);

            } catch (IOException e) {
                e.printStackTrace();
            }
            mouseEvent.consume();
        });
    }

    public void recieveCourseCont(CourseController courseController){this.courseController = courseController;}

    public void passCourseContToQuizDetail(QuizDetailController quizDetailController){quizDetailController.recieveCourseController(courseController);}

    public void setQuizController(QuizController quizController){this.quizController = quizController;}

    public void passCourseManagement(QuizController quizController){
        quizController.recieveCourseManagement(courseManagement);
    }

    public void passQuizBoxItem(QuizDetailController quizDetailController){ quizDetailController.recieveQuizBoxItem(this.quizBoxItem);}

    public void passWrapper(QuizController quizController){ quizController.recieveWrapper(wrapper); }

    public void passWrapperToQuizDetail(QuizDetailController quizDetailController){ quizDetailController.recieveWrapper(wrapper);}

    public void passCourseSpace(QuizController q){ q.recieveCourseSpace(courseSpace);}

    public void passCourseManagementToQuizDetail(QuizDetailController quizDetailController){quizDetailController.recieveCourseMangement(courseManagement);}

    public void passCourseIDToQuizDetail(QuizDetailController quizDetailController){quizDetailController.recieveCourseId(courseId);}

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

    public void recieveCourseId(int courseId){this.courseId = courseId;}
}
