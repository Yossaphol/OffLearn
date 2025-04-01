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

    @Override
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

    public void displayCourseList() {
        List<courseObject> courses = CartManager.getInstance().getCartList();
        courseContainer.getChildren().clear();

        for (courseObject course : courses) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/courseEnrollList.fxml"));
                Pane courseContent = loader.load();
                cartCardController controller = loader.getController();
                controller.setCourse(course);
                controller.setParentController(this);
                controller.setDescription(course.getDescription());
                controller.setName(course.getName());
                controller.setTeacherName(course.getTeacherName());
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
        if (!isClick) {
            edit.setStyle("-fx-background-color: #E8E8E8;" + "-fx-background-radius: 30px;" + "-fx-padding: 5px;");
            isClick = true;
        } else {
            edit.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 30px;" + "-fx-padding: 5px;");
            isClick = false;
        }
    }
}
