package Utili;

import java.util.List;

public class OfflineCourseData {
    private int courseID;
    private int chapterID;
    private String subjectName;
    private String teacherName;
    private List<String[]> playlist; // e.g., each entry: {chapterID, chapterName}

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
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public List<String[]> getPlaylist() {
        return playlist;
    }
    public void setPlaylist(List<String[]> playlist) {
        this.playlist = playlist;
    }
}
