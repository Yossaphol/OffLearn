package Student.courseManage;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Student.payment.paymentController;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class courseEnrollController implements Initializable {
    @FXML public VBox leftWrapper;
    @FXML public HBox rootPage;
    @FXML public HBox searchbar_container;
    @FXML public Text adviceText;
    @FXML public Rectangle courseImg;
    @FXML public Button addToCartBtn;
    @FXML public Button enrollBtn;
    @FXML public HBox reviewContainer;
    @FXML public Button enrollBtn1;
    @FXML public Button otherCourse;
    @FXML public Label couseName;
    @FXML public Label shortDescription;
    @FXML public Circle categoryPic;
    @FXML public Label category;
    @FXML public Text description;
    @FXML public Label totalLesson;
    @FXML public Label price;
    @FXML public Label rating;
    @FXML public ImageView one, two, three, four;
    @FXML public Label reviewerName3, reviewerName2, reviewerName1;

    // 🔒 ตัวแปรเก็บค่าคอร์ส
    private String courseName;
    private double coursePrice;
    private int courseId;

    HomeController ef = new HomeController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEffect();

        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        four.setVisible(false);

        // 👇 ทดลองเรียกคอร์สตัวอย่าง
        setCourseDetail(
                "Algorithm for Machine Learning",
                "Learn Algorithm to solve real problems",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry...",
                "/img/Picture/bg.jpg",
                "/img/icon/artificial-intelligence.png",
                "AI",
                1750.00,
                4.00,
                740,
                20,
                76  // 👈 courseId (ต้องมาจากฐานข้อมูลจริง)
        );

        // 🧠 เชื่อมปุ่ม
        enrollBtn.setOnAction(e -> handleEnrollAction());
        addToCartBtn.setOnAction(e -> addToCart());
        otherCourse.setOnAction(e -> backToCoursePage());
    }

    private void setEffect() {
        if (enrollBtn != null) ef.hoverEffect(enrollBtn);
        if (addToCartBtn != null) ef.hoverEffect(addToCartBtn);
        if (enrollBtn1 != null) ef.hoverEffect(enrollBtn1);
        if (otherCourse != null) ef.hoverEffect(otherCourse);
    }

    // 👉 เมื่อกด Enroll ให้เปิดหน้าชำระเงิน พร้อมส่งข้อมูลไป paymentController
    @FXML
    private void handleEnrollAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/payment/payment.fxml"));
            Parent root = loader.load();

            paymentController controller = loader.getController();
            controller.setCourseInfo(courseName, coursePrice);
            controller.setCourseId(courseId);
            // ✅ ไม่ต้องเซ็ต userId เพราะ controller ดึงจาก SessionManager อยู่แล้ว

            Stage stage = new Stage();
            stage.setTitle("ชำระเงิน");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 📦 เพิ่มลงในตะกร้า (ยังไม่เชื่อม DB)
    public void addToCart() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("เพิ่มคอร์สลงตะกร้าแล้ว!");
        alert.showAndWait();
    }

    // 🔁 กลับไปหน้า Course
    public void backToCoursePage() {
        Navigator nav = new Navigator();
        nav.navigateTo("/fxml/Student/courseManage/course.fxml");
    }

    // 🧠 ใส่ข้อมูลคอร์ส
    public void setCourseDetail(String name, String _shortDescription, String _description, String picPath,
                                String categoryPicPath, String _category, double _price, double _rating,
                                int totalReview, int _totalLesson, int courseId) {
        this.courseName = name;
        this.coursePrice = _price;
        this.courseId = courseId;

        setRating(_rating);
        couseName.setText(name);
        shortDescription.setText(_shortDescription);
        description.setText(_description);
        totalLesson.setText(_totalLesson + " บท");
        price.setText(_price + " บาท");
        rating.setText(_rating + " (" + totalReview + ")");
        category.setText(_category);

        // ❗ เปลี่ยนเป็นโหลดภาพจริงถ้ามี
        URL resource = getClass().getResource("/images/sample.png");
        if (resource != null) {
            Image image = new Image(resource.toExternalForm());
            courseImg.setFill(new ImagePattern(image));
        } else {
            System.err.println("⚠️ ไม่พบไฟล์ภาพ: /images/sample.png");
            courseImg.setFill(Color.LIGHTGRAY);
        }
    }

    public void setRating(double rating) {
        one.setVisible(rating >= 1);
        two.setVisible(rating >= 2);
        three.setVisible(rating >= 3);
        four.setVisible(rating >= 4);
    }
}
