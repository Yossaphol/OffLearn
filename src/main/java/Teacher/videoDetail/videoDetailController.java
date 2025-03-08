package Teacher.videoDetail;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class videoDetailController implements Initializable {
    @FXML
    private ScrollPane mainscrollpane; // tbd

    @FXML
    private VBox teachernavbarcontainer;

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
    }
    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            VBox navContent = calendarLoader.load();
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
}
