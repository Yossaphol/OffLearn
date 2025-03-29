package Student.HomeAndNavigation;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import Student.mainPage.mainPageController;
import Student.navBarAndSearchbar.navBarController;

public class Navigator {

    private static mainPageController controller;
    private static navBarController navCtrl;

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


    public void testResult() {
        navigateTo("/fxml/Student/test/test_result.fxml");
        controller.stopHideNavbar();
        controller.stopHideSearchBar();
    }

    public void testResult(MouseEvent event) {
        navigateTo("/fxml/Student/test/test_result.fxml");
    }

    public void preTestRoute(MouseEvent event) {
        navigateTo("/fxml/Student/test/preTest.fxml");
    }

    public void courseEnrollRoute(MouseEvent event) {
        navigateTo("/fxml/Student/courseManage/courseEnroll.fxml");
    }

    public void testRoute(MouseEvent event) {
        navigateTo("/fxml/Student/test/testPage.fxml");
        controller.hideNavbar();
        controller.hideSearchBar();
    }

    public void seeAnswer(MouseEvent event) {
        navigateTo("/fxml/Student/test/answerPage.fxml");
    }

    public void myCourseRoute(MouseEvent event) {
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
        if (controller != null) {
            controller.displayNavbar();
            controller.displayContent(fxmlPath);
        } else {
            System.out.println("Navigator Error");
        }

    }
}

