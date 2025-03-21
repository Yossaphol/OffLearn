package Teacher.courseManagement;

import Database.ChapterDB;
import Database.CourseDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CourseContent implements Initializable {

    @FXML
    private HBox courseContent;

    @FXML
    private TextField chapterName;

    @FXML
    private TextField chapDesc;

    @FXML
    private ImageView saveChap;

    @FXML
    private ImageView delete;

    private ChapterItem chapterItem;
    private ArrayList<ChapterItem> chapList;
    private VBox parentContainer;
    private HBox chapContainer;
    private ChapterDB chapterDB;
    private CourseDB courseDB;
    private TextField courseName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton();
        deleteButton();
    }

    public void recieveChapList(ArrayList<ChapterItem> chapList){ this.chapList = chapList; }

    public void recieveCourseName(TextField courseName) { this.courseName = courseName; }

    public void saveToChapList(){
        String c = chapterName.getText();
        String d = chapDesc.getText();

        courseDB =  new CourseDB();
        chapterDB = new ChapterDB();
        int courseID = courseDB.getCourseID(courseName.getText());
        int chapterID = chapterDB.saveChapter(courseID, c, d);

        if (chapterID != -1) {
            chapterItem = new ChapterItem(chapterID, c, d);
            chapList.add(chapterItem);
            System.out.println(chapterItem.toString());
        }
    }

    public void saveButton(){
        saveChap.setOnMouseClicked(mouseEvent -> {
            saveToChapList();

            saveChap.setVisible(false);
        });
    }

    public void deleteButton(){
        courseDB =  new CourseDB();
        chapterDB = new ChapterDB();
        delete.setOnMouseClicked(mouseEvent -> {
            if (chapterItem != null) {
                chapterDB.deleteChapter(chapterItem.getChapId());
                chapList.remove(chapterItem);

                if (parentContainer != null && chapContainer != null) {
                    parentContainer.getChildren().remove(chapContainer);
                }
            }

        });
    }

    public void setParentContainer(VBox parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setProblemContent(HBox chapContainer) {
        this.chapContainer = chapContainer;
    }

}
