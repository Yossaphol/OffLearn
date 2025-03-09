package Teacher.dashboard;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Student.HomeAndNavigation.HomeController;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class dashboardController implements Initializable {
    public HBox searhbar_container;
    public HBox dashboard_profile;
    public HBox course_container_table;
    public BarChart revenueChart;
    public CategoryAxis xAxis_revenue;
    public NumberAxis yAxis_revenue;
    public LineChart lineChart_enroll;
    public PieChart pie_chart_std;
    public Button course_manageBtn;
    public Button withdrawBtn;
    public Button course_btn;
    public Button withdraw_btn1;
    public HBox linechart_container;
    public VBox piechartContainer;
    public VBox lowScore_container;
    public VBox revenue_container;
    public VBox overview_revenue_container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HomeController effectMethod = new HomeController();
        effectMethod.hoverEffect(course_manageBtn);
        effectMethod.hoverEffect(withdrawBtn);
        effectMethod.hoverEffect(course_btn);
        effectMethod.hoverEffect(withdraw_btn1);

        hoverEffect(dashboard_profile);
        hoverEffect(linechart_container);
        hoverEffect(piechartContainer);
        hoverEffect(lowScore_container);
        hoverEffect(revenue_container);
        hoverEffect(course_container_table);
        hoverEffect(overview_revenue_container);

        displayNavbar();
        displayProfileBox();
        displayCourseInTable();
        setupRevenueChart();
        setupLinechartEnroll();
        setupStdChart();

    }

    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            searhbar_container.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayProfileBox(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/statistics/dashboardProfile.fxml"));
            HBox navContent = calendarLoader.load();
            dashboard_profile.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayCourseInTable(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/statistics/courseInTable.fxml"));
            HBox navContent = calendarLoader.load();
            course_container_table.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupRevenueChart() {
        if (xAxis_revenue == null || yAxis_revenue == null || revenueChart == null) {
            System.out.println("FXML components not setup properly!");
            return;
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Sales");

        series.getData().add(new XYChart.Data<>("Course A", 50));
        series.getData().add(new XYChart.Data<>("Course B", 70));
        series.getData().add(new XYChart.Data<>("Course C", 30));

        revenueChart.setLegendVisible(false);
        revenueChart.getData().add(series);

        Platform.runLater(() -> {
            series.getData().get(0).getNode().setStyle("-fx-bar-fill: #3498db;");
            series.getData().get(1).getNode().setStyle("-fx-bar-fill: #e74c3c;");
            series.getData().get(2).getNode().setStyle("-fx-bar-fill: #2ecc71;");
        });
    }

    public void setupLinechartEnroll() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Enrollment");


        series.getData().add(new XYChart.Data<>("Course1", 30));
        series.getData().add(new XYChart.Data<>("Course2", 50));
        series.getData().add(new XYChart.Data<>("Course3", 10));
        series.getData().add(new XYChart.Data<>("Course4", 30));
        series.getData().add(new XYChart.Data<>("Course5", 60));

        lineChart_enroll.setLegendVisible(false);
        lineChart_enroll.setCreateSymbols(false);
        lineChart_enroll.getData().clear();
        lineChart_enroll.getData().add(series);

        Platform.runLater(() -> {
            series.getNode().setStyle("-fx-stroke: #0675de; -fx-stroke-width: 2px;"); // Change line color
        });
    }

    public void setupStdChart() {
        PieChart.Data slice1 = new PieChart.Data("Pass", 64);
        PieChart.Data slice2 = new PieChart.Data("Fail", 36);

        pie_chart_std.getData().addAll(slice1, slice2);
        pie_chart_std.setLegendVisible(false);
        slice1.getNode().setStyle("-fx-pie-color: #3498db;");
        slice2.getNode().setStyle("-fx-pie-color: #e74c3c;");

    }

    public void hoverEffect(HBox vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT);
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#0675de", 0.25)))
            );
            timeline.play();
        });

        vBox.setOnMouseExited(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), dropShadow.getColor())),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT))
            );
            timeline.play();
        });
    }

    public void hoverEffect(VBox vBox) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.TRANSPARENT);
        vBox.setEffect(dropShadow);

        vBox.setOnMouseEntered(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.web("#0675de", 0.25)))
            );
            timeline.play();
        });

        vBox.setOnMouseExited(mouseEvent -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.colorProperty(), dropShadow.getColor())),
                    new KeyFrame(Duration.millis(200), new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT))
            );
            timeline.play();
        });
    }

}
