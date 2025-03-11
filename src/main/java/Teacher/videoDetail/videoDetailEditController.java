package Teacher.videoDetail;

import Student.HomeAndNavigation.HomeController;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class videoDetailEditController implements Initializable {

    @FXML
    private ImageView videothumbnail;

    @FXML
    private TextField subjectnamebox;

    @FXML
    private Button changePic;

    @FXML
    private Button deletePic;

    @FXML
    private Button handleSave;

    @FXML
    private Button choosefile;

    @FXML
    private Button deletefile;

    @FXML
    private Button closebtn;

    @FXML
    private TextArea viddescriptionbox;

    @FXML
    private ListView<String> attachmentlist;

    private videoDetailController parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HomeController effect = new HomeController();
        effect.hoverEffect(changePic);
        effect.hoverEffect(deletePic);
        effect.hoverEffect(choosefile);
        effect.hoverEffect(deletefile);
        effect.hoverEffect(handleSave);
        effect.hoverEffect(closebtn);

        attachmentlist.setItems(FXCollections.observableArrayList());
        attachmentlist.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) change -> {
            if (attachmentlist.getSelectionModel().getSelectedItems().isEmpty()) {
                fadeOutDeleteButton();
            } else {
                fadeInDeleteButton();
            }
        });

        deletefile.setVisible(false);
        deletefile.setOpacity(0.0);

        attachmentlist.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

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
    private void handleDeleteFiles(ActionEvent event) {
        var selectedItems = attachmentlist.getSelectionModel().getSelectedItems();
        if (!selectedItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete selected file(s)?");

            var result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                attachmentlist.getItems().removeAll(selectedItems);
            }
        }
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the current thumbnail?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            videothumbnail.setImage(null);
        }
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
    private void fadeInDeleteButton() {
        if (deletefile.isVisible() && deletefile.getOpacity() == 1.0) return;
        deletefile.setVisible(true);
        deletefile.setOpacity(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), deletefile);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private void fadeOutDeleteButton() {
        if (!deletefile.isVisible() || deletefile.getOpacity() == 0.0) return;
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), deletefile);
        fadeOut.setFromValue(deletefile.getOpacity());
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            deletefile.setVisible(false);
        });
        fadeOut.play();
    }
}
