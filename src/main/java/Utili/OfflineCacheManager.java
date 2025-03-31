package Utili;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfflineCacheManager {
    // Use an ArrayList if order matters
    private List<OfflineCourseData> offlineChapters = new ArrayList<>();

    // Or you can use a Map for quick lookup by chapterID
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
}
