package client.inbox.pChat;

import client.FontLoader.FontLoader;
import client.HomeAndNavigation.Navigator;
import client.inbox.Client;
import client.inbox.DataBase.StudentsDBConnect;
import client.inbox.DataBase.TeacherDBConnect;
import client.inbox.DataBase.ChatHistoryDB;
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

import java.io.IOException;
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

    @FXML
    private HBox MainFrame;

    @FXML
    public HBox dashboard;
    public HBox home;
    public HBox course;
    public HBox inbox;
    public HBox task;
    public HBox roadmap;
    public BorderPane borderPane;
    public VBox privateBar;
    public VBox pChatDisplay;
    public HBox globalButton;

    private Map<String, List<HBox>> chatHistory = new HashMap<>();
    private Map<String, Client> clientMap = new HashMap<>();
    private String selectedTeacher;
    private TeacherDBConnect teacherDb;
    private ChatHistoryDB chatHistoryDB;
    private StudentsDBConnect studentsDBConnect;
    private String studentName = "StudentTest";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        route();
        switchToGlobal();

        hoverEffect(dashboard);
        hoverEffect(home);
        hoverEffect(course);
        hoverEffect(inbox);
        hoverEffect(task);
        hoverEffect(roadmap);

        spMain.vvalueProperty().bind(vboxMessage.heightProperty());
        allCourse.setStyle("-fx-background-color: linear-gradient(to bottom, #410066, #8100CC);" +
                "-fx-background-radius: 10px 10px 10px 10px;");
        myCourse.setStyle("-fx-background-color: linear-gradient(to right, #410066, #8100CC);" +
                "-fx-background-radius: 15px 15px 15px 15px;");

        teacherDb = new TeacherDBConnect();
        chatHistoryDB = new ChatHistoryDB();
        studentsDBConnect = new StudentsDBConnect();
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


    public void switchToGlobal() {
        globalButton.setOnMouseEntered(mouseEvent -> globalButton.setOpacity(0.5));
        globalButton.setOnMouseExited(mouseEvent -> globalButton.setOpacity(1));

        globalButton.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader globalRoute = new FXMLLoader(getClass().getResource("/fxml/client/inbox/routeToPChat.fxml"));
                FXMLLoader globalChatDisplay = new FXMLLoader(getClass().getResource("/fxml/client/inbox/gChat.fxml"));

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

    public void hoverEffect(HBox hBox){
        hBox.setOnMouseEntered(mouseEvent -> hBox.setOpacity(0.3));
        hBox.setOnMouseExited(mouseEvent -> hBox.setOpacity(1));
    }

    public void route(){
        Navigator nav = new Navigator();
        dashboard.setOnMouseClicked(nav::dashboardRoute);
        home.setOnMouseClicked(nav::homeRoute);
        course.setOnMouseClicked(nav::courseRoute);
        inbox.setOnMouseClicked(nav::inboxRoute);
        task.setOnMouseClicked(nav::taskRoute);
        roadmap.setOnMouseClicked(nav::roadmapRoute);
    }

    public void switchTeacher(String newTeacher) {
        String oldTeacher = selectedTeacher;
        selectedTeacher = newTeacher;

        if (oldTeacher != null && clientMap.containsKey(oldTeacher)) {
            clientMap.get(oldTeacher).closeConnection();
        }

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
                int senderId = studentsDBConnect.getStudentID(studentName);
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

        int studentId = studentsDBConnect.getStudentID(studentName);
        int teacherId = teacherDb.getTeacherId(teacherName);

        List<String[]> sentMessages = chatHistoryDB.getSentMessages(studentId, teacherId);
        List<String[]> receivedMessages = chatHistoryDB.getReceivedMessages(studentId, teacherId);

        List<String[]> allMessages = new ArrayList<>();
        for (String[] msg : sentMessages) {
            allMessages.add(new String[]{"student", msg[0], msg[1]});
        }
        for (String[] msg : receivedMessages) {
            allMessages.add(new String[]{"teacher", msg[0], msg[1]});
        }

        allMessages.sort(Comparator.comparing(msg -> msg[2]));

        Platform.runLater(() -> {
            for (String[] msgData : allMessages) {
                String senderType = msgData[0];
                String message = msgData[1];

                if (senderType.equals("student")) {
                    addMessage(message, Pos.CENTER_RIGHT, "#DB9DFF");
                } else {
                    addMessage(message, Pos.CENTER_LEFT, "#D9D9D9");
                }
            }
        });
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
