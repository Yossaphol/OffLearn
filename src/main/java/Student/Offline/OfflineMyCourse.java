package Student.Offline;

import Student.HomeAndNavigation.Navigator;
import Utili.OfflineCourseData;
import Utili.OfflineCourseManager;
import a_Session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OfflineMyCourse implements Initializable {
    @FXML private VBox coursecardContainer3;

    private int userID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userID = Integer.parseInt(SessionManager.getInstance().getUserID()); /// CHANGE HERE
        loadOfflineCourses();
    }

    private void loadOfflineCourses() {
        List<OfflineCourseData> courses = OfflineCourseManager.getAllOfflineCourses(userID);
        for (OfflineCourseData course : courses) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/Offline/OfflineCourseCard.fxml"));
                Node card = loader.load();

                offlineCourseCardController ctrl = loader.getController();
                ctrl.setCourse(course);

                ctrl.setOnClickHandler(e -> {
                    MainPageOffline offlineMain = Navigator.getCurrentOfflineController();
                    if (offlineMain != null) {
                        // Switch to learning page
                        offlineMain.displayContent("/fxml/Student/Offline/learningPageOffline.fxml");

                        // Load actual chapters
                        List<OfflineCourseData> chapters = OfflineCourseManager.getAllChaptersForCourse(userID, course.getCourseID());
                        if (!chapters.isEmpty()) {
                            // Now pass the *real* chapter data
                            Object contentCtrl = Navigator.getCurrentContentController();
                            if (contentCtrl instanceof learningPageOfflineController learningCtrl) {
                                learningCtrl.receiveOfflineData(chapters.get(0), userID);
                            }
                        } else {
                            System.err.println("No chapters found for course " + course.getCourseID());
                        }
                    } else {
                        System.err.println("❌ Main controller is not MainPageOffline");
                    }
                });

                coursecardContainer3.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}