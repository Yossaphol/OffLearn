package Student.leaderboard;

import Database.EnrollDB;
import Database.StudentScoreDB;
import Database.UserDB;
import a_Session.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class leaderboardController implements Initializable {

    public HBox searhbar_container;
    public VBox leftWrapper;
    public Label welcomeText;
    public VBox leaderboardTable_container;
    public VBox studentList;
    public ComboBox select_subject;

    //First
    public Circle pic1;
    public Label name_nd1;
    public Label pts_nd1;
    //Second
    public Circle pic2;
    public Label name_nd;
    public Label pts_nd;
    //Third
    public Circle pic3;
    public Label name_nd2;
    public Label pts_nd2;

    //Slide
    public Button next;
    public Button previous;
    private int currentPage = 0;
    private final int PAGE_SIZE = 5;

    EnrollDB enrollDta = new EnrollDB();
    UserDB user = new UserDB();
    String username = SessionManager.getInstance().getUsername();
    StudentScoreDB scoreDB = new StudentScoreDB();
    int std_id = user.getUserId(username);
    List<String> EnrolledCourses = enrollDta.getEnrolledCourseNames(std_id);
    List<String> studentInCourse;
    private int courseID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeText.setText("คุณเก่งขึ้นในทุกๆวัน\nสู้ต่อไป อย่ายอมแพ้!");


        loadStdFromSubject();
        setupSubjectSelectionListener();
        setupPaginationButtons();
    }


    private void loadStd(String courseName) {
        studentInCourse = enrollDta.getStudentsByCourseName(courseName);
        courseID = scoreDB.getCourseIdByCourseName(courseName);

        if (courseID == -2) {
            welcomeText.setText("หลักสูตรนี้ยังไม่มีแบบทดสอบ :)");
            setFirst("First", 0, "/img/Profile/user.png");
            setSecond("Second", 0, "/img/Profile/user.png");
            setThird("Third", 0, "/img/Profile/user.png");
            studentList.getChildren().clear();
            studentList.getChildren().add(welcomeText);
            return;
        }


        Map<String, Integer> studentScores = new HashMap<>();
        for (String student : studentInCourse) {
            int studentId = user.getUserId(student);
            studentScores.put(student, scoreDB.getTotalStudentScore(studentId, courseID));
        }

        studentInCourse.sort((s1, s2) -> Integer.compare(studentScores.get(s2), studentScores.get(s1)));

        currentPage = 0;
        updateStudentList();
        welcomeText.setText("คุณเก่งขึ้นในทุกๆวัน\nสู้ต่อไป อย่ายอมแพ้!");


        if (!studentInCourse.isEmpty()) {
            if (studentInCourse.size() >= 3) {
                setFirst(studentInCourse.get(0), scoreDB.getTotalStudentScore(user.getUserId(studentInCourse.get(0)), courseID), user.getProfile(studentInCourse.get(0)));
                setSecond(studentInCourse.get(1), scoreDB.getTotalStudentScore(user.getUserId(studentInCourse.get(1)), courseID), user.getProfile(studentInCourse.get(1)));
                setThird(studentInCourse.get(2), scoreDB.getTotalStudentScore(user.getUserId(studentInCourse.get(2)), courseID), user.getProfile(studentInCourse.get(2)));
            } else if (studentInCourse.size() == 2) {
                setFirst(studentInCourse.get(0), scoreDB.getTotalStudentScore(user.getUserId(studentInCourse.get(0)), courseID), user.getProfile(studentInCourse.get(0)));
                setSecond(studentInCourse.get(1), scoreDB.getTotalStudentScore(user.getUserId(studentInCourse.get(1)), courseID), user.getProfile(studentInCourse.get(1)));
                setThird("Third", 0, "/img/Profile/user.png");
            } else if (studentInCourse.size() == 1) {
                setFirst(studentInCourse.get(0), scoreDB.getTotalStudentScore(user.getUserId(studentInCourse.get(0)), courseID), user.getProfile(studentInCourse.get(0)));
                setSecond("Second", 0, "/img/Profile/user.png");
                setThird("Third", 0, "/img/Profile/user.png");
            }
        }
    }


    private void loadStdFromSubject(){
        if(!EnrolledCourses.isEmpty()){
            for(String course: EnrolledCourses){
                select_subject.getItems().add(course);
            }
            select_subject.setValue(EnrolledCourses.get(0));
            loadStd(String.valueOf(select_subject.getValue()));
        }else{
            select_subject.setValue("คุณยังไม่ได้ลงทะเบียนหลักสูตรใดๆ");
        }
    }

    private void setupSubjectSelectionListener() {
        select_subject.setOnAction(event -> {
            String selectedCourse = (String) select_subject.getValue();
            if (selectedCourse != null && !selectedCourse.equals("คุณยังไม่ได้ลงทะเบียนหลักสูตรใดๆ")) {
                studentList.getChildren().clear();
                loadStd(selectedCourse);
            }
        });
    }

    private void updateStudentList() {
        studentList.getChildren().clear();

        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, studentInCourse.size());

        for (int i = start; i < end; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/statistics/studentNode.fxml"));
                Node stdBox = loader.load();
                studentNodeController controller = loader.getController();

                Integer studentScore = scoreDB.getTotalStudentScore(user.getUserId(studentInCourse.get(i)), courseID);
                if (studentScore == null) {
                    studentScore = 0;
                }

                String studentName = studentInCourse.get(i);
                if(studentName.equals(username)){
                    studentName = studentInCourse.get(i)+ " (คุณ)";
                }
                controller.setDetail(i + 1, studentName, studentScore, user.getProfile(studentInCourse.get(i)));
                studentList.getChildren().add(stdBox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        updatePaginationButtons();
    }

    private void setupPaginationButtons() {
        next.setOnAction(event -> {
            if ((currentPage + 1) * PAGE_SIZE < studentInCourse.size()) {
                currentPage++;
                updateStudentList();
            }
        });
        previous.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                updateStudentList();
            }
        });
        updatePaginationButtons();
    }

    private void updatePaginationButtons() {
        previous.setDisable(currentPage == 0);
        next.setDisable((currentPage + 1) * PAGE_SIZE >= studentInCourse.size());
    }

    private void setFirst(String name, int point, String imgPath){
        name_nd1.setText(name);
        pts_nd1.setText(String.valueOf(point));
        setPic(imgPath, pic1);
    }

    private void setSecond(String name, int point, String imgPath){
        name_nd.setText(name);
        pts_nd.setText(String.valueOf(point));
        setPic(imgPath, pic2);
    }
    private void setThird(String name, int point, String imgPath){
        name_nd2.setText(name);
        pts_nd2.setText(String.valueOf(point));
        setPic(imgPath, pic3);
    }

    public void setPic(String Url, Circle profilePic){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        profilePic.setStroke(Color.TRANSPARENT);
        profilePic.setFill(new ImagePattern(img));
    }
}
