package Student.courseManage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class courseEnrollController implements Initializable {
    @FXML
    public VBox leftWrapper;
    public HBox rootPage;
    public HBox searchbar_container;
    public Text adviceText;

    public void initialize(URL location, ResourceBundle resources) {
        adviceText.setText("เนื้อหาที่ปูพื้นฐานให้ตั้งแต่เริ่มต้น\nตั้งแต่กระบวนการคิด จนถึงการปฏิบัติ");
    }

}
