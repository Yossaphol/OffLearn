package Utili;

public class OfflineChapterData {
    private int chapterID;
    private String chapterName;
    private String chapterDescription;
    private String videoPath;

    // Constructors
    public OfflineChapterData(int chapterID, String chapterName, String videoPath) {
        this.chapterID = chapterID;
        this.chapterName = chapterName;
        this.videoPath = videoPath;
    }

    // Getters and Setters
    public int getChapterID() { return chapterID; }
    public String getChapterName() { return chapterName; }
    public String getVideoPath() { return videoPath; }
    public String getChapterDescription() { return chapterDescription; }
    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }
}