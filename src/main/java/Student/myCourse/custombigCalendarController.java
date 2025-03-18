package Student.myCourse;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.YearMonth;

public class custombigCalendarController {
    @FXML
    private Label headerLabel;

    @FXML
    private GridPane calendarGrid;

    @FXML
    public void initialize() {
        LocalDate today = LocalDate.now();
        headerLabel.setText(today.getMonth().toString() + " " + today.getDayOfMonth() + ", " + today.getYear());


        YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonthValue());
        LocalDate firstDay = yearMonth.atDay(1);
        int startDayOfWeek = firstDay.getDayOfWeek().getValue() % 7;
        int daysInMonth = yearMonth.lengthOfMonth();

        int row = 1;
        int col = startDayOfWeek;
        for (int day = 1; day <= daysInMonth; day++) {
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle(
                    "-fx-font-family: 'Kodchasan'; -fx-font-weight: bold; -fx-text-fill: #000000; " +
                    "-fx-alignment: center; -fx-padding: 20px; -fx-font-size: 20px;"
            );

            dayLabel.setOnMouseEntered(e -> dayLabel.setStyle(
                    dayLabel.getStyle() + "-fx-background-color: #F0F0F0;"
            ));
            dayLabel.setOnMouseExited(e -> dayLabel.setStyle(
                    "-fx-brackground-color: white;"+"-fx-font-family: 'Kodchasan'; -fx-font-weight: bold; -fx-text-fill: #000000; " +
                            "-fx-alignment: center; -fx-padding: 20px;  -fx-font-size: 20px;"

            ));

            dayLabel.setMaxWidth(Double.MAX_VALUE);
            dayLabel.setMaxHeight(Double.MAX_VALUE);

            dayLabel.setMaxWidth(Double.MAX_VALUE);
            calendarGrid.add(dayLabel, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }


        addEventBlock(10, 4);
        addEventBlock(12, 2);
    }

    private void addEventBlock(int startDay, int startCol) { //Function นี้ยังทำงานผิดอยู่ เดะมาแก้น้า
        Label eventBlock = new Label("Event");
        eventBlock.setStyle("-fx-background-color: rgba(6, 117, 222);");

        eventBlock.setStyle(eventBlock.getStyle() + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-text-fill: #FFFFFF;");

        eventBlock.setMinWidth(100);
        GridPane.setColumnIndex(eventBlock, startCol);
        GridPane.setRowIndex(eventBlock, startDay / 7 + 1);
        calendarGrid.getChildren().add(eventBlock);
    }




}
