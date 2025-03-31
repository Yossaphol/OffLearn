package Utili;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileStorageHelper {

    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final String HIDDEN_FOLDER_NAME_WINDOWS = ".OfflearnVideos";  // Hidden folder name for Windows
    private static final String HIDDEN_FOLDER_NAME_LINUX_MAC = ".OfflearnVideos";  // Hidden folder name for Linux/macOS

    public static File getUserStorageFolder() {
        File folder;

        // Check the OS and set the folder name accordingly
        if (OSUtils.isWindows()) {
            folder = new File(PROJECT_DIR, HIDDEN_FOLDER_NAME_WINDOWS);  // Hidden folder inside project directory for Windows
        } else {
            folder = new File(PROJECT_DIR, HIDDEN_FOLDER_NAME_LINUX_MAC);  // Hidden folder for Linux/macOS
        }

        if (!folder.exists()) {
            folder.mkdirs();  // Create folder if it doesn't exist
        }

        // For Windows, make the folder hidden (Linux/macOS folders are already hidden with the dot prefix)
        if (OSUtils.isWindows()) {
            setHiddenFolder(folder);  // Set folder hidden on Windows
        }

        return folder;
    }

    private static void setHiddenFolder(File folder) {
        try {
            Path folderPath = folder.toPath();
            Files.setAttribute(folderPath, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
            System.out.println("Windows folder is now hidden.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getChapterVideoFile(int chapterID) {
        String filename = chapterID + ".mp4";
        return new File(getUserStorageFolder(), filename);
    }
}

