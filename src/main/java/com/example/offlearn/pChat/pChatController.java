package com.example.offlearn.pChat;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class pChatController implements Initializable {

    @FXML
    private Button allCourse;

    @FXML
    private Button myCourse;

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
        myCourse.setStyle("-fx-background-color: linear-gradient(to right, #4D0079, #8100CC); -fx-background-radius: 15;");
        allCourse.setStyle("-fx-background-color: linear-gradient(to right, #4D0079, #8100CC); -fx-background-radius: 15;");

        try{
            client = new Client(new Socket("localhost", 5678));
        } catch (IOException e){
            System.out.println("Error creating client.");
        }

        vboxMessage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                spMain.setVvalue((Double) newValue);
            }
        });

        client.receiveMessageFromServer(vboxMessage);

        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = tfMessage.getText();
                if (!messageToSend.isEmpty()){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-text-fill: #000000;" +
                            "-fx-background-color: #DB9DFF;" +
                            "-fx-background-radius: 20px;" +
                            "-fx-font-size: 16px;");

                    textFlow.setPadding(new Insets(15, 15, 15, 15));

                    hBox.getChildren().add(textFlow);
                    vboxMessage.getChildren().add(hBox);

                    client.sendMessageToServer(messageToSend);
                    tfMessage.clear();
                }
            }
        });
    }

    public static void addLabel(String msgFromServer,VBox vbox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233, 233, 235);" +
                "-fx-background-radius: 20px;" +
                "-fx-font-size: 16px;");

        textFlow.setPadding(new Insets(15, 15, 15, 15));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
    }
}
