package Utili;


public class OfflineCourseInfo {

    private int userID;
    private int courseID;
    private String courseName;
    private String courseDescription;
    private String courseCategory;
    private String teacherName;

    // Optional (used for display in offline course list)
    private String courseThumbnailPath; // Local image path or placeholder

    // Getters and Setters

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseThumbnailPath() {
        return courseThumbnailPath;
    }

    public void setCourseThumbnailPath(String courseThumbnailPath) {
        this.courseThumbnailPath = courseThumbnailPath;
    }
}
