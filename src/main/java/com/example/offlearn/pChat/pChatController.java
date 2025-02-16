package com.example.offlearn.pChat;

import com.example.offlearn.pChat.DataBase.TeacherDBConnect;
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

import java.net.URL;
import java.util.*;

public class pChatController implements Initializable {

    @FXML
    private Button sendButton;

    @FXML
    private TextField tfMessage;

    @FXML
    private VBox vboxMessage;

    @FXML
    private ScrollPane spMain;

    @FXML
    private ListView<String> teacherList;

    private Map<String, List<HBox>> chatHistory = new HashMap<>();
    private String selectedTeacher;

    private Client client;
    private TeacherDBConnect teacherDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spMain.vvalueProperty().bind(vboxMessage.heightProperty());

        teacherDb = new TeacherDBConnect();
        loadTeacherList();

        String teacherIP = "127.0.0.1";
        int teacherPort = 5678;
        String studentName = "Student1";

        client = new Client(teacherIP, teacherPort, studentName);

        teacherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTeacher = newValue;

                // ล้างแชทก่อนหน้า และโหลดแชทของอาจารย์ที่เลือกใหม่
                Platform.runLater(() -> {
                    vboxMessage.getChildren().clear();
                    loadChatHistory(selectedTeacher);
                });

                System.out.println("Selected teacher: " + selectedTeacher);
            }
        });

        teacherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTeacher = newValue;
                loadChatHistory(selectedTeacher);
                System.out.println("Selected teacher: " + selectedTeacher);
            }
        });

        sendButton.setOnAction(event -> {
            if (selectedTeacher != null) {
                String messageToSend = tfMessage.getText();
                if (!messageToSend.isEmpty()) {
                    addMessage(messageToSend, Pos.CENTER_RIGHT, "#DB9DFF");
                    client.sendMessage(messageToSend);

                    // ใช้ Platform.runLater เพื่อให้แน่ใจว่า TextField ถูกเคลียร์
                    Platform.runLater(() -> tfMessage.clear());
                }
            } else {
                System.out.println("กรุณาเลือกอาจารย์ก่อนส่งข้อความ!");
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

        // บันทึกประวัติแชท
        chatHistory.computeIfAbsent(selectedTeacher, k -> new ArrayList<>()).add(hBox);

        Platform.runLater(() -> vboxMessage.getChildren().add(hBox));
    }

    private void loadChatHistory(String teacherName) {
        Platform.runLater(() -> {
            vboxMessage.getChildren().clear(); // ล้างแชทเก่า
            List<HBox> history = chatHistory.getOrDefault(teacherName, new ArrayList<>());
            vboxMessage.getChildren().addAll(history);
        });
    }

    public void addTeacher(String teacherName) {
        Platform.runLater(() -> teacherList.getItems().add(teacherName));
    }

    public void removeTeacher(String teacherName) {
        Platform.runLater(() -> teacherList.getItems().remove(teacherName));
    }

    private void loadTeacherList() {
        ArrayList<String> teacherNames = teacherDb.getTeacherName();
        ObservableList<String> observableList = FXCollections.observableArrayList(teacherNames);
        teacherList.setItems(observableList);
    }

    private VBox createTeacherBox(String teacherName) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));
        hBox.setAlignment(Pos.CENTER_LEFT);

        Image image = new Image(getClass().getResource("/img/Profile/teacher.png").toExternalForm());
        ImageView profilePic = new ImageView(image);
        profilePic.setFitHeight(50);
        profilePic.setFitWidth(50);

        Label nameLabel = new Label(teacherName);
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        hBox.getChildren().addAll(profilePic, nameLabel);

        VBox vBox = new VBox(hBox);
        vBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px;");
        vBox.setAlignment(Pos.CENTER_LEFT);

        return vBox;
    }
}
