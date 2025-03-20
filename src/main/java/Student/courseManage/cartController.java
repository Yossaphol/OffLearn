package Student.courseManage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class cartController implements Initializable {
    @FXML
    public VBox leftWrapper;
    public HBox rootPage;
    public HBox searchbar_container;
    public VBox courseContainer;
    @FXML
    public Button edit;
    private boolean isDeleteVisible = true;
    private boolean isClick = false;

    public void initialize(URL location, ResourceBundle resources) {
        displayCourseList();
        editCart();
    }

    private void editCart() {
        edit.setOnMouseClicked(event -> {
            for (Node node : courseContainer.getChildren()) {
                if (node instanceof HBox) {
                    HBox courseBox = (HBox) node;
                    for (Node child : courseBox.getChildren()) {
                        if (child instanceof Pane) {
                            Pane courseContent = (Pane) child;
                            cartCardController controller = (cartCardController) courseContent.getUserData();
                            if (controller != null) {
                                controller.displayDeleteBtn(isDeleteVisible);
                            }
                        }
                    }
                }
            }
            isDeleteVisible = !isDeleteVisible;
        });
    }

    private void displayCourseList() {
        List<courseObject> courses = List.of(
                new courseObject("Learn algorithm to solve real problem", "Data Structure and Algorithm", 1900, 3.5, 437, "/img/icon/_.png", "Coding", "/img/Picture/bg.jpg"),
                new courseObject("Master web development with JavaScript", "Full-Stack Web Development", 2500, 4.2, 520, "/img/icon/_.png", "Web Development", "/img/Picture/bg.jpg"),
                new courseObject("Understand AI concepts and applications", "Artificial Intelligence Basics", 3000, 4.8, 600, "/img/icon/_.png", "AI & ML", "/img/Picture/bg.jpg")
        );

        courseContainer.getChildren().clear();

        for (courseObject course : courses) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/courseEnrollList.fxml"));
                Pane courseContent = loader.load();
                cartCardController controller = loader.getController();
                controller.setDescription(course.getDescription());
                controller.setName(course.getName());
                controller.setPrice(course.getPrice());
                controller.setRating(course.getRating(), course.getReviewCount());
                controller.setCategory(course.getCategoryIcon(), course.getCategoryName());
                controller.setPicture(course.getPicture());
                courseContent.setUserData(controller);

                HBox courseBox = new HBox();
                courseBox.getChildren().add(courseContent);
                courseContainer.getChildren().add(courseBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void changeColor(ActionEvent event) {
        if(!isClick){
            edit.setStyle("-fx-background-color: #E8E8E8;"+"-fx-background-radius: 30px;"+"-fx-padding: 5px;");
            isClick = true;
        }else{
            edit.setStyle("-fx-background-color: transparent;"+"-fx-background-radius: 30px;"+"-fx-padding: 5px;");
            isClick = false;
        }
    }
}
