package Student.learningPage;

public class VideoItem {
    private int id;
    private int chapterId;
    private String title;
    private String path;
    private String thumbnail;
    private String playTime;
    private int order;
    private int likeCount;

    public VideoItem(int id, int chapterId, String title, String path, String thumbnail, String playTime, int order, int likeCount) {
        this.id = id;
        this.chapterId = chapterId;
        this.title = title;
        this.path = path;
        this.thumbnail = thumbnail;
        this.playTime = playTime;
        this.order = order;
        this.likeCount = likeCount;
    }

    // Getters
    public int getId() { return id; }
    public int getChapterId() { return chapterId; }
    public String getTitle() { return title; }
    public String getPath() { return path; }
    public String getThumbnail() { return thumbnail; }
    public String getPlayTime() { return playTime; }
    public int getOrder() { return order; }
    public int getLikeCount() { return likeCount; }
}