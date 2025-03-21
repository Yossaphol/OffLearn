package Teacher.experiment;

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
    private ArrayList<QuizItem> quizItemsList;
    private ArrayList<ArrayList<QuizItem>> lqg;
    private VBox courseSpace;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProblemButton();
        backButton();
        quizItemsList = new ArrayList<QuizItem>();
        saveAllButton();
        setLevelGroup();
    }

    public void setLevelGroup(){
        ToggleGroup t = new ToggleGroup();
        hard.setToggleGroup(t);
        normal.setToggleGroup(t);
        easy.setToggleGroup(t);
    }

    public void addProblemButton(){
        addProblem.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/problemContent.fxml"));
                problemContent = fxmlLoader.load();

                ProblemContent p = fxmlLoader.getController();
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

    public void recieveLastQuizGroup(ArrayList<ArrayList<QuizItem>> lqg){ this.lqg = lqg;}

    public void passQuizItemList(ProblemContent p){
        p.recieveQuizItemList(quizItemsList);
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
            for (QuizItem i : quizItemsList){
                cnt += i.getPoint();
            }
            q.setParentContainer(courseSpace);
            q.setProblemContent(quizBox);
            q.setQuizName(quizName.getText());
            q.setCount(quizItemsList.size() + "");
            q.setMinScore(minScore.getText());
            q.setMaxScore(cnt + "");
            q.recieveLQG(lqg);
            q.recieveQuizItemList(quizItemsList);

            courseSpace.getChildren().add(quizBox);
            wrapper.setContent(courseManagement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToLQG(){
        lqg.add(quizItemsList);
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




}

