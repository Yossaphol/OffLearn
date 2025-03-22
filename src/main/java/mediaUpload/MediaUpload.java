package mediaUpload;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.File;
import java.nio.file.Paths;

public class MediaUpload {
    private static final Dotenv env = Dotenv.load();
    private static final String BUCKET_NAME = "offlearnmedia";
    private static final String REGION = "ap-southeast-2";

    public String uploadImg(File file) {
        try {
            String accessKey = env.get("AWS_AC_KEY");
            String secretKey = env.get("AWS_SC_KEY");

            S3Client s3 = S3Client.builder()
                    .region(Region.of(REGION))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();

            String fileKey = "image/" + file.getName();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileKey)
                    .build();

            s3.putObject(request, Paths.get(file.getAbsolutePath()));

            return "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/" + fileKey;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String uploadVideo(File file) {
        try {
            String accessKey = env.get("AWS_ACCESS_KEY_ID");
            String secretKey = env.get("AWS_SECRET_ACCESS_KEY");

            S3Client s3 = S3Client.builder()
                    .region(Region.of(REGION))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();

            String fileKey = "video/" + file.getName();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileKey)
                    .build();

            s3.putObject(request, Paths.get(file.getAbsolutePath()));

            return "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/" + fileKey;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
