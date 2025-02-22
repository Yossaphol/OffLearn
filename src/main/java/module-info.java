module com.example.offlearn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.xml.dom;
    requires jdk.compiler;
    requires jdk.dynalink;


    opens inbox to javafx.fxml;
    exports inbox;

    opens loginAndSignUp to javafx.fxml;
    exports loginAndSignUp;

    opens HomeAndNavigation to javafx.fxml;
    exports HomeAndNavigation;

    opens dashboard to javafx.fxml;
    exports dashboard;

    opens roadmap to javafx.fxml;
    exports roadmap;

    opens task to javafx.fxml;
    exports task;

    exports inbox.pChat;
    opens inbox.pChat to javafx.fxml;

    exports inbox.gChat;
    opens inbox.gChat to javafx.fxml;

}