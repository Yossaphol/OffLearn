package Student.learningPage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AttachmentController {

    @FXML
    private HBox attachment;

    @FXML
    private Label filename;

    @FXML
    private Button downloadbtn;

    private String fileUrl;

    public void setFilename(String name) {
        filename.setText(name);
    }

    public void setDownloadURL(String url) {
        downloadbtn.setOnAction(e -> {
            // todooo
            System.out.println("⬇ Downloading from: " + url);
        });
    }
}