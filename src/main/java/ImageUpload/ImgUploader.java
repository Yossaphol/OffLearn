package ImageUpload;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobHttpHeaders;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImgUploader {

    private static final Dotenv nev = Dotenv.load();
    private static final String CONNECTION_STRING = nev.get("AZ_KEY");
    private static final String CONTAINER_NAME = nev.get("AZ_CONT");

    public static String upload(File file) {
        if (file == null) {
            return "Please choose your file";
        }

        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(CONNECTION_STRING)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME);
            BlobClient blobClient = containerClient.getBlobClient("uploads/" + file.getName());

            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                blobClient.upload(fileInputStream, file.length(), true);
                blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType(getMimeType(file)));
            }

            return blobClient.getBlobUrl();

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String getMimeType(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".png")) return "image/png";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }
}
