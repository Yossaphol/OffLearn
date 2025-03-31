package Student.inbox.gChat;

import Database.*;
import Student.swing.PostTopicMDI;
import a_Session.SessionHandler;
import a_Session.SessionManager;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

import java.net.URL;
import java.util.ResourceBundle;

public class gChatController implements Initializable, SessionHandler {

    @FXML
    private Button createTopic;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView refresh;

    @FXML
    private VBox main;

    @FXML
    private Pane pane;

    @FXML
    private Label Name;

    @FXML
    private Circle profile;

    private String name;
    private UserDB userDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDB = new UserDB();
        handleSession();

        setProfile(userDB.getProfile(name));

        refreshContent();
        createTopic.setOnAction(actionEvent -> openPostTopic());
        refresh.setOnMouseClicked(mouseEvent -> refreshContent());

        setEffect();
    }

    @Override
    public void handleSession() {
        this.name = SessionManager.getInstance().getUsername();
        Name.setText(name);
    }

    public void refreshContent() {
        new Thread(() -> {
            topicDB topicDB = new topicDB();
            VBox newContent = topicDB.showTopicFromDB();

            Platform.runLater(() -> scrollPane.setContent(newContent));
        }).start();
    }

    public Pane getPane() {
        return this.pane;
    }

    public VBox getMain() {
        return this.main;
    }

    public void setProfile(String Url) {
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        this.profile.setStroke(Color.TRANSPARENT);
        this.profile.setFill(new ImagePattern(img));
    }

    public void setEffect() {
        hoverEffect(createTopic);
    }

    public void hoverEffect(Button btn) {
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

        btn.setOnMouseEntered(mouseEvent -> scaleUp.play());
        btn.setOnMouseExited(mouseEvent -> scaleDown.play());
    }


    public void openPostTopic() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf()); // FlatLaf Light Theme
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Post Topic");
            frame.setSize(650, 450);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JDesktopPane desktopPane = new JDesktopPane();
            frame.setContentPane(desktopPane);

            JInternalFrame internalFrame = new JInternalFrame("", false, false, false, false);
            internalFrame.setBounds(0, 0, frame.getWidth() - 10, frame.getHeight() - 35);
            internalFrame.setBorder(null);
            internalFrame.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
            internalFrame.setVisible(true);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            contentPanel.add(new JLabel("Post Your Topic"), BorderLayout.NORTH);

            JTextArea textArea = new JTextArea();
            contentPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton postButton = new JButton("Post");
            postButton.addActionListener(e -> {
                System.out.println("Posted: " + textArea.getText());
                frame.dispose();
            });

            buttonPanel.add(postButton);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);

            internalFrame.setContentPane(contentPanel);

            desktopPane.add(internalFrame);
            frame.setVisible(true);
        });
    }


}

