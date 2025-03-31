package Utili;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OfflineCourseManager {
    private static final String OFFLINE_FILE = "offline_courses.json";
    private Gson gson = new Gson();

    // Save a list of OfflineCourseData objects to file
    public void saveOfflineCourses(List<OfflineCourseData> courses) {
        try (FileWriter writer = new FileWriter(OFFLINE_FILE)) {
            gson.toJson(courses, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load offline courses from file
    public List<OfflineCourseData> loadOfflineCourses() {
        File file = new File(OFFLINE_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<OfflineCourseData>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
