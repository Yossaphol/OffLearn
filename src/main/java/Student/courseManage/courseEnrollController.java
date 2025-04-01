package Student.courseManage;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class courseEnrollController implements Initializable {
    @FXML
    public VBox leftWrapper;
    public HBox rootPage;
    public HBox searchbar_container;
    public Text adviceText;
    //public Text thisCourseFor;
    //public Text requirement;
    //public Rectangle pic1;
    //public Rectangle pic2;
    //public Rectangle pic3;
    public Rectangle courseImg;
    public Button addToCartBtn;
    public Button enrollBtn;
    public HBox reviewContainer;
    public Button enrollBtn1;
    public Button otherCourse;
    public Label couseName;
    public Label shortDescription;
    public Circle categoryPic;
    public Label category;
    public Text description;
    public Label totalLesson;
    public Label price;
    public Label rating;
    public ImageView one;
    public ImageView two;
    public ImageView three;
    public ImageView four;
    //public Circle reviewer1;
    //public Circle reviewer2;
    //public Circle reviewer3;
    public Label reviewerName3;
    public Label reviewerName2;
    public Label reviewerName1;
    HomeController ef = new HomeController();

    public void initialize(URL location, ResourceBundle resources) {
        setDetail();
//        setCourseRequirement("ทักษะการสังเกตและแก้ไขปัญหา\n" +
//                "ทักษะคอมพิวเตอร์เบื้องต้น (ใช้งานโปรแกรมพื้นฐาน และ อินเทอร์เน็ต)\n" +
//                "ทักษะคณิตศาสตร์เบื้องต้น (บวก ลบ คูณ หาร – แก้สมการเบื้องต้น)\n" +
//                "Computer หรือ Notebook ที่สามารถใช้งาน Internet ได้ (Windows 8, mac OS 10.9 ขึ้นไป หรือ เทียบเท่า)\n" +
//                "ความใฝ่ฝันในการเริ่มต้นเส้นทางนักพัฒนาโปรแกรม", "ผู้ที่ต้องการเรียนพื้นฐานการพัฒนาโปรแกรมอย่างจริงจัง\n" +
//                "ผู้ที่ต้องการเตรียมย้ายสายงานสู่การพัฒนาโปรแกรมและนักวิเคราะห์ข้อมูล\n" +
//                "ผู้ที่ต้องการเริ่มต้นเข้าสู่ด้าน Tech Startup\n" +
//                "นักเรียน / นิสิต / นักศึกษา และ ผู้สนใจที่ต้องการเริ่มพื้นฐานการพัฒนาโปรแกรม");
//        setImg();
        //applyHoverEffectToInside(reviewContainer);
        setEffect();
        route();

        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        four.setVisible(false);

        setCourseDetail(
                "Algorithm for Machine Learning",
                "Learn Algorithm to solve real problems",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                "/img/Picture/bg.jpg",
                "/img/icon/artificial-intelligence.png",
                "AI",
                1750.00,
                4.00,
                740,
                20
                );
    }

    private void setEffect() {
        if (enrollBtn != null) ef.hoverEffect(enrollBtn);
        if (addToCartBtn != null) ef.hoverEffect(addToCartBtn);
        if (enrollBtn1 != null) ef.hoverEffect(enrollBtn1);
        if (otherCourse != null) ef.hoverEffect(otherCourse);
    }


//    public void applyHoverEffectToInside(HBox root) {
//        for (Node node : root.lookupAll(".forHover")) {
//            if (node instanceof HBox box) {
//                hoverEffect(box);
//            }
//        }
//    }

    private void route(){
        Navigator nav = new Navigator();
        otherCourse.setOnMouseClicked(nav::courseRoute);
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

//    public void setImg(){
//        ef.loadAndSetImage(pic1, "/img/Picture/bg.jpg");
//        ef.loadAndSetImage(pic2, "/img/Picture/jeswin-thomas-2Q3Ivd-HsaM-unsplash.jpg");
//        ef.loadAndSetImage(pic3, "/img/Picture/florian-olivo-4hbJ-eymZ1o-unsplash.jpg");
//        ef.loadAndSetImage(reviewer1, "/img/Profile/doctor.png");
//        ef.loadAndSetImage(reviewer2, "/img/Profile/teacher.png");
//        ef.loadAndSetImage(reviewer3, "/img/Profile/man.png");
//    }

    public void setCourseDetail(String name, String _shortDescription, String _description, String picPath, String categoryPicPath, String _category, double _price, double _rating, int totalReview, int _totalLesson){
        setRating(_rating);
        couseName.setText(name);
        shortDescription.setText(_shortDescription);
        description.setText(_description);
        totalLesson.setText(_totalLesson + " บท");
        price.setText(_price + " บาท");
        rating.setText(_rating + " (" + totalReview + ")");
        category.setText(_category);

        // ✅ โหลดภาพจาก path
        URL resource = getClass().getResource("/images/sample.png");
        if (resource != null) {
            Image image = new Image(resource.toExternalForm());
            courseImg.setFill(new ImagePattern(image));
        } else {
            System.err.println("⚠️ ไม่พบไฟล์ภาพ: /images/sample.png");
            // สามารถใส่ภาพ fallback ได้ เช่นภาพพื้นหลังสีเทา
            courseImg.setFill(Color.LIGHTGRAY); // ต้อง import javafx.scene.paint.Color;
        }


    }



    private void setDetail(){

    }

//    public void setCourseRequirement(String requirement_, String thisCourseIsfor){
//        //thisCourseFor.setText(thisCourseIsfor);
//        //requirement.setText(requirement_);
//    }

    public void setRating(double rating){

        switch ((int) rating){
            case 1:
                one.setVisible(true);
                break;
            case 2:
                one.setVisible(true);
                two.setVisible(true);
                break;
            case 3:
                one.setVisible(true);
                two.setVisible(true);
                three.setVisible(true);
                break;
            case 4:
                one.setVisible(true);
                two.setVisible(true);
                three.setVisible(true);
                four.setVisible(true);
                break;
        }
    }



}
