package Student.dashboard;

public class CourseProgress {
    private String courseName;
    private double progressPercentage;

    public CourseProgress(String courseName, double progressPercentage) {
        this.courseName = courseName;
        this.progressPercentage = progressPercentage;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    @Override
    public String toString() {
        return "Course: " + courseName + ", Progress: " + progressPercentage + "%";
    }
}
