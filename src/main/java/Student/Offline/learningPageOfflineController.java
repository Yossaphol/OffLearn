package Student.Offline;

import Student.learningPage.DisposableController;
import Student.learningPage.EPButtonController;
import Student.learningPage.VideoPlayerManager;
import Utili.OfflineCourseData;
import Utili.OfflineCourseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class learningPageOfflineController implements Initializable, DisposableController {

    @FXML private VBox mediacontainer;
    @FXML private VBox playlistcontainer;
    @FXML private HBox rootpage;
    @FXML private Label subject_name;
    @FXML private Label catName;
    @FXML private Label teacherName;
    @FXML private Text clipDescription;
    @FXML private Label playlistcount;

    private EPButtonController currentlyActiveEPController = null;
    private VideoPlayerManager videoManager;
    private OfflineCourseData offlineData;
    private int currentChapterID;
    private int courseID;
    private int userID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootpage.getStylesheets().add(getClass().getResource("/css/learningPage.css").toExternalForm());
    }

    public void receiveOfflineData(OfflineCourseData data, int userID) {
        this.courseID = data.getCourseID();
        this.currentChapterID = data.getChapterID();
        this.offlineData = data;
        this.userID = userID;

        subject_name.setText(data.getChapterName());
        clipDescription.setText(data.getChapterDescription());
        catName.setText(data.getCourseCategory());
        teacherName.setText(data.getTeacherName());

        disposePlayer();
        loadOfflineChapter(data);

        List<OfflineCourseData> allChapters = OfflineCourseManager.getAllChaptersForCourse(userID, data.getCourseID());
        loadOfflinePlaylist(allChapters);
    }

    public void loadOfflineCourse(int courseID) {
        List<OfflineCourseData> allChapters = OfflineCourseManager.getAllChaptersForCourse(userID, courseID);
        if (!allChapters.isEmpty()) {
            loadOfflineChapter(allChapters.get(0)); // Load first chapter by default.
            loadOfflinePlaylist(allChapters);
        } else {
            System.out.println("No offline data found for course ID: " + courseID);
        }
    }

    private void loadOfflinePlaylist(List<OfflineCourseData> chapterList) {
        playlistcontainer.getChildren().clear();
        for (int i = 0; i < chapterList.size(); i++) {
            OfflineCourseData ch = chapterList.get(i);
            int chapterId = ch.getChapterID();
            String chapterTitle = ch.getChapterName();
            int epNumber = i + 1;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/EPbutton.fxml"));
                Button EPbtn = loader.load();
                EPButtonController controller = loader.getController();
                controller.setActive(chapterId == currentChapterID);
                if (chapterId == currentChapterID) {
                    currentlyActiveEPController = controller;
                }
                controller.setText("EP" + epNumber + " : " + chapterTitle);
                controller.setActive(chapterId == currentChapterID);

                EPbtn.setOnAction(e2 -> {
                    if (chapterId == currentChapterID) return;
                    if (currentlyActiveEPController != null) {
                        currentlyActiveEPController.setActive(false);
                    }
                    controller.setActive(true);
                    currentlyActiveEPController = controller;
                    disposePlayer();
                    receiveOfflineData(ch, userID);
                });

                playlistcontainer.getChildren().add(EPbtn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playlistcount.setText("(" + chapterList.size() + ")");
    }

    private void loadOfflineChapter(OfflineCourseData chapter) {
        currentChapterID = chapter.getChapterID();
        subject_name.setText(chapter.getChapterName());
        clipDescription.setText(chapter.getChapterDescription());
        catName.setText(chapter.getCourseCategory());
        teacherName.setText(chapter.getTeacherName());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/videoPlayer.fxml"));
            StackPane videoRoot = loader.load();
            videoManager = loader.getController();
            mediacontainer.getChildren().setAll(videoRoot);
            videoManager.setVideoPath(chapter.getVideoPath());
            System.out.println("📁 Video path: " + chapter.getVideoPath());
            System.out.println("📦 File exists? " + new File(chapter.getVideoPath()).exists());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public VideoPlayerManager getVideoManager() {
        return videoManager;
    }

    @Override
    public void disposePlayer() {
        System.out.println("Disposing offline video...");
        if (videoManager != null) {
            videoManager.disposePlayer();
        }
    }
}
