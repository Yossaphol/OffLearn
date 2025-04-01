package Utili;


public class OfflineCourseData {
    private int userid;
    private int courseID;
    private int chapterID;
    private String courseName;
    private String courseDescription;
    private String courseCategory;
    private String chapterName;
    private String chapterDescription;
    private String videoPath;
    private String teacherName;

    // Getters and setters
    public int getCourseID() {
        return courseID;
    }
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public int getChapterID() {
        return chapterID;
    }
    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }
    public String getChapterName() {
        return chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public String getChapterDescription() {
        return chapterDescription;
    }
    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }
    public String getVideoPath() {
        return videoPath;
    }
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
