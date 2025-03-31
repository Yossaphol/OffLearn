package Student.learningPage;

import Utili.OfflineCacheManager;
import Utili.OfflineCourseData;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class learningPageOfflineController implements Initializable {

    // UI elements you want to display offline
    @FXML private VBox mediacontainer;
    @FXML private VBox playlistcontainer;      // Listing chapters
    @FXML private HBox rootpage;               // The root container

    @FXML private Label subject_name;          // Course or chapter name
    @FXML private Label catName;               // Course category
    @FXML private Label teacherName;           // Teacher name
    @FXML private Text clipDescription;        // Chapter description
    @FXML private Label playlistcount;

    private EPButtonController currentlyActiveEPController = null;
    private VideoPlayerManager videoManager;
    private OfflineCourseData offlineData;
    private int currentChapterID;
    private int courseID;

    private OfflineCacheManager cacheManager;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a cache manager instance and load offline data from a JSON file.
        OfflineCacheManager cacheManager = new OfflineCacheManager();
        // Adjust the file path to where your JSON file is stored.
        cacheManager.loadOfflineDataFromFile("offlineData.json");

        // For demonstration, let's assume we want to load chapters for a specific course.
        // Here, courseID is assumed to be 1. You can adjust this logic.
        courseID = 1;
        List<OfflineCourseData> courseChapters = cacheManager.getAllOfflineChapters()
                .stream()
                .filter(ch -> ch.getCourseID() == courseID)
                .sorted(Comparator.comparingInt(OfflineCourseData::getChapterID))
                .toList();

        if (!courseChapters.isEmpty()) {
            // Load the first chapter by default.
            loadOfflineChapter(courseChapters.get(0));
            // Update the playlist to reflect loaded chapters.
            loadOfflinePlaylist();
        } else {
            System.out.println("No offline data available for course ID: " + courseID);
        }
    }

    public void receiveOfflineData(OfflineCourseData data) {
        // Update current course and chapter IDs and refresh UI components.
        this.courseID = data.getCourseID();
        this.currentChapterID = data.getChapterID();
        this.offlineData = data;

        // Update UI with chapter data.
        subject_name.setText(data.getChapterName());
        clipDescription.setText(data.getChapterDescription());
        catName.setText(data.getCourseCategory());
        teacherName.setText(data.getTeacherName());

        // Load the video if the videoManager has been initialized.
        if (videoManager != null) {
            videoManager.setVideoPath(data.getVideoPath());
        }

        // Refresh playlist to update active state.
        loadOfflinePlaylist();
    }

    public void loadOfflineCourse(int courseID) {
        // Retrieve and sort chapters for the given course.
        List<OfflineCourseData> allChapters = new OfflineCacheManager().getAllOfflineChapters()
                .stream()
                .filter(ch -> ch.getCourseID() == courseID)
                .sorted(Comparator.comparingInt(OfflineCourseData::getChapterID))
                .toList();

        if (!allChapters.isEmpty()) {
            loadOfflineChapter(allChapters.get(0)); // Load first chapter by default.
            loadOfflinePlaylist();
        } else {
            System.out.println("No offline data found for course ID: " + courseID);
        }
    }

    private void loadOfflinePlaylist() {
        // Assume that OfflineCacheManager.loadAllOfflineChapters() returns the current list.
        OfflineCacheManager cacheManager = new OfflineCacheManager();
        List<OfflineCourseData> offlineChapters = cacheManager.getAllOfflineChapters();
        List<OfflineCourseData> filtered = offlineChapters.stream()
                .filter(c -> c.getCourseID() == courseID)
                .toList();

        playlistcontainer.getChildren().clear();
        for (int i = 0; i < filtered.size(); i++) {
            OfflineCourseData ch = filtered.get(i);
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

                    // Load the selected chapter.
                    receiveOfflineData(ch);
                });

                playlistcontainer.getChildren().add(EPbtn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playlistcount.setText("(" + filtered.size() + ")");
    }

    private void loadOfflineChapter(OfflineCourseData chapter) {
        // Update the current chapter ID and refresh UI details.
        currentChapterID = chapter.getChapterID();
        subject_name.setText(chapter.getChapterName());
        clipDescription.setText(chapter.getChapterDescription());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/learningPage/videoPlayer.fxml"));
            StackPane videoRoot = loader.load();
            videoManager = loader.getController();
            mediacontainer.getChildren().setAll(videoRoot);
            videoManager.setVideoPath(chapter.getVideoPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
