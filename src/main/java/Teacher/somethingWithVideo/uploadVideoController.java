package Teacher.somethingWithVideo;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class uploadVideoController implements Initializable {
    public HBox navBar;
    public Button back;
    public ImageView coverClip;
    public Button addClipCover;
    public TextField clipName;
    public TextField clipDescription;
    public Button clipDoc;
    public TextField clipCat;
    public Button clipFile;
    public Button clipSubmit;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();

        String videoPath = getClass().getResource("/videos/Test.mp4").toExternalForm();
        System.out.println("Duration: " + getVideoDuration(videoPath) + " seconds");

    }

    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            navBar.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getVideoDuration(String videoPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:/ffmpeg/ffmpeg", "-i", videoPath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Duration:")) {
                    String duration = line.split("Duration: ")[1].split(",")[0].trim();
                    return convertToSeconds(duration);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static double convertToSeconds(String duration) {
        String[] parts = duration.split(":");
        double hours = Double.parseDouble(parts[0]);
        double minutes = Double.parseDouble(parts[1]);
        double seconds = Double.parseDouble(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }

}
