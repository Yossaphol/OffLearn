package Teacher.videoDetail;

import Student.HomeAndNavigation.HomeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class videoProfileController implements Initializable {

    @FXML
    private ImageView videothumbnail;

    @FXML
    private Label subjectname;

    @FXML
    private Label viddescription;

    @FXML
    private VBox attachmentcontainer;

    @FXML
    private Button toedit;

    private videoDetailController parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HomeController effect = new HomeController();
        effect.hoverEffect(toedit);
    }

    public void setParentController(videoDetailController parent) {
        this.parentController = parent;
    }

    public void setThumbnail(Image image) {
        videothumbnail.setImage(image);
    }

    public void setSubjectName(String name) {
        subjectname.setText(name);
    }

    public void setDescription(String desc) {
        viddescription.setText(desc);
    }

    public void setAttachments(List<String> attachments) {
        attachmentcontainer.getChildren().clear();

        for (String filePath : attachments) {
            Hyperlink link = new Hyperlink(filePath);
            attachmentcontainer.getChildren().add(link);
        }
    }

    @FXML
    private void toedit(ActionEvent event) {
        if (parentController != null) {
            parentController.showVideoEdit();
        }
    }
}
