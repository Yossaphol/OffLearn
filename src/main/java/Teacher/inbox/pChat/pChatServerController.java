package Teacher.inbox.pChat;

import Database.*;
import Student.HomeAndNavigation.HomeController;
import Student.inbox.pChat.pChatController;
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
import Teacher.Server;
import javafx.util.Duration;
import javafx.scene.layout.Region;
import java.io.ByteArrayInputStream;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.*;

public class pChatServerController extends pChatController implements Initializable {
    public HBox navBarContainer;
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
    private Map<String, Integer> unreadMessageCounts = new HashMap<>();

    private String selectedStudent;

    private Server server;
    private StudentDBConnect stdDb;
    private ChatHistoryDB chatHistoryDB;
    private TeacherDBConnect teacherDBConnect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchToGlobal();

        spMain.vvalueProperty().bind(vboxMessage.heightProperty());

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
            private final Label unreadCountLabel = new Label();

            {
                profileImage.setFitHeight(50);
                profileImage.setFitWidth(50);

                unreadCountLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; " +
                        "-fx-background-radius: 10; -fx-padding: 2 5; " +
                        "-fx-font-weight: bold;");
                Region spacer = new Region();
                HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
                hBox.getChildren().addAll(profileImage, nameLabel, spacer, unreadCountLabel);
                hoverEffect(hBox);
                hBox.setStyle("-fx-cursor: hand;");
            }

            @Override
            protected void updateItem(String studentName, boolean empty) {
                super.updateItem(studentName, empty);
                if (empty || studentName == null) {
                    setGraphic(null);
                } else {
                    byte[] profileImageBytes = stdDb.getStudentProfilePicture(studentName);
                    Image profilePic;
                    if (profileImageBytes != null && profileImageBytes.length > 0) {
                        profilePic = new Image(new ByteArrayInputStream(profileImageBytes));
                    } else {
                        profilePic = new Image(getClass().getResourceAsStream("/img/Profile/user.png"));
                    }

                    Image squaredProfilePic = cropToSquare(profilePic);
                    profileImage.setImage(squaredProfilePic);
                    profileImage.setFitWidth(50);
                    profileImage.setFitHeight(50);
                    profileImage.setPreserveRatio(false);
                    profileImage.setSmooth(true);
                    profileImage.setCache(true);
                    Circle clip = new Circle(25, 25, 25);
                    profileImage.setClip(clip);
                    nameLabel.setText(studentName);
                    nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                    Integer unreadCount = unreadMessageCounts.getOrDefault(studentName, 0);
                    if (unreadCount > 0) {
                        unreadCountLabel.setText(String.valueOf(unreadCount));
                        unreadCountLabel.setVisible(true);
                    } else {
                        unreadCountLabel.setVisible(false);
                    }
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
                byte[] profileImageBytes = stdDb.getStudentProfilePicture(selectedStudent);
                Image profilePic;
                if (profileImageBytes != null && profileImageBytes.length > 0) {
                    profilePic = new Image(new ByteArrayInputStream(profileImageBytes));
                } else {
                    profilePic = new Image(getClass().getResource("/img/Profile/user.png").toExternalForm());
                }
                Image squaredProfilePic = cropToSquare(profilePic);
                currStudentImg.setImage(squaredProfilePic);
                Circle clip = new Circle(25, 25, 25);
                currStudentImg.setClip(clip);
                currStudentImg.setFitWidth(50);
                currStudentImg.setFitHeight(50);
                currStudentImg.setPreserveRatio(false);
                currStudentImg.setSmooth(true);
                currStudentImg.setCache(true);

                int teacherId = teacherDBConnect.getTeacherId(server.getTeacherName());
                int studentId = stdDb.getStudentID(selectedStudent);

                loadChatHistoryFromDB(teacherId, studentId);
            }
        });

        sendButton.setOnAction(event -> {
            sendMessage();
        });

        tfMessage.setOnAction(event -> sendMessage());

        displayNavbar();
        hoverEffect(globalButton);
    }

    private void sendMessage() {
        if (selectedStudent != null && !tfMessage.getText().isEmpty()) {
            String messageToSend = tfMessage.getText();
            server.sendMessageToStudent(selectedStudent, messageToSend);

            int sender_id = teacherDBConnect.getTeacherId(server.getTeacherName());
            int receive_id = stdDb.getStudentID(selectedStudent);
            chatHistoryDB.saveChatMessage(sender_id, "teacher", receive_id, "student", messageToSend);
            loadChatHistoryFromDB(sender_id, receive_id);
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
                studentList.getItems().remove(studentName);
                studentList.getItems().add(0, studentName);
                studentList.getSelectionModel().select(0);
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
            if (!studentList.getItems().get(0).equals(studentName)) {
                studentList.getItems().remove(studentName);
                studentList.getItems().add(0, studentName);
            }
            if (selectedStudent == null || !selectedStudent.equals(studentName)) {
                unreadMessageCounts.put(studentName,
                        unreadMessageCounts.getOrDefault(studentName, 0) + 1);
                studentList.refresh();
            }
            if (selectedStudent != null && selectedStudent.equals(studentName)) {
                addMessage(message, Pos.CENTER_LEFT, "#D9D9D9");
                unreadMessageCounts.put(studentName, 0);
                studentList.refresh();
            }

            chatHistory.computeIfAbsent(studentName, k -> new ArrayList<>())
                    .add(createMessageBox(message, Pos.CENTER_LEFT, "#D9D9D9"));
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
        String studentName = selectedStudent;
        unreadMessageCounts.put(studentName, 0);
        studentList.refresh();
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


    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            navBarContainer.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private Image cropToSquare(Image originalImage) {
        double width = originalImage.getWidth();
        double height = originalImage.getHeight();
        double cropSize = Math.min(width, height);
        double x = (width - cropSize) / 2;
        double y = (height - cropSize) / 2;

        PixelReader pixelReader = originalImage.getPixelReader();
        WritableImage croppedImage = new WritableImage(
                pixelReader,
                (int)x,
                (int)y,
                (int)cropSize,
                (int)cropSize
        );
        return croppedImage;
    }
}
