package Student.payment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class payment extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(payment.class.getResource("/fxml/Student/payment/payment.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);


        stage.setTitle("Offlearn Client");
        stage.setScene(scene);
        stage.show();
    }
}


