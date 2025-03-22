package Teacher.experiment;

import Database.ChapterDB;
import Database.QuizDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Optional;

public class QuizController implements Initializable {

    @FXML
    private VBox problemSpace;

    @FXML
    private Label addProblem;

    @FXML
    private Label back;

    @FXML
    private Button saveAll;

    @FXML
    private TextField quizName;

    @FXML
    private TextField minScore;

    @FXML
    private RadioButton hard;

    @FXML
    private RadioButton normal;

    @FXML
    private RadioButton easy;

    private ScrollPane wrapper;
    private VBox courseManagement;
    private HBox problemContent;
    private ArrayList<QuestionItem> questionItemsList;
    private ArrayList<ArrayList<QuestionItem>> lqg;
    private VBox courseSpace;

    private QuizDB quizDB;
    private ChapterDB chapterDB;
    private boolean first;
    private QuizItem quizItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first = true;

        addQuestionButton();
        backButton();
        questionItemsList = new ArrayList<QuestionItem>();
        saveAllButton();
        setLevelGroup();
    }

    public void setLevelGroup(){
        ToggleGroup t = new ToggleGroup();
        hard.setToggleGroup(t);
        normal.setToggleGroup(t);
        easy.setToggleGroup(t);
    }

    public void addQuestionButton(){
        addProblem.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/questionContent.fxml"));
                problemContent = fxmlLoader.load();

                QuestionContent p = fxmlLoader.getController();

                if (first){
                    first = false;
                    this.saveQuiz();
                }

                p.setParentContainer(problemSpace);
                p.setProblemContent(problemContent);
                problemSpace.getChildren().add(problemContent);
                passQuizItemList(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void backButton(){
        back.setOnMouseClicked(mouseEvent -> {
            wrapper.setContent(courseManagement);
        });
    }

    public void recieveWrapper(ScrollPane wrapper){
        this.wrapper = wrapper;
    }

    public void recieveCourseManagement(VBox courseManagement){
        this.courseManagement = courseManagement;
    }

    public void recieveCourseSpace(VBox courseSpace){ this.courseSpace = courseSpace;}

    public void recieveLastQuizGroup(ArrayList<ArrayList<QuestionItem>> lqg){ this.lqg = lqg;}

    public void passQuizItemList(QuestionContent p){
        p.recieveQuizItemList(questionItemsList);
    }

    public void saveAllButton(){
        saveAll.setOnAction(actionEvent -> {
            showConfirmDialog();
        });
    }

    public void setQuizBox(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/QuizBox.fxml"));
            HBox quizBox = fxmlLoader.load();
            quizBoxContent q = fxmlLoader.getController();

            int cnt = 0;
            for (QuestionItem i : questionItemsList){
                cnt += i.getPoint();
            }
            q.setParentContainer(courseSpace);
            q.setProblemContent(quizBox);
            q.setQuizName(quizName.getText());
            q.setCount(questionItemsList.size() + "");
            q.setMinScore(minScore.getText());
            q.setMaxScore(cnt + "");
            q.recieveLQG(lqg);
            q.recieveQuizItemList(questionItemsList);
            q.setQuizItem(quizItem);

            courseSpace.getChildren().add(quizBox);
            wrapper.setContent(courseManagement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToLQG(){
        lqg.add(questionItemsList);
    }


    public void showConfirmDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ยืนยันการทำรายการ");
        alert.setHeaderText("คุณแน่ใจหรือไม่?");

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            setQuizBox();
            addToLQG();
        } else {
            System.out.println("ผู้ใช้กด No หรือปิดหน้าต่าง");
        }
    }

    public void saveQuiz(){
        quizDB = new QuizDB();
        chapterDB = new ChapterDB();

        String name = quizName.getText();
        int mini = Integer.parseInt(minScore.getText());
        String lev = this.getLevel();

        int id = quizDB.saveQuiz(chapterDB.getCurrentChapterId(), name, mini, lev);
        quizItem = new QuizItem(id);
    }

    public String getLevel(){
        if (hard.isSelected()){
            return "hard";
        } else if (normal.isSelected()){
            return "normal";
        } else if (easy.isSelected()){
            return "easy";
        }
        return "";
    }

}

