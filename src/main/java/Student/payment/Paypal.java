package Student.payment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Paypal {

    private static final String CLIENT_ID = "Ab19y0O5MXTWy-AYYRUbC10euWgmXrYJVnYL_nx6RRj8w0OQ8cF1WK5NId74C8lDeKVXdURsojzWr1H7";
    private static final String CLIENT_SECRET = "EDSAS2SooPMPeayVIRsROc7oBGF2ntEShv9aqlj6kBvyg8b70i6Aj0knEtAqYKqWnVmCJFmP9xwRLslN";
    private static final String API_BASE = "https://api-m.sandbox.paypal.com/v2/checkout/orders";

    public static Image generateQRCode(String text, int size) throws WriterException {
        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size);
        WritableImage image = new WritableImage(size, size);
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (bitMatrix.get(x, y)) {
                    pixelWriter.setArgb(x, y, 0xFF000000);
                } else {
                    pixelWriter.setArgb(x, y, 0xFFFFFFFF);
                }
            }
        }
        return image;
    }


    public static String getAccessToken() throws IOException {
        HttpURLConnection con = createConnection("https://api-m.sandbox.paypal.com/v1/oauth2/token", "POST");
        con.setRequestProperty("Authorization", "Basic " + encodeCredentials());
        sendRequest(con, "grant_type=client_credentials");

        return new JSONObject(readResponse(con)).getString("access_token");
    }

    public static String[] createOrder(String accessToken, double amount, String currency) throws IOException {
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);
        con.setDoOutput(true);

        String postData = "{\"intent\":\"CAPTURE\",\"purchase_units\":[{\"amount\":{\"currency_code\":\"" + currency + "\",\"value\":\"" + amount + "\"}}],\"application_context\":{\"return_url\":\"https://example.com/success\",\"cancel_url\":\"https://example.com/cancel\"}}";

        OutputStream os = con.getOutputStream();
        os.write(postData.getBytes(StandardCharsets.UTF_8));
        os.close();

        if (con.getResponseCode() != 201) {
            throw new IOException("Failed, code: " + con.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JSONObject res = new JSONObject(response.toString());
        String orderId = res.getString("id");

        JSONArray links = res.getJSONArray("links");
        String approveUrl = "";
        for (int i = 0; i < links.length(); i++) {
            JSONObject link = links.getJSONObject(i);
            if (link.getString("rel").equals("approve")) {
                approveUrl = link.getString("href");
                break;
            }
        }

        if (approveUrl.isEmpty()) {
            throw new IOException("No approval URL found");
        }

        return new String[]{ orderId, approveUrl };
    }

    public static boolean waitForApproval(String accessToken, String orderId) throws IOException {
        int maxRetries = 30;
        int retryInterval = 5000;

        for (int i = 0; i < maxRetries; i++) {
            if ("APPROVED".equals(getOrderStatus(accessToken, orderId))) {
                return true;
            }
            try {
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    public static boolean capturePayment(String accessToken, String orderId) throws IOException {
        HttpURLConnection con = createConnection(API_BASE + "/" + orderId + "/capture", "POST", accessToken);
        sendRequest(con, "{}");

        return new JSONObject(readResponse(con)).optString("status").equals("COMPLETED");
    }

    public static String getOrderStatus(String accessToken, String orderId) throws IOException {
        HttpURLConnection con = createConnection(API_BASE + "/" + orderId, "GET", accessToken);
        return new JSONObject(readResponse(con)).getString("status");
    }

    private static HttpURLConnection createConnection(String url, String method) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        return con;
    }

    private static HttpURLConnection createConnection(String url, String method, String accessToken) throws IOException {
        HttpURLConnection con = createConnection(url, method);
        con.setRequestProperty("Authorization", "Bearer " + accessToken);
        return con;
    }

    private static void sendRequest(HttpURLConnection con, String postData) throws IOException {
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(postData.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }
    }

    private static String readResponse(HttpURLConnection con) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            return in.lines().reduce("", (a, b) -> a + b);
        }
    }

    private static String encodeCredentials() {
        return Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8));
    }
}
