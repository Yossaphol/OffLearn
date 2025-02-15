package com.example.offlearn.pChat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class pChatController implements Initializable {

    @FXML
    private Button sendButton;

    @FXML
    private TextField tfMessage;

    @FXML
    private VBox vboxMessage;

    @FXML
    private ScrollPane spMain;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spMain.vvalueProperty().bind(vboxMessage.heightProperty());

        String teacherIP = "127.0.0.1"; // เปลี่ยนเป็น IP ครู
        int teacherPort = 5678;
        String studentName = "Student1"; // เปลี่ยนชื่อเป็นชื่อจริง

        client = new Client(teacherIP, teacherPort, studentName);

        sendButton.setOnAction(event -> {
            String messageToSend = tfMessage.getText();
            if (!messageToSend.isEmpty()) {
                addMessage(messageToSend, Pos.CENTER_RIGHT, "#DB9DFF");
                client.sendMessage(messageToSend);
                tfMessage.clear();
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
}
