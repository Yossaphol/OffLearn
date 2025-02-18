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

    @FXML
    private Button allCourse;

    @FXML
    private Button myCourse;

    @FXML
    private HBox currentTeacher;

    @FXML
    private ImageView currentTeacherImg;

    @FXML
    private Label currentTeacherName;

    private Map<String, List<HBox>> chatHistory = new HashMap<>();
    private Map<String, Client> clientMap = new HashMap<>();
    private String selectedTeacher;
    private TeacherDBConnect teacherDb;
    private String studentName = "StudentTest";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spMain.vvalueProperty().bind(vboxMessage.heightProperty());
        allCourse.setStyle("-fx-background-color: linear-gradient(to bottom, #410066, #8100CC);" +
                "-fx-background-radius: 10px 10px 10px 10px;");
        myCourse.setStyle("-fx-background-color: linear-gradient(to right, #410066, #8100CC);" +
                "-fx-background-radius: 15px 15px 15px 15px;");

        teacherDb = new TeacherDBConnect();
        loadTeacherList();

        vboxMessage.getChildren().clear();

        teacherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switchTeacher(newValue);
            }
        });

        sendButton.setOnAction(event -> sendMessage());
        tfMessage.setOnAction(event -> sendMessage());

    }

    private void switchTeacher(String newTeacher) {
        if (selectedTeacher != null && clientMap.containsKey(selectedTeacher)) {
            clientMap.get(selectedTeacher).closeConnection();
        }

        selectedTeacher = newTeacher;
        String teacherIP = teacherDb.getTeacherIP(selectedTeacher);
        int teacherPort = teacherDb.getTeacherPort(selectedTeacher);

        Client client = new Client(teacherIP, teacherPort, studentName);
        client.setMessageListener(this::receiveMessage);

        clientMap.put(selectedTeacher, client);

        Platform.runLater(() -> {

            currentTeacherName.setText(selectedTeacher);
            currentTeacherImg.setImage(new Image(getClass().getResource("/img/Profile/user.png").toExternalForm()));

            vboxMessage.getChildren().clear();
            loadChatHistory(selectedTeacher);
        });
    }

    public void receiveMessage(String message) {
        if (selectedTeacher == null) {
            System.out.println("Don't selected");
            return;
        }

        Platform.runLater(() -> addMessage(message, Pos.CENTER_LEFT, "D9D9D9"));
    }

    private void sendMessage() {
        if (selectedTeacher != null) {
            String messageToSend = tfMessage.getText();
            if (!messageToSend.isEmpty()) {
                addMessage(messageToSend, Pos.CENTER_RIGHT, "#DB9DFF");

                Client client = clientMap.get(selectedTeacher);
                if (client != null) {
                    client.sendMessage(messageToSend);
                } else {
                    System.out.println("Teacher is offline, storing message.");
                }

                Platform.runLater(() -> tfMessage.clear());
            }
        } else {
            System.out.println("Please select a teacher");
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

        chatHistory.computeIfAbsent(selectedTeacher, k -> new ArrayList<>()).add(hBox);

        Platform.runLater(() -> vboxMessage.getChildren().add(hBox));
    }

    private void loadChatHistory(String teacherName) {
        Platform.runLater(() -> {
            vboxMessage.getChildren().clear();
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

        teacherList.setCellFactory(listView -> new ListCell<String>() {
            private final HBox content;
            private final ImageView profilePic;
            private final Label nameLabel;

            {
                profilePic = new ImageView(new Image(getClass().getResource("/img/Profile/teacher.png").toExternalForm()));
                profilePic.setFitHeight(40);
                profilePic.setFitWidth(40);

                nameLabel = new Label();
                nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                content = new HBox(10, profilePic, nameLabel);
                content.setAlignment(Pos.CENTER_LEFT);
                content.setPadding(new Insets(5));
            }

            @Override
            protected void updateItem(String teacherName, boolean empty) {
                super.updateItem(teacherName, empty);
                if (empty || teacherName == null) {
                    setGraphic(null);
                } else {
                    nameLabel.setText(teacherName);
                    setGraphic(content);
                }
            }
        });
    }
}
