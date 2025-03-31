package Utili;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfflineCacheManager {
    // Existing fields
    private List<OfflineCourseData> offlineChapters = new ArrayList<>();
    private Map<Integer, OfflineCourseData> offlineChaptersMap = new HashMap<>();

    public void addOfflineChapter(OfflineCourseData chapter) {
        offlineChapters.add(chapter);
        offlineChaptersMap.put(chapter.getChapterID(), chapter);
    }

    public OfflineCourseData getOfflineChapter(int chapterID) {
        return offlineChaptersMap.get(chapterID);
    }

    public List<OfflineCourseData> getAllOfflineChapters() {
        return offlineChapters;
    }

    // Save all offline chapters to a JSON file
    public void saveOfflineDataToFile(String filePath) {
        Gson gson = new Gson();
        String json = gson.toJson(offlineChapters);
        try {
            Files.write(Paths.get(filePath), json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load offline chapters from a JSON file
    public void loadOfflineDataFromFile(String filePath) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<OfflineCourseData>>() {}.getType();
            List<OfflineCourseData> loadedData = gson.fromJson(json, listType);

            // Clear current data and load new data
            offlineChapters.clear();
            offlineChaptersMap.clear();
            if (loadedData != null) {
                for (OfflineCourseData chapter : loadedData) {
                    addOfflineChapter(chapter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
