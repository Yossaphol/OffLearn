package Student.courseManage;

import Student.HomeAndNavigation.HomeController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Modality;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class EnrollCourseItemController implements Initializable {

    @FXML private Label courseNameLabel;
    @FXML private Button addtocart;
    @FXML private Label tag1;
    @FXML private Label shortDescription;
    @FXML private Label teacherName;
    @FXML private Circle teacher_pic;
    @FXML private Label categoryLabel;
    @FXML private Button detailBtn;
    @FXML private Rectangle course_image;


    private courseObject course;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (addtocart != null) {
            addtocart.setOnAction(event -> {
                if (course != null) {
                    CartManager.getInstance().addCourse(course);
                    System.out.println("Added to cart: " + course.getName());
                }
            });
        }

        if (detailBtn != null) {
            detailBtn.setOnAction(this::goToDetailPage);
        }
    }

    public void setData(courseObject course) {
        this.course = course;

        if (courseNameLabel != null) courseNameLabel.setText(course.getName());
        if (tag1 != null) tag1.setText(course.getCategoryName());
        if (shortDescription != null) shortDescription.setText(course.getShortDescription());
        if (teacherName != null) teacherName.setText(course.getTeacherName());
        if (categoryLabel != null) categoryLabel.setText(course.getCategoryName());

        if (teacher_pic != null) {
            String teacherImg = course.getTeacherImg();
            if (teacherImg != null && !teacherImg.isBlank()) {
                new HomeController().loadAndSetImage(teacher_pic, teacherImg);
            } else {
                System.out.println("teacherImg เป็น null หรือว่าง: " + teacherImg);
            }
        }
    }


    @FXML
    private void goToDetailPage(ActionEvent event) {
        if (course == null) {
            System.out.println("Course is null");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/courseEnroll.fxml"));
            Parent root = loader.load();

            courseEnrollController controller = loader.getController();
            controller.setCourse(course);

            Stage popupStage = new Stage();
            popupStage.setTitle("รายละเอียดคอร์ส");
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCourseName(String name) {
        if (courseNameLabel != null) courseNameLabel.setText(name);
    }

    public void setShortDescription(String desc) {
        if (shortDescription != null) shortDescription.setText(desc);
    }

    public void setTeacherName(String name) {
        if (teacherName != null) teacherName.setText(name);
    }

    public void setTeacherImg(String path) {
        if (teacher_pic != null) new HomeController().loadAndSetImage(teacher_pic, path);
    }

    public void setCategoryName(String category) {
        if (categoryLabel != null) categoryLabel.setText(category);
    }

    public void setCourseImg(String imageUrl) {
        Image img;

        if (imageUrl.startsWith("http")) {
            img = new Image(imageUrl, true);

            // ฟังเมื่อโหลดเสร็จ
            img.progressProperty().addListener((obs, oldProgress, newProgress) -> {
                if (newProgress.doubleValue() >= 1.0) {
                    Platform.runLater(() -> course_image.setFill(new ImagePattern(img)));
                }
            });

        } else {
            InputStream is = getClass().getResourceAsStream(imageUrl);
            if (is != null) {
                img = new Image(is);
                course_image.setFill(new ImagePattern(img));
            } else {
                System.out.println("ไม่พบภาพ: " + imageUrl);
            }
        }
    }


    @FXML
    private void handleDetailButton(ActionEvent event) {
        if (course == null) {
            System.out.println("Course is null");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/courseEnroll.fxml"));
            Parent root = loader.load();

            courseEnrollController controller = loader.getController();
            controller.setCourse(course);

            Stage popupStage = new Stage();
            popupStage.setTitle("รายละเอียดคอร์ส");
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }








}
