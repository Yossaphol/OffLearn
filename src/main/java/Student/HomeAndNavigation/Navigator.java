package Student.HomeAndNavigation;

import Student.Quiz.QuizPageController;
import Student.Quiz.QuizSummary;
import Student.Quiz.ResultPageController;
import Student.dashboard.dashboardController;
import Student.learningPage.DisposableController;
import Student.Offline.MainPageOffline;
import Student.navBarAndSearchbar.navBarOffline;
import Teacher.quiz.QuizItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import Student.mainPage.mainPageController;
import Student.navBarAndSearchbar.navBarController;
import Student.inbox.pChat.pChatController;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {

    private static mainPageController onlineController;
    private static MainPageOffline offlineController;
    private static navBarController navCtrl;
    private static navBarOffline navCtrlOffline;
    private static Object currentContentController;

    // Setters and getters for the online main controller
    public static void setController(mainPageController ctrl) {
        onlineController = ctrl;
    }

    public static mainPageController getOnlineController() {
        return onlineController;
    }

    // Setters and getters for the offline main controller
    public static void setOfflineController(MainPageOffline ctrl) {
        offlineController = ctrl;
    }

    public static MainPageOffline getCurrentOfflineController() {
        return offlineController;
    }

    public static void setNavBarController(navBarController navController) {
        navCtrl = navController;
    }

    public static void setNavBarController(navBarOffline navController) {
        navCtrlOffline = navController;
    }

    private void setCurrentPageSafe(String id) {
        if (navCtrl != null) navCtrl.setCurrentPage(id);
        if (navCtrlOffline != null) navCtrlOffline.setCurrentPage(id);
    }

    private void displayNavbarSafe() {
        if (onlineController != null) onlineController.displayNavbar();
        if (offlineController != null) offlineController.displayNavbar();
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

            if (onlineController != null) {
                onlineController.displayNavbar();
                onlineController.displayContent(root);
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

    public void QuizResult(int point, int courseID, QuizItem quizItem, int chapterID, DisposableController disposableController) {
        navigateTo(disposableController, "/fxml/Student/Quiz/resultPage.fxml", point, courseID, quizItem, chapterID);
        if (onlineController != null) {
            onlineController.stopHideNavbar();
            onlineController.stopHideSearchBar();
        }
    }

    public void QuizSummary(int point, int courseID, QuizItem quizItem, int chapterID, DisposableController disposableController) {
        navigateTo(disposableController, "/fxml/Student/Quiz/quizSummary.fxml", point, courseID, quizItem, chapterID);
        if (onlineController != null) {
            onlineController.stopHideNavbar();
            onlineController.stopHideSearchBar();
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
        if (onlineController != null) {
            onlineController.hideNavbar();
            onlineController.hideSearchBar();
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
        if (currentContentController instanceof DisposableController disposable) {
            disposable.disposePlayer();
        }
        if (onlineController != null) {
            onlineController.displayNavbar();
            onlineController.displayContent(fxmlPath);
        } else if (offlineController != null) {
            offlineController.displayContent(fxmlPath);
        } else {
            System.err.println("No main controller set!");
        }
    }

    public void navigateTo(Parent root) {
        System.out.println("navigateTo called with Parent: " + root);
        if (currentContentController instanceof DisposableController disposable) {
            System.out.println("Calling dispose on current content controller...");
            disposable.disposePlayer();
        }
        if (onlineController != null) {
            onlineController.displayNavbar();
            onlineController.displayContent(root);
        }
    }

    public void navigateTo(String fxmlPath, int chapterID, int quizID) {
        System.out.println("navigateTo called: " + fxmlPath);

        if (currentContentController instanceof DisposableController disposable) {
            System.out.println("Calling dispose on current content controller...");
            disposable.disposePlayer();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            QuizPageController quizController = loader.getController();
            quizController.setQuizId(quizID);
            quizController.setChapterID(chapterID);
            quizController.loadQuiz();
            quizController.setSendButton();
            quizController.setQuizDesc();
            if (onlineController != null) {
                onlineController.displayNavbar();
                onlineController.displayContent(root);
            } else {
                System.out.println("Navigator Error: online controller is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateTo(DisposableController disposable, String fxmlPath, int point, int courseID, QuizItem quizItem, int chapterID) {
        System.out.println("navigateTo called: " + fxmlPath);

        if (disposable != null) {
            disposable.disposePlayer();
        }

        if (onlineController != null) {
            onlineController.displayNavbar();
            Object newController = null;
            if (fxmlPath.contains("quizSummary")) {
                newController = onlineController.displayContent(fxmlPath, QuizSummary.class);
                ((QuizSummary) newController).loadAnwer(point, quizItem, quizItem.getQuizID());
            } else if (fxmlPath.contains("resultPage")) {
                newController = onlineController.displayContent(fxmlPath, ResultPageController.class);
                ((ResultPageController) newController).loadData(point, courseID, quizItem, chapterID);
            }
            currentContentController = newController;
        }
    }


    public static void setCurrentContentController(Object controller) {
        currentContentController = controller;
    }

    public static Object getCurrentContentController() {
        return currentContentController;
    }

    public void roadmapRoute1(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/courseManage/roadmap.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("สำรวจ Roadmap");
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
