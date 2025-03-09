package Teacher.experiment;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class problemContent implements Initializable {

    @FXML
    private TextField problem;

    @FXML
    private TextField point;

    @FXML
    private TextField correctAns;

    @FXML
    private Label addChoice;

    @FXML
    private ImageView delete;

    @FXML
    private Button addImg;

    @FXML
    private VBox choiceSpace;

    @FXML
    private ImageView img;

    private int count = 0;
    private ToggleGroup group;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChoice();
        addImage();
    }

    public void addChoice(){
        group = new ToggleGroup();
        addChoice.setOnMouseClicked(mouseEvent -> {

            if (count < 6){
                HBox choice = new HBox(10);
                RadioButton radioButton = new RadioButton();
                TextField choiceMessage = new TextField();

                choice.setAlignment(Pos.CENTER_LEFT);
                choiceMessage.setPrefWidth(300);
                radioButton.setText("");
                radioButton.setToggleGroup(group);

                choice.getChildren().addAll(radioButton, choiceMessage);

                choiceSpace.getChildren().add(choice);

                count++;
            }
        });
    }

    public void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        addImg.setOnMouseClicked(mouseEvent -> {
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                img.setImage(image);
            } else {
                System.out.println("No file selected.");
            }
        });
    }

}
