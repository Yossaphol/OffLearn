package Utili;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class OfflineCourseManager {

    private static final Gson gson = new Gson();

    public static void saveChapter(int userID, OfflineCourseData data) throws IOException {
        File chapterFile = OfflinePathHelper.getChapterFile(userID, data.getCourseID(), data.getChapterID());
        String json = gson.toJson(data);
        Files.write(chapterFile.toPath(), json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static OfflineCourseData loadChapter(int userID, int courseID, int chapterID) throws IOException {
        File chapterFile = OfflinePathHelper.getChapterFile(userID, courseID, chapterID);
        if (!chapterFile.exists()) return null;
        String json = Files.readString(chapterFile.toPath(), StandardCharsets.UTF_8);
        return gson.fromJson(json, OfflineCourseData.class);
    }

    public static void saveCourseInfo(int userID, OfflineCourseData info) throws IOException {
        File courseInfoFile = new File(OfflinePathHelper.getCourseFolder(userID, info.getCourseID()), "course_info.json");
        String json = gson.toJson(info);
        Files.write(courseInfoFile.toPath(), json.getBytes(StandardCharsets.UTF_8));
    }

    public static OfflineCourseData loadCourseInfo(int userID, int courseID) throws IOException {
        File file = new File(OfflinePathHelper.getCourseFolder(userID, courseID), "course_info.json");
        if (!file.exists()) return null;
        String json = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        return gson.fromJson(json, OfflineCourseData.class);
    }

    public static List<OfflineCourseData> getAllChaptersForCourse(int userID, int courseID) {
        File courseDir = OfflinePathHelper.getCourseFolder(userID, courseID);
        File[] files = courseDir.listFiles((f, name) -> name.startsWith("chapter_") && name.endsWith(".json"));

        List<OfflineCourseData> list = new ArrayList<>();
        if (files != null) {
            for (File f : files) {
                try {
                    String json = Files.readString(f.toPath());
                    OfflineCourseData data = gson.fromJson(json, OfflineCourseData.class);
                    list.add(data);
                } catch (IOException ignored) {}
            }
        }
        return list;
    }

    public static List<OfflineCourseData> getAllOfflineCourses(int userID) {
        File userFolder = OfflinePathHelper.getUserFolder(userID);
        File[] courseFolders = userFolder.listFiles(File::isDirectory);

        List<OfflineCourseData> courses = new ArrayList<>();
        if (courseFolders != null) {
            for (File dir : courseFolders) {
                File infoFile = new File(dir, "course_info.json");
                if (infoFile.exists()) {
                    try {
                        String json = Files.readString(infoFile.toPath());
                        courses.add(gson.fromJson(json, OfflineCourseData.class));
                    } catch (IOException ignored) {}
                }
            }
        }
        return courses;
    }
}

