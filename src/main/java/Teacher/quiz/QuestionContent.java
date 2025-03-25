package Teacher.quiz;

import Database.QuestionDB;
import Database.QuizDB;
import Database.ChoicesDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private QuizController quizController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChoice();
        addImage();
        saveButton();
        deleteButton();

        shadow();

        txtGroup = new ArrayList<TextField>();
    }

    public void addChoice(){
        group = new ToggleGroup();
        addChoice.setOnMouseClicked(mouseEvent -> {

            if (choiceSpace.getChildren().size() < 6){
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

            }
        });
    }

    public void setQuestionID(int questionID){
        this.questionID = questionID;
    }

    public void setQuizController(QuizController quizController){
        this.quizController = quizController;
    }

    public HBox getProblemContent(){
        return this.problemContent;
    }

    public QuestionItem getQuestionItem(){
        return this.questionItem;
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

            if (!isReadyToSave()) {
                return;
            }

            int quiz_id = quizDB.getLatestQuizID();
            String question = problem.getText();
            String corr = correctAns.getText();
            int point = Integer.parseInt(this.point.getText());

            questionID = questionDB.saveQuestion(quiz_id, question, corr, point, imgUrl);
            saveChoices();

            save.setVisible(false);

            itm.add(this.getQuizItem());
            this.setQuizItem();
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
            quizController.removeQuestion(this);

            System.out.println(questionID);

            if (parentContainer != null && problemContent != null) {
                parentContainer.getChildren().remove(problemContent);
            }
        });
    }

    private boolean isReadyToSave() {
        String questionText = problem.getText().trim();
        String pointText = point.getText().trim();
        String correctAnswer = correctAns.getText().trim();

        if (questionText.isEmpty()) {
            showAlert("", "กรุณากรอกคำถาม", Alert.AlertType.ERROR);
            return false;
        }

        if (txtGroup.size() < 2) {
            showAlert("", "ต้องมีตัวเลือกอย่างน้อย 2 ตัวเลือก", Alert.AlertType.ERROR);
            return false;
        }

        int pointValue;
        try {
            pointValue = Integer.parseInt(pointText);
            if (pointValue < 0) {
                showAlert("", "คะแนนต้องเป็นค่ามากกว่าหรือเท่ากับ 0", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("", "กรุณากรอกคะแนนเป็นตัวเลข", Alert.AlertType.ERROR);
            return false;
        }

        if (correctAnswer.isEmpty()) {
            showAlert("", "กรุณากรอกคำตอบที่ถูกต้อง", Alert.AlertType.ERROR);
            return false;
        }

        boolean isCorrectInChoices = false;
        for (TextField choice : txtGroup) {
            if (correctAnswer.equals(choice.getText().trim())) {
                isCorrectInChoices = true;
                break;
            }
        }
        if (!isCorrectInChoices) {
            showAlert("", "คำตอบที่ถูกต้องต้องอยู่ในตัวเลือก", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void shadow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(2.5);
        dropShadow.setOffsetY(2.5);
        dropShadow.setColor(Color.GRAY);

        problemContent.setEffect(dropShadow);
    }

    public void setDisplay(String name, int point, String corrAns, ArrayList<String> choices){
        problem.setText("");
        this.point.setText("");
        correctAns.setText("");
        choiceSpace.getChildren().clear();
        txtGroup.clear();
        group = new ToggleGroup();

        problem.setText(name);
        this.point.setText(point + "");
        correctAns.setText(corrAns);

        for (String c : choices){
            HBox choice = new HBox(10);
            RadioButton radioButton = new RadioButton();
            TextField choiceMessage = new TextField(c);

            choice.setAlignment(Pos.CENTER_LEFT);
            choiceMessage.setPrefWidth(300);
            radioButton.setText("");
            radioButton.setToggleGroup(group);

            choice.getChildren().addAll(radioButton, choiceMessage);

            choiceSpace.getChildren().add(choice);

            txtGroup.add(choiceMessage);
        }
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
