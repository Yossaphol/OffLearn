package Teacher.courseManagement;

import Database.ChapterDB;
import Database.CourseDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import mediaUpload.MediaUpload;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ChapterContent implements Initializable {

    public Label date;
    @FXML
    private HBox chapterContent;

    @FXML
    private TextField chapterName;

    @FXML
    private TextField chapDesc;

    @FXML
    private ImageView saveChap;

    @FXML
    private ImageView delete;

    @FXML
    private Button addImg;

    @FXML
    private MediaView video;

    @FXML
    private Button addFile;

    @FXML
    private Label fileName;

    private ChapterItem chapterItem;
    private ArrayList<ChapterItem> chapList;
    private VBox parentContainer;
    private HBox chapContainer;
    private ChapterDB chapterDB;
    private CourseDB courseDB;
    private TextField courseName;
    private String chapImgUrl;
    private String chapMatUrl;
    private MediaUpload m;
    private int chapterID = 0;
    private double playtime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton();
        deleteButton();
        addVideo();
        addMaterials();

        shadow();
        setCurrecntDate();
    }

    private void setCurrecntDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        String currentDate = dateFormat.format(new Date());
        date.setText(currentDate);
    }

    public void recieveChapList(ArrayList<ChapterItem> chapList){ this.chapList = chapList; }

    public void recieveCourseName(TextField courseName) { this.courseName = courseName; }

    public void saveToChapList(){
        String c = chapterName.getText();
        String d = chapDesc.getText();

        courseDB =  new CourseDB();
        chapterDB = new ChapterDB();
        int courseID = courseDB.getCourseID(courseName.getText());

        if (!chapterDB.isChapterExists(chapterID)){
            this.chapterID = chapterDB.saveChapter(courseID, c, d, chapImgUrl, chapMatUrl, playtime);
            System.out.println("save chapter complete");
        } else {
            if (chapterItem.getMaterial() != null){
                chapMatUrl = chapterItem.getMaterial();
            }
            if (chapterItem.getImgUrl() != null){
                chapImgUrl = chapterItem.getImgUrl();
            }
            chapterDB.updateChapter(this.chapterID, c, d, chapImgUrl, chapMatUrl, playtime);
            System.out.println("update chapter complete");
        }

        if (chapterID != -1) {
            chapterItem = new ChapterItem(chapterID, c, d, chapImgUrl, chapMatUrl);
            chapList.add(chapterItem);
            System.out.println(chapterItem.toString());
        }
    }

    public void addVideo() {
        m = new MediaUpload();
        FileChooser fileChooser = new FileChooser();

        addImg.setOnMouseClicked(mouseEvent -> {
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                if (this.isImageFile(selectedFile)){
                    showAlert("", "กรุณาเลือกวิดิโอ", Alert.AlertType.ERROR);
                    return;
                }

                chapImgUrl = m.uploadVideo(selectedFile);

                String videoPath = selectedFile.toURI().toString();
                Media media = new Media(videoPath);
                MediaPlayer mediaPlayer = new MediaPlayer(media);

                if (this.video.getMediaPlayer() != null) {
                    this.video.getMediaPlayer().dispose();
                }

                this.video.setMediaPlayer(mediaPlayer);

                mediaPlayer.setOnReady(() -> {
                    this.playtime = mediaPlayer.getTotalDuration().toSeconds();

                });
            } else {
                System.out.println("No file selected.");
            }
        });
    }

    public boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();

        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                fileName.endsWith(".png") || fileName.endsWith(".gif") ||
                fileName.endsWith(".bmp") || fileName.endsWith(".tiff");
    }


    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addMaterials(){
        m = new MediaUpload();
        FileChooser fileChooser = new FileChooser();

        addFile.setOnMouseClicked(mouseEvent -> {
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null){
                chapMatUrl = m.uploadMaterial(selectedFile);
                this.fileName.setText("แนบไฟล์สำเร็จ");
                this.addFile.setText("แนบไฟล์ใหม่");
            }
        });
    }

    public void saveButton(){
        saveChap.setOnMouseClicked(mouseEvent -> {
            if (!isReadyToSave()){
                return;
            }
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

    private boolean isReadyToSave() {
        String c = chapterName.getText().trim();
        String d = chapDesc.getText().trim();

        if (c.isEmpty()) {
            showAlert("", "กรุณาใส่ชื่อบทเรียน", Alert.AlertType.ERROR);
            return false;
        }
        if (d.isEmpty()) {
            showAlert("", "กรุณาใส่คำอธิบายบทเรียน", Alert.AlertType.ERROR);
            return false;
        }
        if ((chapImgUrl == null || chapImgUrl.isEmpty()) && (chapterItem.getImgUrl()) == null) {
            showAlert("", "กรุณาเพิ่มวิดีโอก่อนบันทึก", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    public void shadow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(2.5);
        dropShadow.setOffsetY(2.5);
        dropShadow.setColor(Color.web("#c4c4c4", 0.25));

        chapterContent.setEffect(dropShadow);
    }

    public void setDisplay(ChapterItem chapterItem) {
        this.chapterItem = chapterItem;
        this.chapterName.setText(chapterItem.getChapterName());
        this.chapDesc.setText(chapterItem.getDesc());

        if (chapterItem.getImgUrl() != null) {
            Media media = new Media(chapterItem.getImgUrl());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            this.video.setMediaPlayer(mediaPlayer);
        }

        if (chapterItem.getMaterial() != null){
            this.fileName.setText("แนบไฟล์สำเร็จ");
            this.addFile.setText("แนบไฟล์ใหม่");
        }

        this.chapterID = chapterItem.getChapId();
    }

}
