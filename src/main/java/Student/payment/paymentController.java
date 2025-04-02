package Student.payment;

import Database.CourseDB;
import Database.EnrollmentDB;
import com.google.zxing.WriterException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Student.payment.Paypal.capturePayment;
import a_Session.SessionManager;
import javafx.stage.Stage;


public class paymentController implements Initializable {

    private Runnable onPaymentSuccess;

    private double amount;

    @FXML
    private ImageView qrCode;

    @FXML
    private VBox paymentDisplay;

    @FXML
    private Label status;

    @FXML
    private Label courseTitle;

    @FXML
    private Label priceDisplay;

    private String courseName;
    private double coursePrice;
    private int courseId;
    private int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            qrCodeDisplay();
        } catch (WriterException e) {
            System.out.println(e);
        }
    }

    public void qrCodeDisplay() throws WriterException {
        new Thread(() -> {
            try {
                String accessToken = Paypal.getAccessToken();
                String[] orderData = Paypal.createOrder(accessToken, amount, "THB");
                String orderId = orderData[0];
                String approvalUrl = orderData[1];

                Image qrCodeImage = Paypal.generateQRCode(approvalUrl, 300);
                Platform.runLater(() -> qrCode.setImage(qrCodeImage));

                boolean isApproved = Paypal.waitForApproval(accessToken, orderId);
                if (!isApproved) {
                    Platform.runLater(() -> {
                        status.setText("Payment not approved");
                        status.setStyle("-fx-text-fill: red;");
                    });
                    return;
                }

                boolean isCaptured = capturePayment(accessToken, orderId);
                String paymentMessage = isCaptured ? "Payment successful" : "Payment failed";

                Platform.runLater(() -> {
                    status.setText(paymentMessage);
                    status.setStyle(isCaptured ? "-fx-text-fill: green;" : "-fx-text-fill: red;");

                    if (isCaptured) {
                        insertEnrollmentToDatabase();

                        if (onPaymentSuccess != null) {
                            onPaymentSuccess.run();
                        }

                        // ปิดหน้าต่าง payment ภายใน 5 วินาที หลังจาก suscess
                        new Thread(() -> {
                            try {
                                Thread.sleep(5000); // 5 วินาที
                                Platform.runLater(() -> {
                                    Stage stage = (Stage) qrCode.getScene().getWindow();
                                    stage.close();
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                });

            } catch (IOException | WriterException e) {
                e.printStackTrace();
                Platform.runLater(() -> status.setText("Error generating QR code"));
            }
        }).start();
    }


    public void setCourseInfo(String name, double price) {
        this.courseName = name;
        this.coursePrice = price;
        this.amount = price;

        if (courseTitle != null) courseTitle.setText(name);
        if (priceDisplay != null) priceDisplay.setText(String.format("%.0f THB", price));
    }

    public void setCourseId(int id) {
        this.courseId = id;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    private void insertEnrollmentToDatabase() {
        String userIdStr = SessionManager.getInstance().getUserID();
        if (userIdStr == null) {
            System.err.println("ไม่พบ user ID ใน session!");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);

            if (courseId == 0 && courseName != null) {
                CourseDB db = new CourseDB();
                courseId = db.getCourseID(courseName);
                System.out.println("courseId mapped from name = " + courseId);
            }

            System.out.println("Preparing to enroll userId = " + userId + ", courseId = " + courseId);

            EnrollmentDB enrollmentDB = new EnrollmentDB();
            boolean success = enrollmentDB.insertEnrollment(userId, courseId);

            if (success) {
                System.out.println("Enroll saved to DB!");
            } else {
                System.err.println("Failed to insert enroll into DB.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid user ID format.");
        }
    }

    public void setOnPaymentSuccess(Runnable callback) {
        this.onPaymentSuccess = callback;
    }

}
