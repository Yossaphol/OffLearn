package Student.courseManage;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<courseObject> cartCourses;

    private CartManager() {
        cartCourses = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // แก้จาก static → non-static
    public void addCourse(courseObject course) {
        cartCourses.add(course);
    }

    public List<courseObject> getCartList() {
        return cartCourses;
    }

    public void clearCart() {
        cartCourses.clear();
    }
}
