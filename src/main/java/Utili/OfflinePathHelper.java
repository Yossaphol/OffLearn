package Utili;

import java.io.File;

public class OfflinePathHelper {

    // Root: ~/.offline
    public static File getOfflineRoot() {
        File root = new File(System.getProperty("user.home"), ".offline");
        if (!root.exists()) root.mkdirs();
        return root;
    }

    // Per-user folder: ~/.offline/user_12
    public static File getUserFolder(int userID) {
        File userDir = new File(getOfflineRoot(), "user_" + userID);
        if (!userDir.exists()) userDir.mkdirs();
        return userDir;
    }

    // Per-course folder: ~/.offline/user_12/course_3
    public static File getCourseFolder(int userID, int courseID) {
        File courseDir = new File(getUserFolder(userID), "course_" + courseID);
        if (!courseDir.exists()) courseDir.mkdirs();
        return courseDir;
    }

    // Chapter JSON: ~/.offline/user_12/course_3/chapter_5.json
    public static File getChapterFile(int userID, int courseID, int chapterID) {
        return new File(getCourseFolder(userID, courseID), "chapter_" + chapterID + ".json");
    }

    // Chapter video: ~/.offline/user_12/course_3/chapter_5.mp4
    public static File getChapterVideoFile(int userID, int courseID, int chapterID) {
        return new File(getCourseFolder(userID, courseID), "chapter_" + chapterID + ".mp4");
    }


    // Course metadata: course_info.json
    public static File getCourseInfoFile(int userID, int courseID) {
        return new File(getCourseFolder(userID, courseID), "course_info.json");
    }
}
