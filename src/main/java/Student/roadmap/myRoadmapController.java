package Student.roadmap;

import Database.OrderRoadmapDB;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class myRoadmapController implements Initializable {

    private String roadmapID;

    @FXML private GridPane roadmap_node_container;
    @FXML private HBox optional_course_container;
    @FXML private HBox hearted_roadmap_container;
    @FXML private Pane optional_course_box;
    @FXML private HBox pre_test;
    @FXML private Pane selected_roadmap_box;
    @FXML private Button pretest_button_text;
    @FXML private VBox related_roadmap_content_container;
    @FXML private VBox optional_course_content_container;

    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (optional_course_container != null) {
            optional_course_container.setViewOrder(-1);
        }

        setEffect();

        if (hearted_roadmap_container != null) {
            applyHoverEffectToInside(hearted_roadmap_container);
        }

        route();
    }

    public void setRoadmapID(String roadmapID) {
        this.roadmapID = roadmapID;
        handleRoadmapNode();
    }

    private void route() {
        Navigator nav = new Navigator();

        if (pre_test != null) {
            pre_test.setOnMouseClicked(nav::preTestRoute);
        }
        if (pretest_button_text != null) {
            pretest_button_text.setOnMouseClicked(nav::preTestRoute);
        }
    }

    public void handleRoadmapNode() {
        try {
            OrderRoadmapDB orderRoadmapDB = new OrderRoadmapDB();
            ArrayList<String[]> courseList = orderRoadmapDB.getCourseListByRoadmapID(roadmapID);

            int col = 0, row = 0;

            for (String[] courseDetails : courseList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/roadmapnode.fxml"));
                Node courseItem = loader.load();
                roadmapnodecontroller controller = loader.getController();

                controller.setDescription(courseDetails[1]);
                controller.setSubjectName(courseDetails[0]);
                controller.setOrderNumber(Integer.parseInt(courseDetails[2]));

                ef.hoverEffect(courseItem);
                roadmap_node_container.add(courseItem, col, row);
                GridPane.setMargin(courseItem, new Insets(10, 30, 10, 30));

                col++;
                if (col == 2) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void applyHoverEffectToInside(HBox root) {
        for (Node node : root.lookupAll(".forHover")) {
            if (node instanceof Button p) {
                hoverEffect(p);
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

    public void hoverEffect(HBox btn) {
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

    private void setEffect() {
        if (optional_course_box != null) {
            ef.hoverEffect(optional_course_box);
        }
        if (pre_test != null) {
            hoverEffect(pre_test);
        }
    }
}
