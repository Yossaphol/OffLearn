package Student.courseManage;

import Student.HomeAndNavigation.HomeController;
import Teacher.courseManagement.CourseItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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


}
