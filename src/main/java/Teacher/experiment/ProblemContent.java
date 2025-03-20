package Teacher.experiment;

import com.beust.ah.A;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProblemContent implements Initializable {

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

    @FXML
    private ImageView save;

    @FXML
    private HBox problemContent;

    private int count = 0;
    private ToggleGroup group;
    private QuizItem quizItem;
    private ArrayList<TextField> txtGroup;
    private ArrayList<QuizItem> itm;
    private VBox parentContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChoice();
        addImage();
        saveButton();
        deleteButton();

        txtGroup = new ArrayList<TextField>();
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

                txtGroup.add(choiceMessage);

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

    public void saveButton(){
        save.setOnMouseClicked(mouseEvent -> {
            this.setQuizItem();
            itm.add(this.getQuizItem());

            save.setVisible(false);
        });
    }

    public void setQuizItem(){
        String p = problem.getText();
        int po;
        if (point.getText().isEmpty()){
            po = 0;
        } else {
            po = Integer.parseInt(point.getText());
        }
        String corr = correctAns.getText();
        quizItem = new QuizItem(p, po, corr);
        for (TextField t: txtGroup){
            quizItem.setChoices(t.getText());
        }
    }

    public void deleteButton(){
        delete.setOnMouseClicked(mouseEvent -> {
            itm.remove(this.quizItem);

            if (parentContainer != null && problemContent != null) {
                parentContainer.getChildren().remove(problemContent);
            }
        });
    }

    public QuizItem getQuizItem(){
        return this.quizItem;
    }

    public void recieveQuizItemList(ArrayList<QuizItem> itm){
        this.itm = itm;
    }

    public void setParentContainer(VBox parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setProblemContent(HBox problemContent) {
        this.problemContent = problemContent;
    }

}
