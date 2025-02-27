module com.example.offlearn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.xml.dom;
    requires jdk.compiler;
    requires jdk.dynalink;
    requires io.github.cdimascio.dotenv.java;

    opens client.mainApp to javafx.fxml;
    exports client.mainApp;

    opens client.inbox to javafx.fxml;
    exports client.inbox;

    opens client.loginAndSignUp to javafx.fxml;
    exports client.loginAndSignUp;

    opens client.HomeAndNavigation to javafx.fxml;
    exports client.HomeAndNavigation;

    opens client.dashboard to javafx.fxml;
    exports client.dashboard;

    opens client.roadmap to javafx.fxml;
    exports client.roadmap;

    opens client.task to javafx.fxml;
    exports client.task;

    exports client.inbox.pChat;
    opens client.inbox.pChat to javafx.fxml;

    exports client.inbox.gChat;
    opens client.inbox.gChat to javafx.fxml;

    exports client.swing to javafx.graphics;

    exports Server.inbox.pChat;
    opens Server.inbox.pChat to javafx.fxml;
}