package Server.inbox.pChat;

import Database.*;
import client.inbox.pChat.pChatController;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import Server.inbox.Server;

import java.net.ServerSocket;
import java.net.URL;
import java.util.*;

public class pChatServerController extends pChatController implements Initializable {
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

    @FXML
    private ImageView currStudentImg;

    @FXML
    private Label currStudentName;

    @FXML
    private Button myCourse;

    @FXML
    private HBox globalButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox privateBar;

    @FXML
    private VBox pChatDisplay;

    private Map<String, List<HBox>> chatHistory = new HashMap<>();
    private String selectedStudent;

    private Server server;
    private StudentDBConnect stdDb;
    private ChatHistoryDB chatHistoryDB;
    private TeacherDBConnect teacherDBConnect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        switchToGlobal();

        spMain.vvalueProperty().bind(vboxMessage.heightProperty());
        myCourse.setStyle("-fx-background-color: linear-gradient(to right, #0675DE, #033F78); " +
                "-fx-background-radius: 15px 15px 15px 15px;");

        stdDb = new StudentDBConnect();
        chatHistoryDB = new ChatHistoryDB();
        teacherDBConnect = new TeacherDBConnect();
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
                currStudentName.setText(selectedStudent);
                currStudentImg.setImage(new Image(getClass().getResource("/img/Profile/user.png").toExternalForm()));

                int teacherId = teacherDBConnect.getTeacherId(server.getTeacherName());
                int studentId = stdDb.getStudentID(selectedStudent);

                loadChatHistoryFromDB(teacherId, studentId);
            }
        });


        sendButton.setOnAction(event -> {
            sendMessage();

            int teacherId = teacherDBConnect.getTeacherId(server.getTeacherName());
            int studentId = stdDb.getStudentID(selectedStudent);
            loadChatHistoryFromDB(teacherId, studentId);
        });

        tfMessage.setOnAction(event -> sendMessage());

    }

    private void sendMessage() {
        if (selectedStudent != null && !tfMessage.getText().isEmpty()) {
            String messageToSend = tfMessage.getText();
            addMessage(messageToSend, Pos.CENTER_RIGHT, "#81C2FF");
            server.sendMessageToStudent(selectedStudent, messageToSend);

            int sender_id = teacherDBConnect.getTeacherId(server.getTeacherName());
            int receive_id = stdDb.getStudentID(selectedStudent);
            chatHistoryDB.saveChatMessage(sender_id, "teacher", receive_id, "student", messageToSend);

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

    public void addStudent(String studentName) {
        Platform.runLater(() -> {
            if (!studentList.getItems().contains(studentName)) {
                studentList.getItems().add(studentName);
            }
        });
    }

    private void loadStudentList() {
        ArrayList<String> stdList = stdDb.getStudentNames();
        ObservableList<String> observableList = FXCollections.observableArrayList(stdList);
        studentList.setItems(observableList);
    }

    public void receiveMessage(String studentName, String message) {
        Platform.runLater(() -> {
            if (selectedStudent != null && selectedStudent.equals(studentName)) {
                addMessage(message, Pos.CENTER_LEFT, "#D9D9D9");
            }
            chatHistory.computeIfAbsent(studentName, k -> new ArrayList<>()).add(createMessageBox(message, Pos.CENTER_LEFT, "#D9D9D9"));
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

    private void loadChatHistoryFromDB(int teacherId, int studentId) {
        vboxMessage.getChildren().clear();

        List<Map<String, String>> messages = chatHistoryDB.getAllMessages(teacherId, studentId);

        for (Map<String, String> msgData : messages) {
            int senderId = Integer.parseInt(msgData.get("sender_id"));
            String msgText = msgData.get("message_text");

            if (senderId == teacherId) {
                addMessage(msgText, Pos.CENTER_RIGHT, "#81C2FF");
            } else {
                addMessage(msgText, Pos.CENTER_LEFT, "#D9D9D9");
            }
        }
    }

}
