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

    private Map<String, List<HBox>> chatHistory = new HashMap<>();
    private String selectedStudent;

    private Server server;
    private StudentDBConnect stdDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spMain.vvalueProperty().bind(vboxMessage.heightProperty());

        stdDb = new StudentDBConnect();
        loadStudentList();

        vboxMessage.getChildren().clear();
        selectedStudent = null;

        studentList.setCellFactory(param -> new ListCell<String>() {
            private final HBox hBox = new HBox(10);
            private final ImageView profileImage = new ImageView();
            private final Label nameLabel = new Label();

            {
                profileImage.setFitHeight(50);
                profileImage.setFitWidth(50);
                hBox.getChildren().addAll(profileImage, nameLabel);
            }

            @Override
            protected void updateItem(String studentName, boolean empty) {
                super.updateItem(studentName, empty);

                if (empty || studentName == null) {
                    setGraphic(null);
                } else {
                    Image profilePic = new Image(getClass().getResourceAsStream("/img/Profile/user.png"));
                    profileImage.setImage(profilePic);

                    nameLabel.setText(studentName);
                    nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                    setGraphic(hBox);
                }
            }
        });

        try {
            server = new Server(new ServerSocket(5678), this);
            new Thread(server::startServer).start();
        } catch (Exception e) {
            System.out.println("Error creating teacher server.");
        }

        studentList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedStudent = newValue;
                vboxMessage.getChildren().clear();
                loadChatHistory(selectedStudent);
                System.out.println("Selected student: " + selectedStudent);
            }
        });


        sendButton.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        if (selectedStudent != null && !tfMessage.getText().isEmpty()) {
            String messageToSend = tfMessage.getText();
            addMessage(messageToSend, Pos.CENTER_RIGHT, "#DB9DFF");
            server.sendMessageToStudent(selectedStudent, messageToSend);
            tfMessage.clear();
        } else {
            System.out.println("Please select student");
        }
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
        chatHistory.computeIfAbsent(selectedStudent, k -> new ArrayList<>()).add(hBox);

        Platform.runLater(() -> vboxMessage.getChildren().add(hBox));
    }

    private void loadChatHistory(String studentName) {
        Platform.runLater(() -> {
            vboxMessage.getChildren().clear();
            List<HBox> history = chatHistory.getOrDefault(studentName, new ArrayList<>());
            vboxMessage.getChildren().addAll(history);
        });
    }

    public void addStudent(String studentName) {
        Platform.runLater(() -> {
            if (!studentList.getItems().contains(studentName)) {
                studentList.getItems().add(studentName);
            }
        });
    }

    private void loadStudentList() {
        ArrayList<String> stdList = stdDb.getStdName();
        ObservableList<String> observableList = FXCollections.observableArrayList(stdList);
        studentList.setItems(observableList);
    }

    public void receiveMessage(String studentName, String message) {
        Platform.runLater(() -> {
            if (selectedStudent != null && selectedStudent.equals(studentName)) {
                addMessage(message, Pos.CENTER_LEFT, "#9FE2BF");
            }
            chatHistory.computeIfAbsent(studentName, k -> new ArrayList<>()).add(createMessageBox(message, Pos.CENTER_LEFT, "#9FE2BF"));
        });
    }

    private HBox createMessageBox(String message, Pos position, String color) {
        HBox hBox = new HBox();
        hBox.setAlignment(position);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 20px; -fx-font-size: 16px;");
        textFlow.setPadding(new Insets(15, 15, 15, 15));

        hBox.getChildren().add(textFlow);
        return hBox;
    }

    public ObservableList<String> getStudentList() {
        return studentList.getItems();
    }

}
