package Teacher.courseManagement;

import Database.Category;
import Database.ChapterDB;
import Database.CourseDB;
import Student.FontLoader.FontLoader;
import Teacher.experiment.QuizController;
import Teacher.experiment.QuestionItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import mediaUpload.MediaUpload;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private Button saveAll;

    @FXML
    private TextField courseName;

    @FXML
    private TextField desc;

    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField price;

    @FXML
    private Label addCourseImg;

    @FXML
    private ImageView img;

    @FXML
    private ImageView saveChap;

    private VBox courseList;

    private ScrollPane wrapper;

    private HBox newCourse;
    private CourseDB courseDB;
    private Category category;
    private ChapterDB chapterDB;
    private int userID = 00000001;
    private int courseID;
    private ArrayList<ChapterItem> chapterList = new ArrayList<>();
    private ArrayList<ArrayList<QuestionItem>> lastQuizGroup = new ArrayList<>();
    private MediaUpload m;
    private String imgUrl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();

        addCourseButton();
        addQuizButton();
        saveAllButton();
        addImage();

        setType();
    }

    public void addCourseButton(){
        addCourse.setOnMouseClicked(mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/chapterContent.fxml"));
            try {
                newCourse = fxmlLoader.load();

                preSave();

                ChapterContent c = fxmlLoader.getController();
                passChapList(c);
                passCourseName(c);
                c.setParentContainer(courseSpace);
                c.setProblemContent(newCourse);

                VBox.setVgrow(newCourse, Priority.ALWAYS);
                courseSpace.getChildren().add(newCourse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void addImage() {
        m = new MediaUpload();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        addCourseImg.setOnMouseClicked(mouseEvent -> {
            File selectedFile = fileChooser.showOpenDialog(null);
            imgUrl = m.uploadImg(selectedFile);

            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                img.setImage(image);
            } else {
                System.out.println("No file selected.");
            }
        });
    }

    public void preSave(){
        courseDB = new CourseDB();
        m = new MediaUpload();

        int catID = category.getCatID(type.getValue());
        String name = courseName.getText();
        String des = desc.getText();
        int priceValue = Integer.parseInt(price.getText());

        courseDB.saveCourse(catID, name, userID, des, priceValue, imgUrl);
    }

    public void passWrapper(QuizController quizController){
        quizController.recieveWrapper(wrapper);
    }

    public void passCourseManagement(QuizController quizController){
        quizController.recieveCourseManagement(courseManagement);
    }

    public void passCourseSpace(QuizController q){ q.recieveCourseSpace(courseSpace);}

    public void passLQG(QuizController q){q.recieveLastQuizGroup(lastQuizGroup);}

    public void passChapList(ChapterContent c){ c.recieveChapList(chapterList);}

    public void passCourseName(ChapterContent c){ c.recieveCourseName(courseName);}

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
                passCourseSpace(quizController);
                passWrapper(quizController);
                passLQG(quizController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void saveAllButton(){
        saveAll.setOnAction(actionEvent -> {
            wrapper.setContent(courseList);
        });
    }

    public void setType(){
        category = new Category();
        type.setItems(FXCollections.observableArrayList(category.getCatList()));
    }
}
;