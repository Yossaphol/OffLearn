package Student.learningPage;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

public class AttachmentController {

    public Label filename;
    public Button downloadbtn;

    private String downloadURL;

    public void setFilename(String name) {
        filename.setText(name);
    }

    public void setDownloadURL(String url) {
        this.downloadURL = url;
        downloadbtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            String safeName = getSafeFilenameFromURL(url);
            String extension = getFileExtension(url); // e.g. ".pdf", ".docx", etc.
            if (!safeName.toLowerCase().endsWith(extension.toLowerCase())) {
                safeName += extension;
            }
            fileChooser.setInitialFileName(safeName);

            if (!extension.isEmpty()) {
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter(extension.toUpperCase() + " Files (*" + extension + ")", "*" + extension);
                fileChooser.getExtensionFilters().clear();
                fileChooser.getExtensionFilters().add(extFilter);
            } else {
                fileChooser.getExtensionFilters().clear();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
            }

            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                downloadFile(url, file);
            }
        });
    }

    private String getFileExtension(String url) {
        try {
            String name = new File(new URL(url).getPath()).getName();
            int lastIndex = name.lastIndexOf('.');
            if (lastIndex > 0 && lastIndex < name.length() - 1) {
                return name.substring(lastIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getSafeFilenameFromURL(String url) {
        try {
            String name = new File(new URL(url).getPath()).getName();
            return name.isEmpty() ? "downloaded_file" : name;
        } catch (Exception e) {
            return "downloaded_file";
        }
    }

    private void downloadFile(String urlStr, File destination) {
        Task<Void> downloadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    URL url = new URL(encodeS3URL(urlStr));
                    try (InputStream in = url.openStream();
                         FileOutputStream out = new FileOutputStream(destination)) {

                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        long totalBytesRead = 0;
                        int contentLength = url.openConnection().getContentLength();

                        while ((bytesRead = in.read(buffer)) != -1) {
                            if (isCancelled()) break;
                            out.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                            if (contentLength > 0) {
                                updateProgress(totalBytesRead, contentLength);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
                return null;
            }
        };

        downloadTask.setOnSucceeded(e -> Platform.runLater(() ->
                showAlert("Download Completed", "File downloaded to: " + destination.getAbsolutePath(), Alert.AlertType.INFORMATION)
        ));

        downloadTask.setOnFailed(e -> Platform.runLater(() ->
                showAlert("Download Failed", "Failed to download the file.", Alert.AlertType.ERROR)
        ));

        new Thread(downloadTask).start();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String encodeS3URL(String rawURL) {
        try {
            URL url = new URL(rawURL);
            String base = url.getProtocol() + "://" + url.getHost();
            String[] parts = url.getPath().split("/");

            StringBuilder encodedPath = new StringBuilder();
            for (String part : parts) {
                if (!part.isEmpty()) {
                    encodedPath.append("/").append(java.net.URLEncoder.encode(part, "UTF-8").replace("+", "%20"));
                }
            }

            return base + encodedPath;
        } catch (Exception e) {
            e.printStackTrace();
            return rawURL;
        }
    }
}
