module com.example.offlearn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens  com.example.offlearn.pChat to javafx.fxml;
    exports com.example.offlearn.pChat;
    exports com.example.offlearn.pChat.DataBase;
    opens com.example.offlearn.pChat.DataBase to javafx.fxml;
}