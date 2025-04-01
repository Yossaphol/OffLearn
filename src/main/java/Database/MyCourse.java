package Database;

public class MyCourse {
    private String Course_ID;
    private String Cat_ID;
    private String courseName;
    private String courseDescription;
    private String price;
    private String image;
    private String User_ID;
    private String Course_Code;

    public MyCourse(String course_ID, String cat_ID, String courseName, String courseDescription, String price, String image, String user_ID, String course_Code) {
        Course_ID = course_ID;
        Cat_ID = cat_ID;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.price = price;
        this.image = image;
        User_ID = user_ID;
        Course_Code = course_Code;
    }


    public void setCourse_ID(String course_ID) {
        this.Course_ID = course_ID;
    }

    public void setCat_ID(String cat_ID) {
        this.Cat_ID = cat_ID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public void setCourse_Code(String course_Code) {
        Course_Code = course_Code;
    }

    public String getCourse_ID() {
        return Course_ID;
    }

    public String getCat_ID() {
        return Cat_ID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public String getCourse_Code() {
        return Course_Code;
    }
}
