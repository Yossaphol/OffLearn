module com.example.offlearn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.xml.dom;
    requires jdk.compiler;
    requires jdk.dynalink;
    requires io.github.cdimascio.dotenv.java;
    requires com.google.zxing.javase;
    requires com.google.zxing;
    requires org.json;
    requires stripe.java;
//    requires okhttp3;
    requires javafx.media;
    requires com.azure.storage.blob;

    opens Student.mainApp to javafx.fxml;
    exports Student.mainApp;

    opens Student.inbox to javafx.fxml;
    exports Student.inbox;

    opens Student.loginAndSignUp to javafx.fxml;
    exports Student.loginAndSignUp;

    opens Student.HomeAndNavigation to javafx.fxml;
    exports Student.HomeAndNavigation;

    opens Student.dashboard to javafx.fxml;
    exports Student.dashboard;

    opens Student.roadmap to javafx.fxml;
    exports Student.roadmap;

    opens Student.task to javafx.fxml;
    exports Student.task;

    exports Student.inbox.pChat;
    opens Student.inbox.pChat to javafx.fxml;

    exports Student.inbox.gChat;
    opens Student.inbox.gChat to javafx.fxml;

    exports Student.swing to javafx.graphics;

    exports Teacher.inbox.pChat;
    opens Teacher.inbox.pChat to javafx.fxml;

    exports Student.payment;
    opens Student.payment to javafx.fxml;

    exports Student.learningPage;
    opens Student.learningPage to javafx.fxml;

    exports Student.myCourse;
    opens Student.myCourse to javafx.fxml;

    exports Student.Setting to javafx.fxml;
    opens Student.Setting;

    exports Student.navBarAndSearchbar to javafx.fxml;
    opens Student.navBarAndSearchbar;

    exports Student.courseManage to javafx.fxml;
    opens Student.courseManage;

    exports Student.test to javafx.fxml;
    opens Student.test;

   exports Teacher.dashboard to javafx.fxml;
    opens Teacher.dashboard;

    exports Teacher.navigator to javafx.fxml;
    opens Teacher.navigator;

    exports Student.leaderboard to javafx.fxml;
    opens Student.leaderboard;

}