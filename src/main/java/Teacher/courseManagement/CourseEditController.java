package Teacher.courseManagement;

import Database.Category;
import Database.ChapterDB;
import Database.CourseDB;
import Student.FontLoader.FontLoader;
import Teacher.quiz.*;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private CourseController courseController;
    private CourseItem courseItem;

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

                if (! saveCourse()){
                    return;
                }

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

    public boolean saveCourse() {
        courseDB = new CourseDB();
        m = new MediaUpload();

        String typeValue = type.getValue();
        String name = courseName.getText().trim();
        String des = desc.getText().trim();
        String priceText = price.getText().trim();

        if (typeValue == null || typeValue.isEmpty()) {
            showAlert("", "กรุณาเลือกประเภทของคอร์สเรียน", AlertType.ERROR);
            return false;
        }
        if (name.isEmpty()) {
            showAlert("", "กรุณากรอกชื่อคอร์สเรียน", AlertType.ERROR);
            return false;
        }
        if (des.isEmpty()) {
            showAlert("", "กรุณากรอกคำอธิบายคอร์สเรียน", AlertType.ERROR);
            return false;
        }
        if (priceText.isEmpty()) {
            showAlert("", "กรุณากรอกราคาคอร์สเรียน", AlertType.ERROR);
            return false;
        }
        if ((imgUrl == null || imgUrl.isEmpty()) && (this.img.getImage().getUrl().contains("bg.jpg"))) {
            System.out.println(this.img.getImage().getUrl());
            showAlert("", "กรุณาเพิ่มรูปภาพสำหรับคอร์สเรียน", AlertType.ERROR);
            return false;
        }


        int catID = category.getCatID(typeValue);
        int priceValue;

        try {
            priceValue = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            showAlert("", "ราคาต้องเป็นตัวเลขเท่านั้น!!!", AlertType.ERROR);
            return false;
        }

        courseDB.saveCourse(catID, name, userID, des, priceValue, imgUrl);
        return true;
    }

    public void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public void recieveMethod(CourseController courseController){this.courseController = courseController;}

    public void addQuizButton() {
        addQuiz.setOnMouseClicked(mouseEvent -> {
            if (courseSpace.getChildren().isEmpty()){
                showAlert("", "กรุณาเพิ่มบทเรียนก่อนสร้างแบบทดสอบ", AlertType.ERROR);
                return;
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/quiz/Quiz.fxml"));
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
            if (! saveCourse()){
                return;
            }
            wrapper.setContent(courseList);
            courseController.showCourseOnListview();
        });
    }

    public void setType(){
        category = new Category();
        type.setItems(FXCollections.observableArrayList(category.getCatList()));
    }

    public void loadMyCourse(int CourseID){
        courseDB = new CourseDB();
        courseItem = courseDB.getCourseByID(CourseID);

        this.courseName.setText(courseItem.getCourseName());
        this.desc.setText(courseItem.getCourseDesc());
        this.type.setValue(courseItem.getCourseCat());
        this.price.setText(courseItem.getCoursePrice() + "");
        this.img.setImage(new Image(courseItem.getCourseImg()));

        for (ChapterItem cItem : courseItem.getChapterList()){

//            My chapter
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/courseManagement/chapterContent.fxml"));
            try {
                newCourse = fxmlLoader.load();

                ChapterContent cContent = fxmlLoader.getController();
                passChapList(cContent);
                passCourseName(cContent);
                cContent.setParentContainer(courseSpace);
                cContent.setProblemContent(newCourse);
                cContent.setDisplay(cItem);

                VBox.setVgrow(newCourse, Priority.ALWAYS);
                courseSpace.getChildren().add(newCourse);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            My quiz
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Teacher/quiz/QuizBox.fxml"));
                HBox quizBox = loader.load();
                QuizBoxContent quizBoxContent = loader.getController();

                QuizItem temp = cItem.getQuizItem();
                if (temp == null){
                    continue;
                }
                String name = temp.getHeader();
                int max = temp.getMaxScore();
                int min = temp.getMinScore();

                QuizBoxItem quizBoxItem = new QuizBoxItem(name, 0, max, min);

                quizBoxContent.setQuizBoxItem(quizBoxItem);
                quizBoxContent.setDisplay();
//                quizBoxContent.recieveQuizItemList(questionItemsList);
                quizBoxContent.setWrapper(wrapper);
                quizBoxContent.setQuizItem(temp);
                quizBoxContent.setCourseManagement(courseManagement);
                quizBoxContent.setCourseSpace(courseSpace);
                quizBoxContent.setParentContainer(courseSpace);
//                passMyController(quizBoxContent);

                quizBoxContent.setProblemContent(quizBox);
                courseSpace.getChildren().add(quizBox);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
;