package Student.payment;

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

public class paymentController implements Initializable {

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
                    Platform.runLater(() -> status.setText("Payment not approved"));
                    status.setStyle("-fx-text-fill: red;");
                    return;
                }

                boolean isCaptured = capturePayment(accessToken, orderId);
                String paymentMessage = isCaptured ? "Payment successful" : "Payment failed";

                Platform.runLater(() -> status.setText(paymentMessage));
                status.setStyle(isCaptured ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
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

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
