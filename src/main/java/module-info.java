module com.example.offlearn {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.xml.dom;
    requires jdk.compiler;
    requires jdk.dynalink;
    requires io.github.cdimascio.dotenv.java;
    requires com.google.zxing.javase;
    requires com.google.zxing;
    requires org.json;
    requires stripe.java;
    requires okhttp3;
    requires javafx.media;
    requires com.azure.storage.blob;
    requires com.azure.core;
    requires jcommander;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.s3;
    requires javafx.swing;
    requires com.formdev.flatlaf;
    requires com.google.gson;
    requires java.sql;

    opens Student.inbox to javafx.fxml;
    exports Student.inbox;

    opens Utili to com.google.gson;

    opens Student.leaderboard to javafx.fxml;
    exports Student.leaderboard;

    opens Student.loginAndSignUp to javafx.fxml;
    exports Student.loginAndSignUp;

    opens Student.HomeAndNavigation to javafx.fxml;
    exports Student.HomeAndNavigation;

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

    exports Student.Quiz to javafx.fxml;
    opens Student.Quiz;

    exports Teacher.dashboard to javafx.fxml;
    opens Teacher.dashboard;

    exports Teacher.navigator to javafx.fxml;
    opens Teacher.navigator;

    exports Teacher.somethingWithVideo to javafx.fxml;
    opens Teacher.somethingWithVideo;

    exports Teacher.quizDetail to javafx.fxml;
    opens Teacher.quizDetail;

    exports Teacher.setting to javafx.fxml;
    opens Teacher.setting;

    exports Teacher.quiz to javafx.fxml;
    opens Teacher.quiz;

    exports Student.mainPage to javafx.fxml;
    opens Student.mainPage;

    exports Teacher.videoDetail to javafx.fxml;
    opens Teacher.videoDetail;

    exports Teacher.courseManagement to javafx.fxml;
    opens Teacher.courseManagement;

    exports Teacher.LoginAndSignup to javafx.fxml;
    opens Teacher.LoginAndSignup;

    exports Teacher.showBalance to javafx.fxml;
    opens Teacher.showBalance;
    opens Student.dashboard;

    exports mediaUpload to javafx.fxml;
    opens mediaUpload;
    exports Student.Offline;
    opens Student.Offline to javafx.fxml;

}