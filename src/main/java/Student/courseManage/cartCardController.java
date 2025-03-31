package Student.courseManage;

import Student.payment.paymentController;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class cartCardController implements Initializable {
    public Rectangle picture;
    public Label name;
    public Label description;
    public Circle categoryPic;
    public Label category;
    public ImageView one;
    public ImageView two;
    public ImageView three;
    public ImageView four;
    public Label review;
    public Label price;
    public Button enrollBtn;

    @FXML
    public Button deleteBtn;

    private String teacherName;
    private String courseName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        four.setVisible(false);
        deleteBtn.setVisible(false);

        route();
    }

    private void route() {
        enrollBtn.setOnMouseClicked(e -> {
            double coursePrice = extractPrice();
            openPaymentPopup(courseName, coursePrice, teacherName);
        });
    }

    private double extractPrice() {
        try {
            String priceText = price.getText().replace(" บาท", "").replace(",", "").trim();
            return Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    private void openPaymentPopup(String courseName, double amount, String teacher) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/payment/payment.fxml"));
            Parent popupRoot = loader.load();

            paymentController controller = loader.getController();
            controller.setCourseInfo(courseName, amount);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("ชำระเงิน");
            popupStage.setScene(new Scene(popupRoot));
            popupStage.setResizable(false);
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTeacherName(String t) {
        this.teacherName = t;
    }

    public void setName(String n) {
        this.courseName = n;
        name.setText(n);
    }


    public void setDescription(String n) {
        description.setText(n);
    }

    public void setCategory(String picturePath, String categoryName) {
        category.setText(categoryName);
        try {
            URL url = getClass().getResource(picturePath);
            if (url != null) {
                categoryPic.setFill(new ImagePattern(new Image(url.toExternalForm())));
            } else {
                System.out.println("❌ ไม่พบรูป category: " + picturePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPrice(double n) {
        price.setText(n + " บาท");
    }

    public void setRating(double rating, int totalReview) {
        review.setText(rating + " (" + totalReview + ")");
        one.setVisible(rating >= 1);
        two.setVisible(rating >= 2);
        three.setVisible(rating >= 3);
        four.setVisible(rating >= 4);
    }

    public void setPicture(String path) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                picture.setFill(new ImagePattern(new Image(url.toExternalForm())));
            } else {
                System.out.println("❌ ไม่พบรูปภาพ course: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayDeleteBtn(boolean isVisible) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), deleteBtn);
        if (isVisible) {
            deleteBtn.setVisible(true);
            deleteBtn.setOpacity(0);
            fade.setToValue(1.0);
        } else {
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(event -> deleteBtn.setVisible(false));
        }
        fade.setInterpolator(Interpolator.EASE_OUT);
        fade.play();
    }

    public void deleteCourse(ActionEvent e) {
        System.out.println("Delete success!");
        // ✅ TODO: ลบออกจาก CartManager ถ้าต้องการ
    }
}
