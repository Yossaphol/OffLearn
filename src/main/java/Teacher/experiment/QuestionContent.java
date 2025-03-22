package Teacher.experiment;

import Database.QuestionDB;
import Database.QuizDB;
import Database.ChoicesDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import mediaUpload.MediaUpload;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuestionContent implements Initializable {

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
    private QuestionItem questionItem;
    private ArrayList<TextField> txtGroup;
    private ArrayList<QuestionItem> itm;
    private VBox parentContainer;
    private MediaUpload m;
    private QuestionDB questionDB;
    private String imgUrl;
    private QuizDB quizDB;
    private int questionID;
    private ChoicesDB choicesDB;

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
        m = new MediaUpload();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        addImg.setOnMouseClicked(mouseEvent -> {
            File selectedFile = fileChooser.showOpenDialog(null);
            imgUrl = m.uploadImg(selectedFile);

            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                img.setImage(image);
            } else {
                System.out.println("No file selected.");
            }
        });
    }

    public void saveButton(){
        questionDB = new QuestionDB();
        quizDB = new QuizDB();

        save.setOnMouseClicked(mouseEvent -> {
            this.setQuizItem();
            itm.add(this.getQuizItem());

            int quiz_id = quizDB.getLatestQuizID();
            String question = problem.getText();
            String corr = correctAns.getText();
            int point = Integer.parseInt(this.point.getText());

            questionID = questionDB.saveQuestion(quiz_id, question, corr, point, imgUrl);
            saveChoices();

            save.setVisible(false);
        });
    }

    public void saveChoices(){
        choicesDB = new ChoicesDB();
        for (TextField t : txtGroup){
            choicesDB.saveChoices( this.questionID, t.getText());
        }
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
        questionItem = new QuestionItem(p, po, corr);
        for (TextField t: txtGroup){
            questionItem.setChoices(t.getText());
        }
    }

    public void deleteButton(){
        questionDB = new QuestionDB();

        delete.setOnMouseClicked(mouseEvent -> {
            itm.remove(this.questionItem);
            questionDB.deleteQuestion(questionID);

            if (parentContainer != null && problemContent != null) {
                parentContainer.getChildren().remove(problemContent);
            }
        });
    }

    public QuestionItem getQuizItem(){
        return this.questionItem;
    }

    public void recieveQuizItemList(ArrayList<QuestionItem> itm){
        this.itm = itm;
    }

    public void setParentContainer(VBox parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setProblemContent(HBox problemContent) {
        this.problemContent = problemContent;
    }

}
