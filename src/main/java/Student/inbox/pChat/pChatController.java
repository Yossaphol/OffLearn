package Student.inbox.pChat;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import Student.inbox.Client;
import Database.*;
import a_Session.SessionHadler;
import a_Session.SessionManager;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class pChatController implements Initializable, SessionHadler {

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
    private HBox currentTeacher;

    @FXML
    private ImageView currentTeacherImg;

    @FXML
    private Label currentTeacherName;


    @FXML
    public BorderPane borderPane;
    public VBox privateBar;
    public VBox pChatDisplay;
    public HBox globalButton;

    private Map<String, List<HBox>> chatHistory = new HashMap<>();
    private Map<String, Client> clientMap = new HashMap<>();
    private String selectedTeacher;
    private TeacherDBConnect teacherDb;
    private ChatHistoryDB chatHistoryDB;
    private StudentDBConnect studentDBConnect;
    private String studentName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleSession();
        switchToGlobal();

        spMain.vvalueProperty().bind(vboxMessage.heightProperty());
        teacherDb = new TeacherDBConnect();
        chatHistoryDB = new ChatHistoryDB();
        studentDBConnect = new StudentDBConnect();
        loadTeacherList();

        vboxMessage.getChildren().clear();

        teacherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switchTeacher(newValue);
            }
        });

        sendButton.setOnAction(event -> sendMessage());
        tfMessage.setOnAction(event -> sendMessage());


        setEffect();
    }

    public void setEffect(){
        hoverEffect(globalButton);
    }

    public void hoverEffect(HBox btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.05);
        scaleDown.setFromY(1.05);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
        });
    }

    @Override
    public void handleSession() {
        this.studentName = SessionManager.getInstance().getUsername();
    }


    public void switchToGlobal() {

        globalButton.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader globalRoute = new FXMLLoader(getClass().getResource("/fxml/Student/inbox/routeToPChat.fxml"));
                FXMLLoader globalChatDisplay = new FXMLLoader(getClass().getResource("/fxml/Student/inbox/gChat.fxml"));

                borderPane.setLeft(globalRoute.load());
                borderPane.setCenter(globalChatDisplay.load());

                globalButtonController controller = globalRoute.getController();

                controller.setPrivateButtonClickListener(this::switchBackToDefault);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void switchBackToDefault() {
        borderPane.setLeft(privateBar);
        borderPane.setCenter(pChatDisplay);
    }

    public void switchTeacher(String newTeacher) {
        try {
            String oldTeacher = selectedTeacher;
            selectedTeacher = newTeacher;

            if (oldTeacher != null && clientMap.containsKey(oldTeacher)) {
                clientMap.get(oldTeacher).closeConnection();
            }

            Map<String, String> teacherInfo = teacherDb.getTeacherInfo(selectedTeacher);
            if (teacherInfo == null) {
                showAlert("Error", "Teacher information not found for: " + selectedTeacher);
                return;
            }

            if (!teacherInfo.containsKey("IP") || !teacherInfo.containsKey("Port")) {
                showAlert("Error", "Incomplete teacher connection details");
                return;
            }

            String teacherIP = teacherInfo.get("IP");
            int teacherPort = Integer.parseInt(teacherInfo.get("Port"));

            Client client = new Client(teacherIP, teacherPort, studentName);
            client.setMessageListener(this::receiveMessage);

            clientMap.put(selectedTeacher, client);

            Platform.runLater(() -> {
                currentTeacherName.setText(selectedTeacher);
                currentTeacherImg.setImage(TEACHER_IMAGE);

                vboxMessage.getChildren().clear();
                loadChatHistory(selectedTeacher);
            });
        } catch (Exception e) {
            showAlert("Connection Error", "Failed to connect to teacher: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void receiveMessage(String message) {
        if (selectedTeacher == null) {
            System.out.println("Don't selected");
            return;
        }

        Platform.runLater(() -> {
            addMessage(message, Pos.CENTER_LEFT, "D9D9D9");
        });
    }

    private void sendMessage() {
        if (selectedTeacher != null) {
            String messageToSend = tfMessage.getText();

            if (!messageToSend.isEmpty()) {
                addMessage(messageToSend, Pos.CENTER_RIGHT, "#DB9DFF");

                Client client = clientMap.get(selectedTeacher);
                if (client != null) {
                    client.sendMessage(messageToSend);
                }

                int receiverId = teacherDb.getTeacherId(selectedTeacher);
                int senderId = studentDBConnect.getStudentID(studentName);
                chatHistoryDB.saveChatMessage(senderId, "student", receiverId, "teacher", messageToSend);

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
        if (selectedTeacher == null) return;

        vboxMessage.getChildren().clear();

        int studentId = studentDBConnect.getStudentID(studentName);
        int teacherId = teacherDb.getTeacherId(teacherName);

        List<Map<String, String>> allMessages = chatHistoryDB.getAllMessages(teacherId, studentId);

        Platform.runLater(() -> {
            for (Map<String, String> msgData : allMessages) {
                int senderId = Integer.parseInt(msgData.get("sender_id"));
                String message = msgData.get("message_text");

                String senderType = (senderId == studentId) ? "student" : "teacher";

                if (senderType.equals("student")) {
                    addMessage(message, Pos.CENTER_RIGHT, "#DB9DFF");
                } else {
                    addMessage(message, Pos.CENTER_LEFT, "#D9D9D9");
                }
            }
        });
    }


    private static final Image TEACHER_IMAGE = new Image(
            Objects.requireNonNull(pChatController.class.getResource("/img/Profile/teacher.png")).toExternalForm()
    );

    private void loadTeacherList() {
        List<String> teacherNames = teacherDb.getTeacherNames();
        ObservableList<String> observableList = FXCollections.observableArrayList(teacherNames);
        teacherList.setItems(observableList);

        teacherList.setCellFactory(listView -> new ListCell<>() {
            private final HBox content;
            private final ImageView profilePic;
            private final Label nameLabel;

            {
                profilePic = new ImageView(TEACHER_IMAGE);
                profilePic.setFitHeight(40);
                profilePic.setFitWidth(40);

                nameLabel = new Label();
                nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"+"-fx-cursor: hand;");

                content = new HBox(10, profilePic, nameLabel);
                content.setAlignment(Pos.CENTER_LEFT);
                content.setPadding(new Insets(5));
                hoverEffect(content);
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
