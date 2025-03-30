package Student.courseManage;

import java.util.ArrayList;
import java.util.List;

public class CartData {
    private static List<courseObject> cartCourses = new ArrayList<>();

    public static void addCourse(courseObject course) {
        cartCourses.add(course);
    }

    public static List<courseObject> getCourses() {
        return cartCourses;
    }

    public static void clearCart() {
        cartCourses.clear();
    }
}
