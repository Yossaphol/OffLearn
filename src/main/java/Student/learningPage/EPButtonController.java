package Student.learningPage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EPButtonController {
    @FXML public Button EPbtn;

    public void setText(String epNumber) {
        EPbtn.setText(epNumber);
    }

    public void setActive(boolean isActive) {
        if (isActive) {
            EPbtn.setStyle("-fx-background-color: #8100cc; -fx-background-radius: 10;");
            EPbtn.setTextFill(javafx.scene.paint.Color.WHITE);
        } else {
            EPbtn.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
            EPbtn.setTextFill(javafx.scene.paint.Color.BLACK);
        }
    }

    public Button getButton() {
        return EPbtn;
    }
}