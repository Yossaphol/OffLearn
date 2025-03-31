package Teacher.courseManagement;

import Student.courseManage.courseObject;
import com.beust.ah.A;

import java.util.ArrayList;

public class CourseItem {
    private int courseId;
    private String courseImg;
    private String courseName;
    private int coursePrice;
    private String courseCat;
    private String courseDesc;
    private ArrayList<ChapterItem> chapterList;
    private String teacherName;
    private String categoryName;

    public CourseItem(int courseId, String courseImg, String courseName, int coursePrice, String courseCat){
        this.setCourseId(courseId);
        this.setCourseImg(courseImg);
        this.setCourseName(courseName);
        this.setCoursePrice(coursePrice);
        this.setCourseCat(courseCat);
        this.chapterList = new ArrayList<ChapterItem>();
    }

    public CourseItem(int courseId, String courseImg, String courseName, int coursePrice, String courseCat, String courseDesc){
        this.setCourseId(courseId);
        this.setCourseImg(courseImg);
        this.setCourseName(courseName);
        this.setCoursePrice(coursePrice);
        this.setCourseDesc(courseDesc);
        this.setCourseCat(courseCat);
        this.chapterList = new ArrayList<ChapterItem>();
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

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public ArrayList<ChapterItem> getChapterList() {
        return chapterList;
    }

    public void setChapterList(ChapterItem chapterItem) {
        this.chapterList.add(chapterItem);
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public courseObject toCourseObject() {
        return new courseObject(
                courseDesc,
                courseName,
                coursePrice,
                4.9,              // rating
                120,              // จำนวนรีวิว
                "/img/icon/code.png",  // ไอคอนหมวดหมู่
                courseCat,
                courseImg,
                this.teacherName
        );
    }

}
