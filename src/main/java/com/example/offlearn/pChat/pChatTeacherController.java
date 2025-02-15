package com.example.offlearn.pChat;

import com.example.offlearn.pChat.DataBase.StudentDBConnect;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.ServerSocket;
import java.net.URL;
import java.util.*;

public class pChatTeacherController implements Initializable {

    @FXML
    private Button sendButton;

    @FXML
    private TextField tfMessage;

    @FXML
    private VBox vboxMessage;


    @FXML
    private ScrollPane spMain;

    @FXML
    private ListView<String> studentList;

    private Map<String, VBox> studentChatBoxes = new HashMap<>();
    private String selectedStudent;

    private Server server;
    private StudentDBConnect stdDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spMain.vvalueProperty().bind(vboxMessage.heightProperty());

        stdDb = new StudentDBConnect();
        loadStudentList();

        try {
            server = new Server(new ServerSocket(5678), this);
            new Thread(server::startServer).start();
        } catch (Exception e) {
            System.out.println("Error creating teacher server.");
        }

        studentList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(String student, boolean empty) {
                super.updateItem(student, empty);
                if (empty || student == null) {
                    setGraphic(null);
                } else {
                    setGraphic(createStudentBox(student));
                }
            }
        });

        studentList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedStudent = newValue;
                vboxMessage.getChildren().clear();
                System.out.println("Selected student: " + selectedStudent);
            }
        });

        sendButton.setOnAction(event -> {
            if (selectedStudent != null) {
                String messageToSend = tfMessage.getText();
                if (!messageToSend.isEmpty()) {
                    addMessage(messageToSend, Pos.CENTER_RIGHT, "#DB9DFF");
                    server.sendMessageToStudent(selectedStudent, messageToSend);
                    tfMessage.clear();
                }
            } else {
                System.out.println("กรุณาเลือกนักเรียนก่อนส่งข้อความ!");
            }
        });
    }

    public void addMessage(String message, Pos position, String color) {
        HBox hBox = new HBox();
        hBox.setAlignment(position);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 20px; -fx-font-size: 16px;");
        textFlow.setPadding(new Insets(15, 15, 15, 15));

        hBox.getChildren().add(textFlow);
        Platform.runLater(() -> vboxMessage.getChildren().add(hBox));
    }

    public void addStudent(String studentName) {
        Platform.runLater(() -> studentList.getItems().add(studentName));
    }

    public void removeStudent(String studentName) {
        Platform.runLater(() -> studentList.getItems().remove(studentName));
    }

    private void loadStudentList() {
        ArrayList<String> stdList = stdDb.getStdName();
        ObservableList<String> observableList = FXCollections.observableArrayList(stdList);
        studentList.setItems(observableList);
    }

    private VBox createStudentBox(String studentName) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));
        hBox.setAlignment(Pos.CENTER_LEFT);

        Image image = new Image(getClass().getResource("/img/Profile/user.png").toExternalForm());
        ImageView profilePic = new ImageView(image);
        profilePic.setFitHeight(50);
        profilePic.setFitWidth(50);

        Label nameLabel = new Label(studentName);
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        hBox.getChildren().addAll(profilePic, nameLabel);

        VBox vBox = new VBox(hBox);
        vBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px;");
        vBox.setAlignment(Pos.CENTER_LEFT);

        return vBox;
    }
}
