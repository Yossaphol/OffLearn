package Student.roadmap;

import Student.FontLoader.FontLoader;
import Student.HomeAndNavigation.Navigator;
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
import java.util.ArrayList;

import Database.Category;
import Database.RoadmapDB;

public class roadmapController implements Initializable {
    public HBox MainFrame;
    public FlowPane btn_cotainer;
    public VBox categoryContainer;
    public HBox levelContainer;
    public Button selectedLevel;

    private RoadmapDB roadmapDB = new RoadmapDB();
    private Navigator nav = new Navigator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontLoader fontLoader = new  FontLoader();
        fontLoader.loadFonts();

        applyHoverEffectToInside(btn_cotainer);
        applyHoverEffectToInside(categoryContainer);
        applyHoverEffectToInside(levelContainer);



        closePopupAuto();
        route();
        loadCategories();
    }


    private void loadCategories() {
        Category category = new Category();
        ArrayList<String> categories = category.getCatList();

        categoryContainer.getChildren().clear();

        for (String catName : categories) {
            Label categoryLabel = new Label(catName);
            categoryLabel.getStyleClass().add("forHover1");

            hoverEffect(categoryLabel);

            categoryContainer.getChildren().add(categoryLabel);

            categoryLabel.setOnMouseClicked(mouseEvent -> {
                int catID = category.getCatID(catName);
                System.out.println(catID + " " + catName);

                loadRoadmapsByCategory(String.format("%3d", catID));
            });
        }
    }

    private void loadRoadmapsByCategory(String catID) {
        roadmapDB.saveToDB(catID);
        btn_cotainer.getChildren().clear();

        ArrayList<String[]> roadmaps = roadmapDB.getRoadmapList();

        for (String[] roadmap : roadmaps) {
            String roadmapID = roadmap[0];
            String roadmapName = roadmap[1];
            String roadmapDesc = roadmap[2];

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/unheartroadmap.fxml"));
                Button roadmapButton = loader.load();
                roadmapButton.setText(roadmapName);

                roadmapButton.setOnAction(actionEvent -> {
                    try {
                        FXMLLoader roadmapLoader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/myroadmap.fxml"));
                        Node node = roadmapLoader.load();

                        myRoadmapController controller = roadmapLoader.getController();
                        controller.setRoadmapID(roadmapID);

                        MainFrame.getChildren().clear();
                        MainFrame.getChildren().add(node);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                btn_cotainer.getChildren().add(roadmapButton);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void route() {
        Navigator nav = new Navigator();
    }

    public void applyHoverEffectToInside(FlowPane root) {
        for (Node node : root.lookupAll(".forHover")) {
            if (node instanceof Button box && box != null) {
                hoverEffect(box);
            }
        }
    }

    public void applyHoverEffectToInside(VBox root) {
        for (Node node : root.lookupAll(".forHover1")) {
            if (node instanceof Label box && box != null) {
                hoverEffect(box);
            }
        }
    }

    public void applyHoverEffectToInside(HBox root) {
        for (Node node : root.lookupAll(".forHover")) {
            if (node instanceof Label box && box != null) {
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
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            dropShadow.setColor(Color.web("#c4c4c4", 0.25));
            btn.setEffect(dropShadow);
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
        });
        btn.setOnMouseExited(mouseEvent -> {
            btn.setStyle("-fx-background-color: transparent;");
            scaleDown.play();
        });
    }

    public void selectLevel(ActionEvent event) {
        Button clickedbtn = (Button) event.getSource();

        if (!levelContainer.isVisible()) {
            levelContainer.setVisible(true);
            closePopupAuto();
        } else {
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
