package Student.loginAndSignUp;

import java.net.InetAddress;

public class Internet {
    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        if (isInternetAvailable()) {
            System.out.println("เชื่อมต่ออินเทอร์เน็ตได้ ✅");
        } else {
            System.out.println("ไม่มีการเชื่อมต่ออินเทอร์เน็ต ❌");
        }
    }
}
