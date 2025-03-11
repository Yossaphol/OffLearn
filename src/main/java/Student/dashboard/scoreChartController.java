package Student.dashboard;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class scoreChartController implements Initializable {
    public LineChart score_development;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreDevelopmentChart();
    }

    public void scoreDevelopmentChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("Quiz1", 100));
        series.getData().add(new XYChart.Data<>("Quiz2", 300));
        series.getData().add(new XYChart.Data<>("Quiz3", 200));
        series.getData().add(new XYChart.Data<>("Quiz4", 500));
        series.getData().add(new XYChart.Data<>("Quiz5", 480));

        score_development.getData().add(series);
        score_development.setLegendVisible(false);

        Platform.runLater(() -> {
            Node line = series.getNode().lookup(".chart-series-line");
            if (line != null) {
                line.setStyle("-fx-stroke: #8100cc; -fx-stroke-width: 2px;");
            }

            for (XYChart.Data<String, Number> data : series.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    node.setStyle("-fx-background-color: transparent; -fx-shape: none;");
                }
            }
        });
    }


}
