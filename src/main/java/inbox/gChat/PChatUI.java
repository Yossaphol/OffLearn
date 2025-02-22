package inbox.gChat;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PChatUI extends VBox {
    public void PChatComponent() {
        setPrefSize(300, 890);
        setStyle("-fx-border-color: D8D9DA; -fx-background-color: white;");

        VBox topSection = new VBox();
        topSection.setPrefSize(298, 98);
        Label chatLabel = new Label("Chat");
        chatLabel.setTextFill(javafx.scene.paint.Color.web("#8100cc"));
        chatLabel.setFont(new Font("Kodchasan Bold", 24));
        chatLabel.setPadding(new Insets(20, 0, 0, 30));

        StackPane searchPane = new StackPane();
        searchPane.setPrefSize(298, 62);
        TextField searchField = new TextField();
        searchField.setPrefSize(238, 27);
        searchField.setPromptText("ค้นหาบทสนทนา");
        searchField.setStyle("-fx-background-radius: 10;");
        StackPane.setMargin(searchField, new Insets(10, 30, 0, 30));

        ImageView searchIcon = new ImageView(new Image(getClass().getResource("/img/icon/search-interface-symbol.png").toExternalForm()));
        searchIcon.setFitWidth(20);
        searchIcon.setFitHeight(15);
        StackPane.setMargin(searchIcon, new Insets(10, 0, 0, 40));

        searchPane.getChildren().addAll(searchField, searchIcon);
        topSection.getChildren().addAll(chatLabel, searchPane);

        HBox chatTypeSection = new HBox();
        chatTypeSection.setPrefSize(339, 76);
        VBox privateChatBox = new VBox();
        privateChatBox.setPrefSize(278, 76);

        Label publicChatLabel = new Label("สาธารณะ");
        publicChatLabel.setTextFill(javafx.scene.paint.Color.web("#8100cc"));
        publicChatLabel.setFont(new Font("Kodchasan Bold", 14));

        HBox privateButton = new HBox();
        privateButton.setPrefSize(96, 46);
        privateButton.setCursor(Cursor.HAND);

        ImageView privateChatIcon = new ImageView(new Image(getClass().getResource("/img/icon/private-chat.png").toExternalForm()));
        privateChatIcon.setFitWidth(50);
        privateChatIcon.setFitHeight(50);

        VBox privateChatInfo = new VBox();
        privateChatInfo.setPrefSize(235, 46);
        Label privateChatLabel = new Label("Private Chat");
        privateChatLabel.setTextFill(javafx.scene.paint.Color.web("#8100cc"));
        privateChatLabel.setFont(new Font("Kodchasan Bold", 15));
        Label messageCount = new Label("99+ ข้อความ");
        messageCount.setTextFill(javafx.scene.paint.Color.web("#8e8e8e"));
        privateChatInfo.getChildren().addAll(privateChatLabel, messageCount);

        privateButton.getChildren().addAll(privateChatIcon, privateChatInfo);
        privateChatBox.getChildren().addAll(publicChatLabel, privateButton);

        chatTypeSection.getChildren().add(privateChatBox);
        setMargin(chatTypeSection, new Insets(10, 0, 0, 20));

        Pane spacer = new Pane();
        spacer.setPrefSize(298, 39);

        getChildren().addAll(topSection, chatTypeSection, spacer);
    }
}
