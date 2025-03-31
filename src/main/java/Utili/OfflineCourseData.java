package Utili;

import java.util.List;

public class OfflineCourseData {
    private int courseID;
    private String courseName;
    private String courseDescription;
    private String courseCategory;
    private String teacherName;
    private List<OfflineChapterData> chapters;  // Store multiple chapters

    // Getters and Setters
    public int getCourseID() { return courseID; }
    public void setCourseID(int courseID) { this.courseID = courseID; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseDescription() { return courseDescription; }
    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }

    public String getCourseCategory() { return courseCategory; }
    public void setCourseCategory(String courseCategory) { this.courseCategory = courseCategory; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public List<OfflineChapterData> getChapters() { return chapters; }
    public void setChapters(List<OfflineChapterData> chapters) { this.chapters = chapters; }
}