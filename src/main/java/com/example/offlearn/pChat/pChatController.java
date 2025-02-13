package com.example.offlearn.pChat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
