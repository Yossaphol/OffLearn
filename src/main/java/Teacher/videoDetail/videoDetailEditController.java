package Teacher.videoDetail;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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

    private videoDetailController parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attachmentlist.setItems(FXCollections.observableArrayList());

        // Drag-and-drop logic
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

    public void setParentController(videoDetailController parent) {
        this.parentController = parent;
    }

    public void setThumbnail(Image image) {
        videothumbnail.setImage(image);
    }

    public void setSubjectName(String subject) {
        subjectnamebox.setText(subject);
    }

    public void setDescription(String description) {
        viddescriptionbox.setText(description);
    }

    public void setAttachments(List<String> existingAttachments) {
        attachmentlist.getItems().clear();
        attachmentlist.getItems().addAll(existingAttachments);
    }

    @FXML
    private void handleChooseFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Attachment(s)");

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(subjectnamebox.getScene().getWindow());
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
        File file = fileChooser.showOpenDialog(subjectnamebox.getScene().getWindow());

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
        if (parentController != null) {
            parentController.setCurrentThumbnail(videothumbnail.getImage());
            parentController.setCurrentSubjectName(subjectnamebox.getText());
            parentController.setCurrentDescription(viddescriptionbox.getText());
            parentController.setCurrentAttachments(attachmentlist.getItems());
            parentController.showVideoProfile();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        if (parentController != null) {
            parentController.showVideoProfile();
        }
    }
}
