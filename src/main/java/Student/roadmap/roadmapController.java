package Student.roadmap;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class roadmapController implements Initializable {
    public HBox MainFrame;
    public VBox LeftWrapper;
    public HBox searhbar_container;
    public Button yourRoadmap;
    public Label category01;
    public Label category02;
    public Label category03;
    public Label category04;
    public Label category05;
    public Label category06;
    public Label category07;
    public Label category08;
    public Button sub_category_btn01;
    public Button sub_category_btn02;
    public Button sub_category_btn03;
    public Button sub_category_btn04;
    public Button sub_category_btn05;
    public Button sub_category_btn06;
    public Button sub_category_btn07;
    public Button sub_category_btn08;
    public Button sub_category_btn09;
    public Button sub_category_btn10;
    public FlowPane btn_cotainer;
    public VBox categoryContainer;
    public Button myRoadmap;
    public HBox levelContainer;
    public Button selectedLevel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();

        applyHoverEffectToInside(btn_cotainer);
        applyHoverEffectToInside(categoryContainer);
        applyHoverEffectToInside(levelContainer);
        hoverEffect(myRoadmap);
        closePopupAuto();

        route();

    }

    public void route(){
        Navigator nav = new Navigator();
        myRoadmap.setOnMouseClicked(nav::myRoadmapRoute);
    }

    public void applyHoverEffectToInside(FlowPane root) {
        for (Node node : root.lookupAll(".forHover")) {
            if (node instanceof Button box) {
                hoverEffect(box);
            }
        }
    }

    public void applyHoverEffectToInside(VBox root) {
        for (Node node : root.lookupAll(".forHover1")) {
            if (node instanceof Label box) {
                hoverEffect(box);
            }
        }
    }

    public void applyHoverEffectToInside(HBox root) {
        for (Node node : root.lookupAll(".forHover")) {
            if (node instanceof Label box) {
                hoverEffect(box);
            }
        }
    }

    public void hoverEffect(Button btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.05);
        scaleDown.setFromY(1.05);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
            dropShadow.setColor(Color.web("#8100CC", 0.25));
            btn.setEffect(dropShadow);
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            dropShadow.setColor(Color.web("#c4c4c4", 0.25));
            btn.setEffect(dropShadow);
            scaleDown.play();
        });
    }

    public void hoverEffect(Label btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.05);
        scaleDown.setFromY(1.05);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        btn.setOnMouseEntered(mouseEvent -> {
            btn.setStyle("-fx-background-color: #EFEFEF;");
            scaleUp.play();
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            btn.setStyle("-fx-background-color: transparent;");
            scaleDown.play();
            scaleDown.play();
        });
    }




    public void selectLevel(ActionEvent event) {
        Button clickedbtn = (Button) event.getSource();

        if (!levelContainer.isVisible()) {
            levelContainer.setVisible(true);
            closePopupAuto();
        }else{
            levelContainer.setVisible(false);
        }

        selectedLevel.setText(clickedbtn.getText());
    }

    public void closePopupAuto() {
        MainFrame.setOnMouseClicked(event -> {
            if (levelContainer.isVisible() &&
                    !levelContainer.contains(event.getX() - levelContainer.getLayoutX(), event.getY() - levelContainer.getLayoutY())) {
                levelContainer.setVisible(false);
            }
        });
    }


}
