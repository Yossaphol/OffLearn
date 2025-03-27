package Teacher.quiz;

import Database.ChapterDB;
import Database.QuestionDB;
import Database.QuizDB;
import Student.HomeAndNavigation.Home;
import Student.HomeAndNavigation.HomeController;
import Teacher.dashboard.dashboardController;
import Teacher.navigator.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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

    @FXML
    private TextField maxScore;

    private ScrollPane wrapper;
    private VBox courseManagement;
    private HBox problemContent;
    private ArrayList<QuestionItem> questionItemsList;
    private ArrayList<ArrayList<QuestionItem>> lqg;
    private VBox courseSpace;

    private QuizDB quizDB;
    private QuestionDB questionDB;
    private ChapterDB chapterDB;
    private QuizItem quizItem;
    private int quizId = -1;
    private QuizBoxContent quizBoxContent;
    private QuizBoxItem quizBoxItem;

    dashboardController d = new dashboardController();
    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addQuestionButton();
        backButton();
        questionItemsList = new ArrayList<QuestionItem>();
        saveAllButton();
        setLevelGroup();

        setEffect();

    }

    private void setEffect(){
        d.hoverEffect(addProblem);
        ef.hoverEffect(saveAll);
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/quiz/questionContent.fxml"));
                problemContent = fxmlLoader.load();

                QuestionContent p = fxmlLoader.getController();

                if (! preSaveQuiz()){
                    return;
                }

                p.setParentContainer(problemSpace);
                p.setProblemContent(problemContent);
                problemSpace.getChildren().add(problemContent);
                passMyController(p);
                passQuizItemList(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void backButton(){
        back.setOnMouseClicked(mouseEvent -> {
            wrapper.setContent(courseManagement);
            wrapper.setVvalue(0);
        });
    }

    public void passMyController(QuestionContent q){q.setQuizController(this);}

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

    public void passMyController(QuizBoxContent quizBoxContent){
        quizBoxContent.setQuizController(this);
    }

    public void setQuizItem(QuizBoxItem quizItem){ this.quizBoxItem = quizItem; }

    public void saveAllButton(){
        saveAll.setOnAction(actionEvent -> {
            showConfirmDialog();
            if (!saveQuiz()){
                return;
            }
            wrapper.setVvalue(0);
        });
    }

    public void updateQuizBox(QuizBoxContent q) {
        chapterDB = new ChapterDB();
        quizDB = new QuizDB();
        int min = Integer.parseInt(minScore.getText());
        int max = Integer.parseInt(maxScore.getText());
        int id = quizDB.saveQuiz(chapterDB.getCurrentChapterId(), this.quizName.getText(), min, max, this.getLevel());
        this.quizItem = new QuizItem(id, this.quizName.getText(), min, max, this.getLevel());

        if (q == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/quiz/QuizBox.fxml"));
                HBox quizBox = fxmlLoader.load();
                QuizBoxContent quizBoxContent = fxmlLoader.getController();

                this.quizBoxItem = new QuizBoxItem(this.quizName.getText(), this.problemSpace.getChildren().size(), max, min);

                quizBoxContent.setDetailGroup(false);
                quizBoxContent.setQuizBoxItem(quizBoxItem);
                quizBoxContent.setDisplay();
                quizBoxContent.recieveLQG(lqg);
                quizBoxContent.recieveQuizItemList(questionItemsList);
                quizBoxContent.setWrapper(wrapper);
                quizBoxContent.setQuizItem(quizItem);
                quizBoxContent.setCourseManagement(courseManagement);
                quizBoxContent.setCourseSpace(courseSpace);
                quizBoxContent.setParentContainer(courseSpace);
                passMyController(quizBoxContent);

                quizBoxContent.setProblemContent(quizBox);
                courseSpace.getChildren().add(quizBox);
                wrapper.setContent(courseManagement);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.quizBoxItem = new QuizBoxItem(this.quizName.getText(), this.problemSpace.getChildren().size(), max, Integer.parseInt(this.minScore.getText()));
            quizBoxItem.setName(this.quizName.getText());
            quizBoxItem.setQuestionCount(problemSpace.getChildren().size());
            quizBoxItem.setMaxScore(max);
            quizBoxItem.setMinScore(min);

            q.setQuizBoxItem(quizBoxItem);
            q.setDisplay();

            q.setDetailGroup(false);
            q.recieveLQG(lqg);
            q.recieveQuizItemList(questionItemsList);
            q.setWrapper(wrapper);
            q.setQuizItem(quizItem);
            q.setCourseManagement(courseManagement);
            q.setCourseSpace(courseSpace);
            q.setParentContainer(courseSpace);
            passMyController(q);

            HBox quizBoxDisplay = q.getQuizBox();
            q.setProblemContent(quizBoxDisplay);
            wrapper.setContent(courseManagement);
        }
    }

    public void showConfirmDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ยืนยันการทำรายการ");
        alert.setHeaderText("คุณแน่ใจหรือไม่?");

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
                updateQuizBox(quizBoxContent);
                saveQuiz();
        } else {
            System.out.println("ผู้ใช้กด No หรือปิดหน้าต่าง");
        }
    }

    public boolean saveQuiz() {
        quizDB = new QuizDB();
        chapterDB = new ChapterDB();

        String name = quizName.getText().trim();
        if (name.isEmpty()) {
            showAlert("", "กรุณากรอกชื่อแบบทดสอบ", Alert.AlertType.ERROR);
            return false;
        }

        int mini;
        try {
            mini = Integer.parseInt(minScore.getText().trim());
            if (mini < 0) {
                showAlert("", "คะแนนขั้นต่ำต้องเป็นค่ามากกว่าหรือเท่ากับ 0", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("", "กรุณากรอกคะแนนขั้นต่ำเป็นตัวเลข", Alert.AlertType.ERROR);
            return false;
        }

        int max;
        try {
            max = Integer.parseInt(maxScore.getText().trim());
            if (max < 0) {
                showAlert("", "คะแนนเต็มต้องเป็นค่ามากกว่าหรือเท่ากับ 0", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("", "กรุณากรอกคะแนนเต็มเป็นตัวเลข", Alert.AlertType.ERROR);
            return false;
        }

        String lev = this.getLevel();
        if (lev == null || lev.isEmpty()) {
            showAlert("", "กรุณาเลือกระดับของแบบทดสอบ", Alert.AlertType.ERROR);
            return false;
        }

        int id = quizDB.saveQuiz(chapterDB.getCurrentChapterId(), name, mini, max, lev);
        if (id == -1) {
            showAlert("", "เกิดข้อผิดพลาดในการบันทึกแบบทดสอบ", Alert.AlertType.ERROR);
        } else {
            quizItem = new QuizItem(id);
        }
        return true;
    }

    public boolean preSaveQuiz() {
        quizDB = new QuizDB();
        chapterDB = new ChapterDB();

        String name = quizName.getText().trim();
        if (name.isEmpty()) {
            showAlert("", "กรุณากรอกชื่อแบบทดสอบ", Alert.AlertType.ERROR);
            return false;
        }

        int mini;
        try {
            mini = Integer.parseInt(minScore.getText().trim());
            if (mini < 0) {
                showAlert("", "คะแนนขั้นต่ำต้องเป็นค่ามากกว่าหรือเท่ากับ 0", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("", "กรุณากรอกคะแนนขั้นต่ำเป็นตัวเลข", Alert.AlertType.ERROR);
            return false;
        }

        int max;
        try {
            max = Integer.parseInt(maxScore.getText().trim());
            if (max < 0) {
                showAlert("", "คะแนนเต็มต้องเป็นค่ามากกว่าหรือเท่ากับ 0", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("", "กรุณากรอกคะแนนเต็มเป็นตัวเลข", Alert.AlertType.ERROR);
            return false;
        }

        String lev = this.getLevel();
        if (lev == null || lev.isEmpty()) {
            showAlert("", "กรุณาเลือกระดับของแบบทดสอบ", Alert.AlertType.ERROR);
            return false;
        }

        int id = quizDB.saveQuiz(chapterDB.getCurrentChapterId(), name, mini, max, lev);
        if (id == -1) {
            showAlert("", "เกิดข้อผิดพลาดในการบันทึกแบบทดสอบ", Alert.AlertType.ERROR);
        } else {
            quizItem = new QuizItem(id);
        }

        return true;
    }

    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void loadQuestion(QuizItem quizItem, QuizController quizCont, QuizBoxContent quizBoxContent){
        this.quizBoxContent = quizBoxContent;
        this.quizId = quizItem.getQuizID();
        quizDB = new QuizDB();

        questionDB = new QuestionDB();
        ArrayList<QuestionItem> questionList = questionDB.getQuestionsByQuizID(quizItem.getQuizID());

        QuizItem qTemp = quizDB.getQuizById(quizItem.getQuizID());

        this.quizName.setText(qTemp.getHeader());
        this.minScore.setText(qTemp.getMinScore() + "");
        this.maxScore.setText(qTemp.getMaxScore() + "");
        switch (qTemp.getLevel()) {
            case "hard" -> {
                hard.setSelected(true);
            }
            case "normal" -> {
                normal.setSelected(true);
            }
            case "easy" -> {
                easy.setSelected(true);
            }
            case null, default -> {
            }
        }

        for (QuestionItem q : questionList){
            System.out.println(q.getQuestionID());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/quiz/questionContent.fxml"));
                problemContent = fxmlLoader.load();

                QuestionContent questionContent = fxmlLoader.getController();
                questionContent.setQuizController(quizCont);
                questionContent.setQuestionID(q.getQuestionID());

                questionContent.setDisplay(q.getQuizName(), q.getPoint(), q.getCorrectChoice(), q.getChoices(), q.getImg());

                questionContent.setParentContainer(problemSpace);
                questionContent.setProblemContent(problemContent);
                problemSpace.getChildren().add(problemContent);
                passQuizItemList(questionContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeQuestion(QuestionContent questionContent) {
        problemSpace.getChildren().remove(questionContent.getProblemContent());
        questionItemsList.remove(questionContent.getQuestionItem());
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

