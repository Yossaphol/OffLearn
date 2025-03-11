package Teacher.videoDetail;

import Student.HomeAndNavigation.HomeController;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class videoDetailController implements Initializable {

    @FXML
    private ScrollPane mainscrollpane;

    @FXML
    private HBox teachernavbarcontainer;

    @FXML
    private Button backbutton;  // tbd

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

    @FXML
    private VBox videoprofilecontainer;

    private static final DecimalFormat DF_2 = new DecimalFormat("#.##");

    // Example data

    private Image currentThumbnail;
    private String currentSubjectName = "Template Course EP";
    private String currentDescription = "Template Description";
    private final List<String> currentAttachments = new ArrayList<>();

    private int viewCountNum    = 1680654;
    private int userCountNum    = 312456;
    private int likeCountNum    = 31232;
    private int dislikeCountNum = 4124;
    private int commentCountNum = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Example labels
        videoduration.setText("99:99");
        dateadded.setText("01/01/2568");
        percentage.setText("64%");

        viewcount.setText(NumFormat(viewCountNum));
        FontScale(viewcount);

        usercount.setText(NumFormat(userCountNum));
        FontScale(usercount);

        countLike.setText(NumFormat(likeCountNum));
        countDislike.setText(NumFormat(dislikeCountNum));
        commentcount.setText(NumFormat(commentCountNum));

        displayNavbar();
        loadWithTransition("/fxml/Teacher/somethingWithVideo/VideoProfile.fxml");
    }

    // --------------------- NAVBAR ---------------------
    private void displayNavbar(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/Teacher/navBar/navBar.fxml")
            );
            HBox navContent = loader.load();
            teachernavbarcontainer.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --------------------- FORMAT UTILS ---------------------
    private String NumFormat(long value) {
        double absValue = Math.abs(value);

        if (absValue < 1_000) {
            return String.valueOf(value);
        } else if (absValue < 1_000_000) {
            double val = value / 1_000.0;
            return DF_2.format(val) + "K";
        } else if (absValue < 1_000_000_000) {
            double val = value / 1_000_000.0;
            return DF_2.format(val) + "M";
        } else {
            double val = value / 1_000_000_000.0;
            return DF_2.format(val) + "B";
        }
    }

    private void FontScale(Label label) {
        String text = label.getText();
        int length = text.length();

        if (length <= 4) {
            label.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");
        } else if (length <= 5) {
            label.setStyle("-fx-font-size: 46; -fx-font-weight: bold;");
        } else if (length <= 6) {
            label.setStyle("-fx-font-size: 44; -fx-font-weight: bold;");
        } else {
            label.setStyle("-fx-font-size: 42; -fx-font-weight: bold;");
        }
    }

    // --------------------- SWAP UI LOGIC ---------------------

    public void showVideoProfile() {
        loadWithTransition("/fxml/Teacher/somethingWithVideo/VideoProfile.fxml");
    }

    public void showVideoEdit() {
        loadWithTransition("/fxml/Teacher/somethingWithVideo/VideoDetailEdit.fxml");
    }

    private void loadWithTransition(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node newContent = loader.load();

            if (fxmlPath.endsWith("VideoProfile.fxml")) {
                videoProfileController profileCtrl = loader.getController();
                profileCtrl.setParentController(this);

                profileCtrl.setThumbnail(currentThumbnail);
                profileCtrl.setSubjectName(currentSubjectName);
                profileCtrl.setDescription(currentDescription);
                profileCtrl.setAttachments(currentAttachments);

            } else if (fxmlPath.endsWith("VideoDetailEdit.fxml")) {
                videoDetailEditController editCtrl = loader.getController();
                editCtrl.setParentController(this);

                editCtrl.setThumbnail(currentThumbnail);
                editCtrl.setSubjectName(currentSubjectName);
                editCtrl.setDescription(currentDescription);
                editCtrl.setAttachments(currentAttachments);
            }

            Node oldContent = null;
            if (!videoprofilecontainer.getChildren().isEmpty()) {
                oldContent = videoprofilecontainer.getChildren().get(0);
            }

            if (oldContent == null) {
                videoprofilecontainer.getChildren().setAll(newContent);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(400), videoprofilecontainer);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
                return;
            }

            double oldHeight = 0.0;
            if (oldContent instanceof Parent) {
                Parent oldParent = (Parent) oldContent;
                oldParent.applyCss();
                oldParent.layout();
                oldHeight = oldParent.getBoundsInParent().getHeight();
            }

            double newHeight = 0.0;
            if (newContent instanceof Parent) {
                Parent newParent = (Parent) newContent;
                newParent.applyCss();
                newParent.layout();
                newHeight = newParent.prefHeight(-1);
            }

            Timeline heightTransition = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(videoprofilecontainer.prefHeightProperty(), oldHeight)),
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(videoprofilecontainer.prefHeightProperty(), newHeight))
            );

            FadeTransition fadeOut = new FadeTransition(Duration.millis(100), videoprofilecontainer);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            ParallelTransition outTransition = new ParallelTransition(heightTransition, fadeOut);

            outTransition.setOnFinished(e -> {
                videoprofilecontainer.getChildren().setAll(newContent);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(350), videoprofilecontainer);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.setOnFinished(evt -> {
                    videoprofilecontainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
                });

                fadeIn.play();
            });

            outTransition.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ====================== Setters ======================
    public void setCurrentThumbnail(Image thumbnail) {
        this.currentThumbnail = thumbnail;
    }
    public void setCurrentSubjectName(String name) {
        this.currentSubjectName = name;
    }
    public void setCurrentDescription(String desc) {
        this.currentDescription = desc;
    }
    public void setCurrentAttachments(List<String> attachments) {
        this.currentAttachments.clear();
        this.currentAttachments.addAll(attachments);
    }

    // getter for later stuff
    public Image getCurrentThumbnail() {
        return currentThumbnail;
    }
    public String getCurrentSubjectName() {
        return currentSubjectName;
    }
    public String getCurrentDescription() {
        return currentDescription;
    }
    public List<String> getCurrentAttachments() {
        return currentAttachments;
    }
}

