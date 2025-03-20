package Student.roadmap;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class myRoadmapController implements Initializable {


    public GridPane roadmap_node_container;
    public HBox optional_course_container;
    public HBox hearted_roadmap_container;
    public Pane optional_course_box;
    public Button relateCourse2;
    public Button relateCourse1;
    public HBox pre_test;
    public Pane selected_roadmap_box;
    public Button pretest_button_text;
    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        optional_course_container.setViewOrder(-1);

        setEffect();
        applyHoverEffectToInside(hearted_roadmap_container);
        handleRoadmapNode();
        displaySelectedRoadmapCard();
        route();
    }

    private void route() {
        Navigator nav = new Navigator();
        // pre test
        pre_test.setOnMouseClicked(nav::preTestRoute);
        pretest_button_text.setOnMouseClicked(nav::preTestRoute);

    }

    public void handleRoadmapNode() {
        try {
            List<String> courseList = new ArrayList<>();
            courseList.add("Linear algebra, Probability and statistics, Calculus1");
            courseList.add("Discrete Mathematics, Data Structures, Algorithms");
            courseList.add("Discrete Mathematics, Data Structures, Algorithms");
            courseList.add("Discrete Mathematics, Data Structures, Algorithms");
            courseList.add("Discrete Mathematics, Data Structures, Algorithms");

            List<String> description = new ArrayList<>();
            description.add("Advance Math");
            description.add("Computer Science");
            description.add("Computer Science");
            description.add("Computer Science");
            description.add("Computer Science");

            int col = 0, row = 0;
            for (int i = 0; i < courseList.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/roadmapnode.fxml"));
                Node courseItem = loader.load();
                roadmapnodecontroller controller = loader.getController();

                // Set details
                controller.setDescription(courseList.get(i));
                controller.setSubjectName(description.get(i));
                controller.setOrderNumber(i+1);

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
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            dropShadow.setColor(Color.web("#c4c4c4", 0.25));
            btn.setEffect(dropShadow);
            scaleDown.play();
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
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            dropShadow.setColor(Color.web("#c4c4c4", 0.25));
            btn.setEffect(dropShadow);
            scaleDown.play();
        });
    }

    private void setEffect(){
        ef.hoverEffect(optional_course_box);
        ef.hoverEffect(relateCourse1);
        ef.hoverEffect(relateCourse2);
        hoverEffect(pre_test);
    }

    public void displaySelectedRoadmapCard(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/myRoadmapCard.fxml"));
            Pane searchbarContent = loader.load();
            myRoadmapCard controller = loader.getController();

            //Set detail
            controller.setTeacherName("Wirayabovorn B.");
            controller.setRoadmapName("Machine Learning");
            controller.setDurationRoadmap(40);
            controller.setProgression(10, 20);
            controller.setShortDescription("How to train your Dragon");
            controller.setTeacherExpertise("Programming");
            controller.setTeacherPic("/img/Profile/man.png");


            selected_roadmap_box.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
