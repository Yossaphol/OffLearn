package Student.dashboard;

public class CourseScore {
    private String courseName;
    private int score;

    public CourseScore(String courseName, int score) {
        this.courseName = courseName;
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getScore() {
        return score;
    }
}
