package Student.HomeAndNavigation;

import Student.Quiz.QuizPageController;
import Student.Quiz.QuizSummary;
import Student.Quiz.ResultPageController;
import Student.learningPage.DisposableController;
import Teacher.quiz.QuizController;
import Teacher.quiz.QuizItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import Student.mainPage.mainPageController;
import Student.navBarAndSearchbar.navBarController;

import java.io.IOException;

public class Navigator {

    private static mainPageController controller;
    private static navBarController navCtrl;
    private static Object currentContentController;

    public static void setController(mainPageController ctrl) {
        controller = ctrl;
    }

    public static void setNavBarController(navBarController navController) {
        navCtrl = navController;
    }

    public void homeRoute(MouseEvent event) {
        navigateTo("/fxml/Student/HomePage/home.fxml");
        navCtrl.setCurrentPage("home_btn");
        controller.displayNavbar();
    }

    public void dashboardRoute(MouseEvent event) {
        navigateTo("/fxml/Student/statistics/dashboard.fxml");
        navCtrl.setCurrentPage("dashboard_btn");
        controller.displayNavbar();
    }

    public void courseRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/course.fxml");
        navCtrl.setCurrentPage("course_btn");
        controller.displayNavbar();
    }

    public void inboxRoute(MouseEvent event) {
        navigateTo("/fxml/Student/inbox/pChat.fxml");
        navCtrl.setCurrentPage("inbox_btn");
        controller.displayNavbar();
    }

    public void taskRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/task.fxml");
        navCtrl.setCurrentPage("task_btn");
        controller.displayNavbar();
    }


    public void roadmapRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/roadmap.fxml");
        navCtrl.setCurrentPage("roadmap_btn");
        controller.displayNavbar();
    }

    public void learningPageRoute(MouseEvent event) {
        navigateTo("/fxml/Student/learningPage/learningPage.fxml");
        controller.displayNavbar();
    }

    public void learningPageRoute(ActionEvent event) {
        navigateTo("/fxml/Student/learningPage/learningPage.fxml");
        controller.displayNavbar();
    }


    public void QuizPage(int chapterID, int quizID) {
        navigateTo("/fxml/Student/Quiz/quizPage.fxml", chapterID, quizID);
    }


    public void QuizResult(int point, int courseID, QuizItem quizItem, int chapterID) {
        navigateTo("/fxml/Student/Quiz/resultPage.fxml", point, courseID, quizItem, chapterID);

        controller.stopHideNavbar();
        controller.stopHideSearchBar();

    }

    public void QuizSummary(int point, int courseID, QuizItem quizItem, int chapterID) {
        navigateTo("/fxml/Student/Quiz/quizSummary.fxml", point, courseID, quizItem, chapterID);

        controller.stopHideNavbar();
        controller.stopHideSearchBar();

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
        controller.hideNavbar();
        controller.hideSearchBar();
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
        navCtrl.setCurrentPage("settingBtn1");
        controller.displayNavbar();
    }

    public void navigateTo(String fxmlPath) {
        System.out.println("navigateTo called: " + fxmlPath);
        if (currentContentController instanceof DisposableController) {
            System.out.println("Calling dispose on current content controller...");
            ((DisposableController) currentContentController).disposePlayer();
        }
        if (controller != null) {
            controller.displayNavbar();
            controller.displayContent(fxmlPath);
        } else {
            System.out.println("Navigator Error");
        }
    }

    public void navigateTo(String fxmlPath, int chapterID, int quizID) {
        System.out.println("navigateTo called: " + fxmlPath);

        if (currentContentController instanceof DisposableController) {
            System.out.println("Calling dispose on current content controller...");
            ((DisposableController) currentContentController).disposePlayer();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if (fxmlPath.equals("/fxml/Student/Quiz/quizPage.fxml")) {
                QuizPageController quizController = loader.getController();
                quizController.setQuizId(quizID);
                quizController.setChapterID(chapterID);
                quizController.loadQuiz();
                quizController.setSendButton();
                quizController.setQuizDesc();
            }

            if (controller != null) {
                controller.displayNavbar();
                controller.displayContent(root);
            } else {
                System.out.println("Navigator Error: controller is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public enum ControllerType {
        QUIZ_SUMMARY,
        QUIZ_RESULT
    }

    public void navigateTo(String fxmlPath, int point, int courseID, QuizItem quizItem, int chapterID) {
        System.out.println("navigateTo called: " + fxmlPath);

        if (currentContentController instanceof DisposableController) {
            System.out.println("Calling dispose on current content controller...");
            ((DisposableController) currentContentController).disposePlayer();
        }

        if (controller != null) {
            controller.displayNavbar();
            Object newController = null;
            if (fxmlPath.equals("/fxml/Student/Quiz/quizSummary.fxml")) {
                newController = controller.displayContent(fxmlPath, QuizSummary.class);
            } else if (fxmlPath.equals("/fxml/Student/Quiz/resultPage.fxml")) {
                newController = controller.displayContent(fxmlPath, ResultPageController.class);
            }

            if (newController instanceof ResultPageController) {
                ((ResultPageController) newController).loadData(point, courseID, quizItem, chapterID);
                currentContentController = newController;
                System.out.println("Loaded ResultPageController successfully.");
            } else if (newController instanceof QuizSummary){
                ((QuizSummary) newController).loadAnwer(point, quizItem, quizItem.getQuizID());
                currentContentController = newController;
                System.out.println("Loaded ResultPageController successfully.");
            } else {
                System.out.println("Controller is null or incorrect type.");
            }
        } else {
            System.out.println("Navigator Error");
        }
    }


    public static void setCurrentContentController(Object controller) {
        currentContentController = controller;
    }
}

