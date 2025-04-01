package Student.HomeAndNavigation;

import Student.Quiz.QuizPageController;
import Student.Quiz.QuizSummary;
import Student.Quiz.ResultPageController;
import Student.learningPage.DisposableController;
import Student.learningPage.MainPageOffline;
import Student.navBarAndSearchbar.navBarOffline;
import Teacher.quiz.QuizItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import Student.mainPage.mainPageController;
import Student.navBarAndSearchbar.navBarController;
import Student.inbox.pChat.pChatController;

import java.io.IOException;

public class Navigator {

    private static mainPageController controller;
    private static MainPageOffline controller1;
    private static navBarController navCtrl;
    private static navBarOffline navCtrl1;
    private static Object currentContentController;

    public static void setController(mainPageController ctrl) {
        controller = ctrl;
    }

    public static void setMainPageOffline(MainPageOffline ctrl1) {
        controller1 = ctrl1;
    }

    public static void setNavBarController(navBarController navController) {
        navCtrl = navController;
    }

    public static void setNavBarController(navBarOffline navController) {
        navCtrl1 = navController;
    }

    private void setCurrentPageSafe(String id) {
        if (navCtrl != null) navCtrl.setCurrentPage(id);
        if (navCtrl1 != null) navCtrl1.setCurrentPage(id);
    }

    private void displayNavbarSafe() {
        if (controller != null) controller.displayNavbar();
        if (controller1 != null) controller1.displayNavbar();
    }

    public void homeRoute(MouseEvent event) {
        navigateTo("/fxml/Student/HomePage/home.fxml");
        setCurrentPageSafe("home_btn");
        displayNavbarSafe();
    }

    public void dashboardRoute(MouseEvent event) {
        navigateTo("/fxml/Student/statistics/dashboard.fxml");
        setCurrentPageSafe("dashboard_btn");
        displayNavbarSafe();
    }

    public void courseRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/course.fxml");
        setCurrentPageSafe("course_btn");
        displayNavbarSafe();
    }

    public void inboxRoute(MouseEvent event) {
        navigateTo("/fxml/Student/inbox/pChat.fxml");
        setCurrentPageSafe("inbox_btn");
        displayNavbarSafe();
    }

    public void inboxRouteWithTeacher(String teacherName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/inbox/pChat.fxml"));
            Parent root = loader.load();
            pChatController chatController = loader.getController();
            chatController.setTeacherToSelect(teacherName);
            if (controller != null) {
                controller.displayNavbar();
                controller.displayContent(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void taskRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/task.fxml");
        setCurrentPageSafe("task_btn");
        displayNavbarSafe();
    }

    public void roadmapRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/roadmap.fxml");
        setCurrentPageSafe("roadmap_btn");
        displayNavbarSafe();
    }

    public void learningPageRoute(MouseEvent event) {
        navigateTo("/fxml/Student/learningPage/learningPage.fxml");
        displayNavbarSafe();
    }

    public void learningPageRoute(ActionEvent event) {
        navigateTo("/fxml/Student/learningPage/learningPage.fxml");
        displayNavbarSafe();
    }

    public void QuizPage(int chapterID, int quizID) {
        navigateTo("/fxml/Student/Quiz/quizPage.fxml", chapterID, quizID);
    }

    public void QuizResult(int point, int courseID, QuizItem quizItem, int chapterID) {
        navigateTo("/fxml/Student/Quiz/resultPage.fxml", point, courseID, quizItem, chapterID);
        if (controller != null) {
            controller.stopHideNavbar();
            controller.stopHideSearchBar();
        }
    }

    public void QuizSummary(int point, int courseID, QuizItem quizItem, int chapterID) {
        navigateTo("/fxml/Student/Quiz/quizSummary.fxml", point, courseID, quizItem, chapterID);
        if (controller != null) {
            controller.stopHideNavbar();
            controller.stopHideSearchBar();
        }
    }

    public void testResult(MouseEvent event) {
        navigateTo("/fxml/Student/Quiz/resultPage.fxml");
    }

    public void preTestRoute(MouseEvent event) {
        navigateTo("/fxml/Student/Quiz/preTest.fxml");
    }

    public void courseEnrollRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/courseEnroll.fxml");
    }

    public void testRoute(MouseEvent event) {
        navigateTo("/fxml/Student/Quiz/quizPage.fxml");
        if (controller != null) {
            controller.hideNavbar();
            controller.hideSearchBar();
        }
    }

    public void myCourseRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/myCourse.fxml");
    }

    public void myCourseRoute(ActionEvent event) {
        navigateTo("/fxml/Student/courseManage/myCourse.fxml");
    }

    public void myRoadmapRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/myroadmap.fxml");
    }

    public void cartRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/cart.fxml");
    }

    public void leaderboardRoute(MouseEvent event) {
        navigateTo("/fxml/Student/statistics/leaderboard.fxml");
    }

    public void settingRoute(MouseEvent event) {
        navigateTo("/fxml/Student/setting/setting.fxml");
        setCurrentPageSafe("settingBtn1");
        displayNavbarSafe();
    }

    public void navigateTo(String fxmlPath) {
        System.out.println("navigateTo called: " + fxmlPath);
        if (currentContentController instanceof DisposableController) {
            ((DisposableController) currentContentController).disposePlayer();
        }
        if (controller != null) {
            controller.displayNavbar();
            controller.displayContent(fxmlPath);
        }
    }

    public void navigateTo(String fxmlPath, int chapterID, int quizID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            QuizPageController quizController = loader.getController();
            quizController.setQuizId(quizID);
            quizController.setChapterID(chapterID);
            quizController.loadQuiz();
            quizController.setSendButton();
            quizController.setQuizDesc();
            if (controller != null) {
                controller.displayNavbar();
                controller.displayContent(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateTo(String fxmlPath, int point, int courseID, QuizItem quizItem, int chapterID) {
        try {
            Object newController = null;
            if (controller != null) {
                controller.displayNavbar();
                if (fxmlPath.contains("quizSummary")) {
                    newController = controller.displayContent(fxmlPath, QuizSummary.class);
                    ((QuizSummary) newController).loadAnwer(point, quizItem, quizItem.getQuizID());
                } else if (fxmlPath.contains("resultPage")) {
                    newController = controller.displayContent(fxmlPath, ResultPageController.class);
                    ((ResultPageController) newController).loadData(point, courseID, quizItem, chapterID);
                }
                currentContentController = newController;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCurrentContentController(Object controller) {
        currentContentController = controller;
    }

    public static Object getCurrentContentController() {
        return currentContentController;
    }
}
