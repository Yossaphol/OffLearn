package Student.courseManage;

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



}
