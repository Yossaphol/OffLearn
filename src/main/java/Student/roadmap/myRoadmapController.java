package Student.roadmap;
import Database.OrderRoadmapDB;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class myRoadmapController implements Initializable {

    private String roadmapID;

    public GridPane roadmap_node_container;
    public HBox optional_course_container;
    public HBox hearted_roadmap_container;
    public Pane optional_course_box;
    public HBox pre_test;
    public Pane selected_roadmap_box;
    public Button pretest_button_text;
    public VBox related_roadmap_content_container;
    public VBox optional_course_content_container;

    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        optional_course_container.setViewOrder(-1);

        setEffect();
        applyHoverEffectToInside(hearted_roadmap_container);
        handleHeartedRoadmap();
        handleRoadmapNode();
        handleOptionalRoadmap();
        handleRelatedRoadmap();
        displaySelectedRoadmapCard();
        route();
    }

    public void setRoadmapID(String roadmapID) {
        this.roadmapID = roadmapID;
        handleRoadmapNode();
    }

    private void route() {
        Navigator nav = new Navigator();
        // pre test
        pre_test.setOnMouseClicked(nav::preTestRoute);
        pretest_button_text.setOnMouseClicked(nav::preTestRoute);

    }

    public void handleHeartedRoadmap() {
        try {
            List<String> roadmap = new ArrayList<>();
            roadmap.add("Roadmap 1");
            roadmap.add("Roadmap 2");
            roadmap.add("Roadmap 3");

            for (String road : roadmap) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/fxml/Student/courseManage/heartedroadmap.fxml"));

                Node roadmapNode = loader.load();

                HeartedRoadmapController controller = loader.getController();

                controller.setHearted_roadmap(road);
                hearted_roadmap_container.getChildren().add(roadmapNode);
            }
        } catch (IOException e) {
            System.err.println("FXML loading error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRoadmapNode() {
        try {
            OrderRoadmapDB orderRoadmapDB = new OrderRoadmapDB();


            ArrayList<String[]> courseList = orderRoadmapDB.getCourseListByRoadmapID(roadmapID);
            //courseList.add("Linear algebra, Probability and statistics, Calculus1");
            //courseList.add("Discrete Mathematics, Data Structures, Algorithms");
            //courseList.add("Discrete Mathematics, Data Structures, Algorithms");
            //courseList.add("Discrete Mathematics, Data Structures, Algorithms");
            //courseList.add("Discrete Mathematics, Data Structures, Algorithms");

            List<String> description = new ArrayList<>();
            //description.add("Advance Math");
            //description.add("Computer Science");
            //description.add("Computer Science");
            //description.add("Computer Science");
            //description.add("Computer Science");

            int col = 0, row = 0;

            for (String[] courseDetails : courseList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/roadmapnode.fxml"));
                Node courseItem = loader.load();
                roadmapnodecontroller controller = loader.getController();

                controller.setDescription(courseDetails[1]);  // รายละเอียดคอร์ส
                controller.setSubjectName(courseDetails[0]);  // ชื่อคอร์ส
                controller.setOrderNumber(Integer.parseInt(courseDetails[2]));  // ลำดับที่

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

    public void handleOptionalRoadmap() {
        try {
            List<String> courses = new ArrayList<>();
            courses.add("Course 1");
            courses.add("Course 2");
            courses.add("Course 3");

            List<String> descriptions = new ArrayList<>();
            descriptions.add("Des 1");
            descriptions.add("Des 2");
            descriptions.add("Des 3");

            for (int i = 0; i < courses.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/fxml/Student/courseManage/optionalcourse.fxml"));

                Node optionalCourse = loader.load();

                OptionalCourseController controller = loader.getController();

                controller.setOptional_Course_Name(courses.get(i));
                controller.setOptional_Course_Description(descriptions.get(i));
                optional_course_content_container.getChildren().add(optionalCourse);
            }
        } catch (IOException e) {
            System.err.println("FXML loading error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRelatedRoadmap() {
        try {
            List<String> roadmaps = new ArrayList<>();
            roadmaps.add("Roadmap 1");
            roadmaps.add("Roadmap 2");
            roadmaps.add("Roadmap 3");

            List<String> descriptions = new ArrayList<>();
            descriptions.add("Des 1");
            descriptions.add("Des 2");
            descriptions.add("Des 3");

            List<Integer> progressions = new ArrayList<>();
            progressions.add(10);
            progressions.add(20);
            progressions.add(30);

            List<Integer> durations = new ArrayList<>();
            durations.add(10);
            durations.add(20);
            durations.add(30);

            for (int i = 0; i < roadmaps.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/fxml/Student/courseManage/relatedRoadmap.fxml"));

                Node relatedRoadmap = loader.load();

                RelatedRoadmapController controller = loader.getController();

                controller.setRelatedRoadmapName(roadmaps.get(i));
                controller.setRelatedRoadmapDescription(descriptions.get(i));
                controller.setContentProgression(progressions.get(i), 100); // Example total: 100
                controller.setRelatedRoadmapDuration(durations.get(i));
                related_roadmap_content_container.getChildren().add(relatedRoadmap);
            }
        } catch (IOException e) {
            System.err.println("FXML loading error: " + e.getMessage());
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
