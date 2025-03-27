package Teacher.somethingWithVideo;

import Database.ChapterDB;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import mediaUpload.MediaUpload;

import javax.swing.*;

public class uploadVideoController implements Initializable {
    public HBox navBar;
    public Button back;
    public ImageView coverClip;
    public Button addClipCover;
    public TextField clipName;
    public TextField clipDescription;
    public Button clipDoc;
    public ComboBox clipCat;
    public Button clipFile;
    public Button clipSubmit;
    public VBox mediacontainer;
    public StackPane videocontainer;
    public MediaView mediaView;
    public AnchorPane controlPane;
    public Button btnPlay;
    public Button btnSound;
    public Slider sliderVolume;
    public Label lblTime;
    public Button btnFullscreen;
    public Slider sliderTime;

    private String imagePath = "";
    private String videoPath = "";

    public void initialize(URL url, ResourceBundle resourceBundle) {
        MediaUpload media = new MediaUpload();
        FileChooser imgChooser = new FileChooser();
        FileChooser videoChooser = new FileChooser();
        videoChooser.setTitle("เลือกไฟล์สำหรับอัปโหลด");

        addClipCover.setOnAction(event -> {
            imgChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.webp")
            );
            File selectedFile = imgChooser.showOpenDialog(clipFile.getScene().getWindow());

            if (selectedFile != null) {
                System.out.println("เลือกไฟล์: " + selectedFile.getAbsolutePath());
                File file = new File(selectedFile.getAbsolutePath());
                String mediaPath = file.toURI().toString();
                coverClip.setImage(new Image(mediaPath));

                imagePath = String.valueOf(mediaPath);

                System.out.println("selectedFile: " +selectedFile);
                System.out.println("mediaPath: " + imagePath);
            }
        });

        clipFile.setOnAction(event -> {
            videoChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov")
            );
            File selectedFile = videoChooser.showOpenDialog(clipFile.getScene().getWindow());

            if (selectedFile != null) {
                System.out.println("เลือกไฟล์: " + selectedFile.getAbsolutePath());

                File file = new File(selectedFile.getAbsolutePath());
                String mediaPath = file.toURI().toString();
                videoPath = String.valueOf(mediaPath);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Teacher/somethingWithVideo/videoclip.fxml"));
                    StackPane videoClipNode = loader.load();

                    ClipManager clipManager = loader.getController();
                    clipManager.setVideoUrl(mediaPath);

                    mediacontainer.getChildren().clear();
                    mediacontainer.getChildren().add(videoClipNode);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        clipSubmit.setOnAction(event -> {
//           int chapterID = chapterList.getChapterID(String.valueOf(clipCat.valueProperty().getValue()));
           if (videoPath.equals("")) {
               JOptionPane.showMessageDialog(null,"Please select your video" ,"Video Warning",JOptionPane.WARNING_MESSAGE);
               return;
           }

           if (imagePath.equals("")) {
               JOptionPane.showMessageDialog(null,"Please select your image title" ,"Image Warning",JOptionPane.WARNING_MESSAGE);
               return;
           }

           if (clipName.getText().trim().equals("")) {
               String s = JOptionPane.showInputDialog(null, "Please enter your video name", "Video Warning", JOptionPane.QUESTION_MESSAGE);
               clipName.setText(s);
               return;
           }

           if (clipDescription.getText().trim().equals("")) {
               String s = JOptionPane.showInputDialog(null, "Please enter your video descliption", "Video Warning", JOptionPane.QUESTION_MESSAGE);
               clipDescription.setText(s);
               return;
           }

//           if (chapterID == 0) {
//               JOptionPane.showMessageDialog(null,"Please select your category" ,"Category Warning",JOptionPane.WARNING_MESSAGE);
//               return;
//           }
        });

    }

    private static double convertToSeconds(String duration) {
        String[] parts = duration.split(":");
        double hours = Double.parseDouble(parts[0]);
        double minutes = Double.parseDouble(parts[1]);
        double seconds = Double.parseDouble(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }


}
