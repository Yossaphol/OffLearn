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

    @FXML
    private ImageView qrCode;

    @FXML
    private VBox paymentDisplay;

    @FXML
    private Label status;

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
                String[] orderData = Paypal.createOrder(accessToken, 10.0, "THB");
                String orderId = orderData[0];
                String approvalUrl = orderData[1];

                Image qrCodeImage = Paypal.generateQRCode(approvalUrl, 300);

                Platform.runLater(() -> qrCode.setImage(qrCodeImage));


                boolean isApproved = Paypal.waitForApproval(accessToken, orderId);
                if (!isApproved) {
                    Platform.runLater(() -> status.setText("Payment not approved"));
                    return;
                }

                boolean isCaptured = capturePayment(accessToken, orderId);
                String paymentMessage = isCaptured ? "Payment successful" : "Payment failed";

                Platform.runLater(() -> status.setText(paymentMessage));

            } catch (IOException | WriterException e) {
                e.printStackTrace();
                Platform.runLater(() -> status.setText("Error generating QR code"));
            }
        }).start();
    }

}
