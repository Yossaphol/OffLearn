package Student.test;

import Student.HomeAndNavigation.Home;
import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class test_resultController implements Initializable {
    public VBox leftWrapper;
    public HBox searchbar_container;
    public Label pretestDescription;
    public Label score_getandmax;
    public Label score_average_percen;
    public Label score_average_testerpercen;
    public BarChart score_average_chart;
    public Circle teacher_pic;
    public Label teacher_name;
    public Label teacher_description;
    public Label like_count;
    public Button do_button_text;
    public Label text;
    public Button seeAnswer;
    public Button startLearning;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPretestDescription("Data Scientist");
        setAvgScoreChart();
        setEffect();
        route();
        setTeacherDetail("Wiraya B.", "Best software engineer", "/img/Profile/man.png");
    }

    public void setTeacherDetail(String name, String description, String picPath){
        teacher_name.setText(name);
        teacher_description.setText(description);
        ef.loadAndSetImage(teacher_pic, picPath);
    }

    private void setPretestDescription(String subjectName){
        text.setText("เริ่มต้นเส้นทางแห่งการเรียนรู้\n" +
                "คุณมีความเข้าใจเกี่ยวกับแขนง "+subjectName+" เป็นอย่างดี");
    }

    private void setEffect(){
        ef.hoverEffect(seeAnswer);
        ef.hoverEffect(startLearning);
    }

    public void setAvgScoreChart() {
        score_average_chart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("0", 17));
        series.getData().add(new XYChart.Data<>("33", 23));
        series.getData().add(new XYChart.Data<>("45", 73));
        series.getData().add(new XYChart.Data<>("65", 43));
        series.getData().add(new XYChart.Data<>("85", 11));
        series.getData().add(new XYChart.Data<>("90", 10));

        score_average_chart.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #8100cc;");
        }

    }

    private void route(){
        Navigator nav = new Navigator();
        seeAnswer.setOnMouseClicked(nav::seeAnswer);
    }

}
