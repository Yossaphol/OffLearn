package ImageUpload;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class test extends Application {

    private Label statusLabel;
    private ImageView imageView;
    private File selectedFile;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Azure Blob Image Upload");

        Button uploadButton = new Button("Choose your file");
        Button uploadToAzureButton = new Button("Upload");
        statusLabel = new Label("waiting");
        imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        uploadButton.setOnAction(e -> {
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                statusLabel.setText(selectedFile.getName());
            }
        });

        uploadToAzureButton.setOnAction(e -> {
            if (selectedFile != null) {
                String result = ImgUploader.upload(selectedFile);
                statusLabel.setText(result);
            } else {
                statusLabel.setText("Please choose your file");
            }
        });

        VBox root = new VBox(10, uploadButton, imageView, uploadToAzureButton, statusLabel);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20px; -fx-background-color: #f4f4f4;");

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
}
