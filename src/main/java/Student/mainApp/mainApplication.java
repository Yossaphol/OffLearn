package Student.mainApp;

import Student.HomeAndNavigation.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class mainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Navigator navigator = new Navigator();
      //  navigator.navigateTo("/fxml/Student/HomePage/home.fxml", primaryStage);
    }
}
