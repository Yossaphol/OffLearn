package Teacher.courseManagement;

import Database.Category;
import Database.ChapterDB;
import Database.CourseDB;
import Student.FontLoader.FontLoader;
import Teacher.experiment.QuizController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class CourseEditController implements Initializable {

    @FXML
    private Label addCourse;

    @FXML
    private VBox courseSpace;

    @FXML
    private Label addQuiz;

    @FXML
    private VBox courseManagement;

    @FXML
    private Button save;

    @FXML
    private TextField courseName;

    @FXML
    private TextField desc;

    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField price;

    private VBox courseList;

    private ScrollPane wrapper;

    private HBox newCourse;
    private CourseDB courseDB;
    private Category category;
    private ChapterDB chapterDB;
    private int userID;
    private int courseID;
    private ArrayList<CourseContent> chapterList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();

        addCourseButton();
        addQuizButton();
        saveButton();

        setType();
    }

    public void addCourseButton(){
        addCourse.setOnMouseClicked(mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/courseContent.fxml"));
            try {
                courseDB = new CourseDB();

                int catID = category.getCatID(type.getValue());
                String name = courseName.getText();
                userID = 908108;
                String des = desc.getText();
                int priceValue = Integer.parseInt(price.getText());

                courseDB.saveCourse(catID, name, userID, des, priceValue);

                newCourse = fxmlLoader.load();
                CourseContent courseContent = fxmlLoader.getController();

                chapterList.add(courseContent);

                VBox.setVgrow(newCourse, Priority.ALWAYS);
                courseSpace.getChildren().add(newCourse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void passWrapper(QuizController quizController){
        quizController.recieveWrapper(wrapper);
    }

    public void passCourseManagement(QuizController quizController){
        quizController.recieveCourseManagement(courseManagement);
    }

    public void recieveWrapper(ScrollPane wrapper){
        this.wrapper = wrapper;
    }

    public void recieveCourseList(VBox courseList) {
        this.courseList = courseList;
    }

    public void addQuizButton() {
        addQuiz.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/experiment/Quiz.fxml"));
                VBox quizContent = fxmlLoader.load();
                wrapper.setContent(quizContent);

                QuizController quizController = fxmlLoader.getController();

                passCourseManagement(quizController);
                passWrapper(quizController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void saveButton(){
        save.setOnAction(actionEvent -> {
            category = new Category();
            courseDB = new CourseDB();

            int catID = category.getCatID(type.getValue());
            String name = courseName.getText();
            userID = 908108;
            String des = desc.getText();
            int priceValue = Integer.parseInt(price.getText());

            courseDB.saveCourse(catID, name, userID, des, priceValue);
            wrapper.setContent(courseList);

            saveChapter();
        });
    }

    public void setType(){
        category = new Category();
        type.setItems(FXCollections.observableArrayList(category.getCatList()));
    }

    public void saveChapter() {
        chapterDB = new ChapterDB();
        courseDB = new CourseDB();

        courseID = courseDB.getCourseID(courseName.getText());

        for (int i = 0; i < chapterList.size(); i++) {
            CourseContent course = chapterList.get(i);

            Map<String, String> data = course.getChapterData();
            String chapterName = data.get("chapterName");
            String chapDesc = data.get("chapDesc");
            chapterDB.saveChapter(courseID, chapterName, chapDesc);

        }
    }
}
;