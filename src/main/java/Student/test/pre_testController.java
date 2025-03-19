package Student.test;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Student.roadmap.myRoadmapCard;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class pre_testController implements Initializable {


    public Label pretestDescription;
    public VBox subject_card;
    public Button do_button_text;
    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayCard();
        setEffect();
        route();
        pretestDescription.setText("1. Don't\n2. Do\n3. But...");
    }

    private void setEffect(){
        ef.hoverEffect(do_button_text);
    }

    private void route(){
        Navigator nav = new Navigator();
        do_button_text.setOnMouseClicked(nav::testRoute);
    }

    private void displayCard(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/test/preTestCard.fxml"));
            VBox searchbarContent = loader.load();
            preTestCardController controller = loader.getController();
            //Set detail
            controller.setTeacherName("Wirayabovorn B.");
            controller.setHardness("ปานกลาง");
            controller.setPreTestName("Machine learning");
            controller.setPretestDuration(120);
            controller.setAmount(70);
            controller.setTeacherExpertise("Programming");
            controller.setTeacherPic("/img/Profile/man.png");


            subject_card.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
