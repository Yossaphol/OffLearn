module com.example.offlearn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens  com.example.offlearn.pChat to javafx.fxml;
    exports com.example.offlearn.pChat;

    opens loginAndSignUp to javafx.fxml;
    exports loginAndSignUp;

    opens HomeAndNavigation to javafx.fxml;
    exports HomeAndNavigation;


}