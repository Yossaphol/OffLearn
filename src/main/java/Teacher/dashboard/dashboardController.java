package Teacher.dashboard;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Student.HomeAndNavigation.HomeController;
import Teacher.navigator.Navigator;
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
    public VBox month_selector;
    public Button month_select;
    public Button edit_profile;
    public Button save_change;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HomeController effectMethod = new HomeController();
        effectMethod.hoverEffect(course_manageBtn);
        effectMethod.hoverEffect(withdrawBtn);
        effectMethod.hoverEffect(course_btn);
        effectMethod.hoverEffect(withdraw_btn1);
        effectMethod.hoverEffect(save_change);

        effectMethod.applyHoverEffectToInside(month_selector);

        hoverEffect(dashboard_profile);
        hoverEffect(linechart_container);
        hoverEffect(piechartContainer);
        hoverEffect(lowScore_container);
        hoverEffect(revenue_container);
        hoverEffect(course_container_table);
        hoverEffect(overview_revenue_container);

        save_change.setVisible(false);
        closePopupAuto();

        displayNavbar();
        displayProfileBox();
        displayCourseInTable();
        setupRevenueChart();
        setupLinechartEnroll();
        setupStdChart();
        route();

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


    private void route(){
        Navigator nav = new Navigator();


        //Course
        course_btn.setOnMouseClicked(nav::courseRoute);
        course_manageBtn.setOnMouseClicked(nav::courseRoute);


        withdrawBtn.setOnMouseClicked(nav::withdrawRoute);
        withdraw_btn1.setOnMouseClicked(nav::withdrawRoute);


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


    @FXML
    private void openPopup(ActionEvent event){
        Button clickedbtn = (Button) event.getSource();
        switch (clickedbtn.getId()){
            case "month_select":
                _openPopup(month_selector);
                break;
        }

    }

    @FXML
    private void month_selected(ActionEvent event){
        Button clickedbtn = (Button) event.getSource();
        String text = clickedbtn.getText();
        month_select.setText(text);
    }


    @FXML
    private void _openPopup(Node popup) {
        popup.setViewOrder(-1);
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);

        if (!popup.isVisible()) {
            popup.setVisible(true);
            popup.setOpacity(0);
            fade.setFromValue(0);
            fade.setToValue(1);


        } else {
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> popup.setVisible(false));
        }

        fade.play();
    }

    public void closePopupAuto() {
        month_selector.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    if (month_selector.isVisible() && !month_selector.isHover()) {
                        closePopup(month_selector);
                    }
                });
            }
        });
    }


    private void closePopup(Node popup) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), popup);

        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> popup.setVisible(false));

        fade.play();
    }


    @FXML
    private void editProfile(ActionEvent event){
        Button btn = (Button) event.getSource();
        switch (btn.getId()){
            case "edit_profile":
                edit_profile.setVisible(false);
                save_change.setVisible(true);
                displayProfileBoxEdit();
                break;
            case "save_change":
                save_change.setVisible(false);
                edit_profile.setVisible(true);
                displayProfileBox();
                break;
            default:
                save_change.setVisible(false);
                edit_profile.setVisible(true);
                displayProfileBox();
        }
    }

    private void displayProfileBox() {
        loadWithTransition("/fxml/Teacher/statistics/dashboardProfile.fxml");
    }

    private void displayProfileBoxEdit() {
        loadWithTransition("/fxml/Teacher/statistics/dashboardProfileEdit.fxml");
    }

    private void loadWithTransition(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            HBox newContent = loader.load();

            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), dashboard_profile);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                dashboard_profile.getChildren().setAll(newContent);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), dashboard_profile);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
