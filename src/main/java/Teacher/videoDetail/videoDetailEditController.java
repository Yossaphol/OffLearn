package Teacher.videoDetail;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class videoDetailEditController implements Initializable {

    @FXML
    private ImageView videothumbnail;

    @FXML
    private TextField subjectnamebox;

    @FXML
    private TextArea viddescriptionbox;

    @FXML
    private ListView<String> attachmentlist;

    private boolean saved = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attachmentlist.setItems(FXCollections.observableArrayList());

        attachmentlist.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        attachmentlist.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles()) {
                event.getDragboard().getFiles().forEach(file -> {
                    attachmentlist.getItems().add(file.getAbsolutePath());
                });
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

    }

    // ========== SETTERS  ==========
    public void setSubjectName(String subject) {
        subjectnamebox.setText(subject);
    }

    public void setDescription(String description) {
        viddescriptionbox.setText(description);
    }

    public void setThumbnail(Image image) {
        videothumbnail.setImage(image);
    }

    public void setAttachments(List<String> existingAttachments) {
        attachmentlist.getItems().clear();
        attachmentlist.getItems().addAll(existingAttachments);
    }

    // ========== GETTERS  ==========
    public String getSubjectName() {
        return subjectnamebox.getText();
    }

    public String getDescription() {
        return viddescriptionbox.getText();
    }

    public Image getThumbnail() {
        return videothumbnail.getImage();
    }

    public List<String> getAttachments() {
        return attachmentlist.getItems();
    }

    public boolean isSaved() {
        return saved;
    }

    @FXML
    private void handleChooseFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Attachment(s)");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                attachmentlist.getItems().add(file.getAbsolutePath());
            }
        }
    }

    @FXML
    private void handleChangePic(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Thumbnail");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            videothumbnail.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void handleDeletePic(ActionEvent event) {
        videothumbnail.setImage(null);
    }

    @FXML
    private void handleSave(ActionEvent event) {
        saved = true;
        closeWindow(event);
    }

    @FXML
    private void handleCloseWindow(ActionEvent event) {
        closeWindow(event);
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
