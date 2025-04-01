package Student.learningPage;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class EPButtonController {

    @FXML
    private Button EPbtn;

    private boolean isActive = false;

    @FXML
    private void initialize() {
        setupHoverEffect();
    }

    public void setText(String text) {
        EPbtn.setText(text);
    }

    public void setActive(boolean active) {
        this.isActive = active;
        if (active) {
            EPbtn.setStyle("-fx-background-color: #8100cc; -fx-text-fill: white; -fx-background-radius: 10;");
        } else {
            EPbtn.setStyle("-fx-background-color: white; -fx-text-fill: #8100CC; -fx-background-radius: 10;");
        }
    }

    private void setupHoverEffect() {
        EPbtn.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            if (!isActive) {
                EPbtn.setStyle("-fx-background-color: #f3e6ff; -fx-text-fill: #8100CC; -fx-background-radius: 10;");
            }
            animateScale(EPbtn, 1.0, 1.05); // scale up slightly
        });

        EPbtn.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            if (!isActive) {
                EPbtn.setStyle("-fx-background-color: white; -fx-text-fill: #8100CC; -fx-background-radius: 10;");
            }
            animateScale(EPbtn, 1.05, 1.0);
        });
    }

    private void animateScale(Button button, double from, double to) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(150), button);
        scale.setFromX(from);
        scale.setFromY(from);
        scale.setToX(to);
        scale.setToY(to);
        scale.play();
    }
}
