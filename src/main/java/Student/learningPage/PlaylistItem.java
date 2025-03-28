package Student.learningPage;

public class PlaylistItem {
    private int chapterID;
    private String type; // "video" or "quiz"

    public PlaylistItem(int chapterID, String type) {
        this.chapterID = chapterID;
        this.type = type;
    }

    public int getChapterID() { return chapterID; }
    public String getType() { return type; }
}