package Database;

import Teacher.courseManagement.ChapterItem;
import Teacher.courseManagement.CourseItem;
import Teacher.quiz.QuestionItem;
import Teacher.quiz.QuizItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDB extends ConnectDB{
    private CourseItem courseItem;

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public void saveToDB(int cat, String courseName, int userId, String desc, int price, String imageUrl) {
        String checkSql = "SELECT COUNT(*) FROM course WHERE courseName = ? AND User_ID = ?";
        String insertSql = "INSERT INTO course (Cat_ID, courseName, User_ID, courseDescription, price, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = this.connectToDB();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
        ) {
            checkStmt.setString(1, courseName);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return;
            }

            insertStmt.setInt(1, cat);
            insertStmt.setString(2, courseName);
            insertStmt.setInt(3, userId);
            insertStmt.setString(4, desc);
            insertStmt.setInt(5, price);
            insertStmt.setString(6, imageUrl);
            insertStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCourseIDByChapterID(int chapterID) {
        String sql = "SELECT Course_ID FROM offlearn.chapter WHERE Chapter_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, chapterID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt("Course_ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public int getCourseID(String name){
        String sql = "SELECT Course_ID FROM offlearn.course WHERE courseName = ? ";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt("Course_ID");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<CourseItem> getMyCourse(int user_id) {
        ArrayList<CourseItem> courseList = new ArrayList<>();

        String sql = "SELECT c.Course_ID, c.courseName, c.image, c.price, cat.catname " +
                "FROM offlearn.course c " +
                "JOIN offlearn.category cat ON c.Cat_ID = cat.Cat_id " +
                "WHERE c.user_id = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, user_id);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("Course_ID");
                    String name = rs.getString("courseName");
                    String img = rs.getString("image");
                    int price = rs.getInt("price");
                    String catName = rs.getString("catname");

                    courseList.add(new CourseItem(id, img, name, price, catName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }


    public CourseItem getCourseByID(int courseID) {
        String courseSql = "SELECT c.courseName, c.image, c.price, c.courseDescription, cat.catname " +
                "FROM offlearn.course c " +
                "JOIN offlearn.category cat ON c.Cat_ID = cat.Cat_id " +
                "WHERE c.Course_ID = ?";

        String chapterSql = "SELECT Chapter_ID, chapterName, chapter_image, chapterDescription, chapter_material " +
                "FROM offlearn.chapter WHERE Course_ID = ?";

        String quizSql = "SELECT Quiz_ID, header FROM offlearn.quiz WHERE Chapter_ID = ? LIMIT 1";

        String questionSql = "SELECT Question_ID, questionText, correctAns, point, question_img FROM offlearn.question WHERE Quiz_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement courseStmt = conn.prepareStatement(courseSql);
             PreparedStatement chapterStmt = conn.prepareStatement(chapterSql)) {

            courseStmt.setInt(1, courseID);
            ResultSet courseRs = courseStmt.executeQuery();

            if (!courseRs.next()) return null;

            CourseItem course = new CourseItem(
                    courseID,
                    courseRs.getString("image"),
                    courseRs.getString("courseName"),
                    courseRs.getInt("price"),
                    courseRs.getString("catname"),
                    courseRs.getString("courseDescription")
            );

            chapterStmt.setInt(1, courseID);
            ResultSet chapterRs = chapterStmt.executeQuery();

            ChapterItem chapter = null;
            while (chapterRs.next()) {
                int chapterID = chapterRs.getInt("Chapter_ID");
                String chapterName = chapterRs.getString("chapterName");
                String chapterImage = chapterRs.getString("chapter_image");
                String chapterDescription = chapterRs.getString("chapterDescription");
                String chapterMaterial = chapterRs.getString("chapter_material");

                QuizItem quizItem = null;
                try (PreparedStatement quizStmt = conn.prepareStatement(quizSql)) {
                    quizStmt.setInt(1, chapterID);
                    ResultSet quizRs = quizStmt.executeQuery();
                    if (quizRs.next()) {
                        int quizID = quizRs.getInt("Quiz_ID");
                        quizItem = new QuizItem(quizID);

                        try (PreparedStatement questionStmt = conn.prepareStatement(questionSql)) {
                            questionStmt.setInt(1, quizID);
                            ResultSet questionRs = questionStmt.executeQuery();
                            while (questionRs.next()) {
                                String questionName = questionRs.getString("questionText");
                                int point = questionRs.getInt("point");
                                String corrAns = questionRs.getString("correctAns");
                                String img = questionRs.getString("question_img");
                                quizItem.setQuestionList(new QuestionItem(questionName, point, corrAns, img));
                            }
                        }
                    }
                }

                chapter = new ChapterItem(chapterID, chapterName, chapterDescription, chapterImage, chapterMaterial);
                chapter.setQuizItem(quizItem);
                course.setChapterList(chapter);
            }

            return course;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("not found");
        return null;
    }

    public boolean deleteFromDB(int courseID) {
        String deleteCourseSql = "DELETE FROM offlearn.course WHERE Course_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement deleteCourseStmt = conn.prepareStatement(deleteCourseSql)) {

            deleteCourseStmt.setInt(1, courseID);
            int affectedRows = deleteCourseStmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countMyCourses(int user_id) {
        String sql = "SELECT COUNT(*) FROM offlearn.course WHERE User_ID = ?";
        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, user_id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Integer> getAllMyCourseIDs(int user_id) {
        ArrayList<Integer> courseIDs = new ArrayList<>();
        String sql = "SELECT Course_ID FROM offlearn.course WHERE User_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, user_id);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    courseIDs.add(rs.getInt("Course_ID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseIDs;
    }

    public ArrayList<CourseItem> getAllCourses() {
        ArrayList<CourseItem> courseList = new ArrayList<>();

        String sql = "SELECT c.Course_ID, c.courseName, c.image, c.price, c.courseDescription, cat.catname, " +
                "CONCAT(u.firstname, ' ', u.lastname) AS teacherName " +
                "FROM offlearn.course c " +
                "JOIN offlearn.category cat ON c.Cat_ID = cat.Cat_id " +
                "JOIN offlearn.user u ON c.User_ID = u.User_ID " +
                "WHERE c.verify = 1";



        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("Course_ID");
                String name = rs.getString("courseName");
                String img = rs.getString("image");
                int price = rs.getInt("price");
                String description = rs.getString("courseDescription");
                String catName = rs.getString("catname");
                String teacherName = rs.getString("teacherName");

                CourseItem course = new CourseItem(id, img, name, price, catName, description);
                course.setTeacherName(teacherName);

                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }

    public int countMyChapter(int course_id) {
        String sql = "SELECT COUNT(*) FROM offlearn.chapter WHERE Course_ID = ?";
        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, course_id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public CourseItem getLatestCourse() {
        String sql = "SELECT c.Course_ID, c.courseName, c.image, c.price, c.courseDescription, cat.catname, " +
                "CONCAT(u.firstname, ' ', u.lastname) AS teacherName " +
                "FROM offlearn.course c " +
                "JOIN offlearn.category cat ON c.Cat_ID = cat.Cat_id " +
                "JOIN offlearn.user u ON c.User_ID = u.User_ID " +
                "WHERE c.verify = 1 " +
                "ORDER BY c.Course_ID DESC LIMIT 1";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt("Course_ID");
                String name = rs.getString("courseName");
                String img = rs.getString("image");
                int price = rs.getInt("price");
                String description = rs.getString("courseDescription");
                String catName = rs.getString("catname");
                String teacherName = rs.getString("teacherName");

                CourseItem course = new CourseItem(id, img, name, price, catName, description);
                course.setTeacherName(teacherName);

                return course;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
