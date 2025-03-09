package Teacher.videoDetail;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class videoDetailController implements Initializable {
    @FXML
    private ScrollPane mainscrollpane; // tbd

    @FXML
    private HBox teachernavbarcontainer;

    @FXML
    private Button backbutton;  // tbd

    @FXML
    private ImageView videothumbnail;

    @FXML
    private Label subjectname;

    @FXML
    private Label viddescription;

    @FXML
    private VBox attachmentcontainer;  // tbd

    @FXML
    private Button toedit;  // tbd

    @FXML
    private Label videoduration;

    @FXML
    private Label dateadded;

    @FXML
    private Label viewcount;

    @FXML
    private Label percentage;

    @FXML
    private Label usercount;

    @FXML
    private Label countLike;

    @FXML
    private Label countDislike;

    @FXML
    private Label commentcount;

    @FXML
    private VBox commentcontainer;  // tbd

    @FXML
    private VBox nocommentcontainer;  // tbd

    private int viewCountNum    = 1680654;
    private int userCountNum    = 312456;
    private int likeCountNum = 31232;
    private int dislikeCountNum = 4124;
    private int commentCountNum = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subjectname.setText("Template Course EP");
        viddescription.setText("Template Description ");
        videoduration.setText("99:99");
        dateadded.setText("01/01/2568");
        percentage.setText("64%");
        viewcount.setText(CommaFormat(viewCountNum));
        FontScale(viewcount);
        usercount.setText(CommaFormat(userCountNum));
        FontScale(usercount);
        countLike.setText(CommaFormat(likeCountNum));
        countDislike.setText(CommaFormat(dislikeCountNum));
        commentcount.setText(CommaFormat(commentCountNum));
        displayNavbar();
    }
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            teachernavbarcontainer.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String CommaFormat(int number) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        return nf.format(number);
    }
    private void FontScale(Label label) {
        String text = label.getText();
        int length = text.length();

        //tweak as desire
        if (length <= 4) {
            label.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");
        } else if (length <= 6) {
            label.setStyle("-fx-font-size: 44; -fx-font-weight: bold;");
        } else if (length <= 8) {
            label.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
        } else {
            label.setStyle("-fx-font-size: 28; -fx-font-weight: bold;");
        }
    }
    @FXML
    private void toedit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Teacher/somethingWithVideo/videoDetailEdit.fxml"));
            Parent root = loader.load();

            videoDetailEditController editController = loader.getController();

            editController.setSubjectName(subjectname.getText());
            editController.setDescription(viddescription.getText());

            Image currentThumb = videothumbnail.getImage();
            if (currentThumb != null) {
                editController.setThumbnail(currentThumb);
            }

            List<String> currentAttachments = new ArrayList<>();
            attachmentcontainer.getChildren().forEach(node -> {
                if (node instanceof Hyperlink link) {
                    currentAttachments.add(link.getText());
                }
            });
            editController.setAttachments(currentAttachments);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Edit Video Details");
            popupStage.setScene(new Scene(root));

            popupStage.showAndWait();
            attachmentcontainer.getChildren().clear();

            if (editController.isSaved()) {
                subjectname.setText(editController.getSubjectName());
                viddescription.setText(editController.getDescription());
                videothumbnail.setImage(editController.getThumbnail());
                for (String filePath : editController.getAttachments()) {
                    Hyperlink link = new Hyperlink(filePath);
                    // (Optional) link.setOnAction(...) to open or handle file
                    attachmentcontainer.getChildren().add(link);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
