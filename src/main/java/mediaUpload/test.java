package mediaUpload;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class test extends Application {
    private MediaUpload mediaUpload;

    @Override
    public void start(Stage primaryStage) {
        mediaUpload = new MediaUpload();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("เลือกไฟล์สำหรับอัปโหลด");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null && selectedFile.isFile()) {
            String url = mediaUpload.uploadImg(selectedFile);
            if (url != null) {
                System.out.println("อัปโหลดสำเร็จ: " + url);
            } else {
                System.out.println("อัปโหลดไม่สำเร็จ!");
            }
        }
    }
}
