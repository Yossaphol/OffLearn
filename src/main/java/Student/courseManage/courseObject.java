package Student.courseManage;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class courseObject {
    private String description;
    private String name;
    private double price;
    private double rating;
    private int reviewCount;
    private String categoryIcon;
    private String categoryName;
    private String picture;
    private String teacherName;
    private String shortDescription;
    private String courseImg;

    private int courseID;
    private int totalReview;
    private int totalLesson;
    private String teacherImg;

    public int getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(int totalReview) {
        this.totalReview = totalReview;
    }

    public int getTotalLesson() {
        return totalLesson;
    }

    public void setTotalLesson(int totalLesson) {
        this.totalLesson = totalLesson;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTeacherImg() {
        return teacherImg;
    }

    public void setTeacherImg(String teacherImg) {
        this.teacherImg = teacherImg;
    }


    public courseObject(String description, String name, double price, double rating, int reviewCount,
                        String categoryIcon, String categoryName, String picture, String teacherName) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
        this.picture = picture;
        this.teacherName = teacherName;
    }

        public String getDescription() {
            return description;
        }
        public String getName() {
            return name;
        }
        public double getPrice() {
            return price;
        }
        public double getRating() {
            return rating;
        }
        public int getReviewCount() {
            return reviewCount;
        }
        public String getCategoryIcon() {
            return categoryIcon;
        }

        public String getCategoryName() {
            return categoryName;
        }
        public String getPicture() {
            return picture;
        }
        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        courseObject other = (courseObject) obj;
        return this.courseID == other.courseID;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(courseID);
    }



}
