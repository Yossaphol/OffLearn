package Teacher.courseManagement;

public class CourseItem {
    private String courseImg;
    private String courseName;
    private int coursePrice;
    private String courseCat;

    public CourseItem(String courseImg, String courseName, int coursePrice, String courseCat){
        this.setCourseImg(courseImg);
        this.setCourseName(courseName);
        this.setCoursePrice(coursePrice);
        this.setCourseCat(courseCat);
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(int coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseCat() {
        return courseCat;
    }

    public void setCourseCat(String courseCat) {
        this.courseCat = courseCat;
    }
}
