package Student.courseManage;

import Student.HomeAndNavigation.HomeController;
import Teacher.courseManagement.CourseItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EnrollCourseItemController implements Initializable {
    @FXML
    private Label courseNameLabel;

    @FXML
    private Button addtocart;

    private courseObject course;

    @FXML
    private Label tag1;

    public void setData(CourseItem course) {
        tag1.setText(course.getCourseCat()); // แสดงหมวดหมู่
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addtocart.setOnAction(event -> {
            if (course != null) {
                CartManager.getInstance().addCourse(course);
                System.out.println("✅ Added to cart: " + course.getName());
            }
        });

        // ✅ ผูกปุ่ม "รายละเอียด" กับ method goToDetailPage
        detailBtn.setOnAction(this::goToDetailPage);
    }


    public void setData(courseObject course) {
        this.course = course;
        courseNameLabel.setText(course.getName());
    }

    @FXML
    private Label shortDescription;
    @FXML
    private Label teacherName;
    @FXML
    private javafx.scene.shape.Circle teacher_pic;


    public void setCourseName(String name) {
        courseNameLabel.setText(name);
    }

    public void setShortDescription(String desc) {
        shortDescription.setText(desc);
    }

    public void setTeacherName(String name) {
        teacherName.setText(name);
    }

    public void setTeacherImg(String path) {
        // สมมติว่าคุณมี Circle ชื่อ teacher_pic อยู่ใน FXML
        new HomeController().loadAndSetImage(teacher_pic, path);
    }

    @FXML
    private Label categoryLabel;

    public void setCategoryName(String category) {
        categoryLabel.setText(category);
    }

    @FXML
    private Button detailBtn; // ปุ่ม 'รายละเอียด'

    @FXML
    private void goToDetailPage(ActionEvent event) {
        if (course == null) {
            System.out.println("❌ Course is null");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/courseEnroll.fxml"));
            Node root = loader.load();

            // ดึง controller ของหน้าใหม่
            courseEnrollController controller = loader.getController();

            // ส่งข้อมูลคอร์สไปให้ controller หน้าใหม่
            controller.setCourseDetail(
                    course.getName(),
                    course.getShortDescription(),
                    course.getDescription(),
                    course.getPicture(),
                    "/img/icon/artificial-intelligence.png", // หรือใช้จาก course.getCategoryImg()
                    course.getCategoryName(),
                    course.getPrice(),
                    course.getRating(),
                    126,
                    7
            );

            // เปลี่ยน scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene((Parent) root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
