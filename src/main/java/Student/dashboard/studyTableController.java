package Student.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class studyTableController implements Initializable {
    public HBox content_pane;
    public VBox content_container;
    public HBox hour_container;
    public VBox TIme0;
    public HBox day1_container;
    public VBox Time1;
    public HBox day2_container;
    public VBox Time2;
    public HBox day3_container;
    public VBox Time3;
    public HBox day4_container;
    public VBox Time4;
    public HBox day5_container;
    public VBox Time5;
    public HBox day6_container;
    public VBox Time6;
    public HBox day7_container;
    public Label Open;
    public Line l1;
    public Line l2;
    public Line l3;
    public Line l4;
    public Line l5;
    public Line l6;
    public Line l7;
    public Line l8;

    private ObservableList<ScheduleItem> tableData = FXCollections.observableArrayList();
    private ObservableList<Integer> range = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScheduleItem subject1 = new ScheduleItem(1, "One", "Monday", "09", "10", "Red", "White");
        ScheduleItem subject2 = new ScheduleItem(2, "Two", "Friday", "09", "12", "Blue", "White");
        ScheduleItem subject3 = new ScheduleItem(3, "Three", "Monday", "10", "11", "Green", "White");

        tableData.addAll(subject1, subject2, subject3);
        range.addAll(7, 17);

        setWidthAll(134 * (1 + range.get(1) - range.get(0)));
        addHour(range.get(0), range.get(1));
        addContent();
    }

    public void setWidthAll(double width) {
        content_pane.setPrefWidth(width);
        content_container.setPrefWidth(width);
        hour_container.setPrefWidth(width);
        TIme0.setPrefWidth(width);
        day1_container.setPrefWidth(width);
        Time1.setPrefWidth(width);
        day2_container.setPrefWidth(width);
        Time2.setPrefWidth(width);
        day3_container.setPrefWidth(width);
        Time3.setPrefWidth(width);
        day4_container.setPrefWidth(width);
        Time4.setPrefWidth(width);
        day5_container.setPrefWidth(width);
        Time5.setPrefWidth(width);
        day6_container.setPrefWidth(width);
        Time6.setPrefWidth(width);
        day1_container.setPrefWidth(width);
        day2_container.setPrefWidth(width);
        day3_container.setPrefWidth(width);
        day4_container.setPrefWidth(width);
        day5_container.setPrefWidth(width);
        day6_container.setPrefWidth(width);
        day7_container.setPrefWidth(width);
        l1.setEndX(Math.max(width - 20, 995));
        l2.setEndX(Math.max(width - 20, 995));
        l3.setEndX(Math.max(width - 20, 995));
        l4.setEndX(Math.max(width - 20, 995));
        l5.setEndX(Math.max(width - 20, 995));
        l6.setEndX(Math.max(width - 20, 995));
        l7.setEndX(Math.max(width - 20, 995));
        l8.setEndX(Math.max(width - 20, 995));
    }

    public void addHour(int start, int stop) {
        for (int i = start; i < stop; i++) {
            Label hour = new Label();
            hour.setPrefSize(134, 24);
            hour.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            hour.setText(String.format("%02d:00", i));
            hour_container.getChildren().add(hour);
        }
    }

    public void addSubject(String name, String day, String start, String stop, String bgcolor, String tcolor) {
        Label subject = new Label();
        subject.setPrefSize(134 * (Integer.parseInt(stop) - Integer.parseInt(start)), 24);
        subject.setStyle(String.format(
                "-fx-font-size: 14; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: %s; " +
                        "-fx-background-color: %s; " +
                        "-fx-background-radius: 10; " +
                        "-fx-alignment: CENTER;",
                tcolor, bgcolor));

        subject.setText(name);

        String dayLower = day.toLowerCase();
        if (dayLower.equals("sunday")) {
            day1_container.getChildren().add(subject);
        } else if (dayLower.equals("monday")) {
            day2_container.getChildren().add(subject);
        } else if (dayLower.equals("tuesday")) {
            day3_container.getChildren().add(subject);
        } else if (dayLower.equals("wednesday")) {
            day4_container.getChildren().add(subject);
        } else if (dayLower.equals("thursday")) {
            day5_container.getChildren().add(subject);
        } else if (dayLower.equals("friday")) {
            day6_container.getChildren().add(subject);
        } else if (dayLower.equals("saturday")) {
            day7_container.getChildren().add(subject);
        } else {
            System.out.println("Invalid day: " + day);
        }
    }

    public void addSpace(String day, String start, String stop) {
        Label subject = new Label();
        subject.setPrefSize(134 * (Integer.parseInt(stop) - Integer.parseInt(start)), 24);

        String dayLower = day.toLowerCase();
        if (dayLower.equals("sunday")) {
            day1_container.getChildren().add(subject);
        } else if (dayLower.equals("monday")) {
            day2_container.getChildren().add(subject);
        } else if (dayLower.equals("tuesday")) {
            day3_container.getChildren().add(subject);
        } else if (dayLower.equals("wednesday")) {
            day4_container.getChildren().add(subject);
        } else if (dayLower.equals("thursday")) {
            day5_container.getChildren().add(subject);
        } else if (dayLower.equals("friday")) {
            day6_container.getChildren().add(subject);
        } else if (dayLower.equals("saturday")) {
            day7_container.getChildren().add(subject);
        } else {
            System.out.println("Invalid day: " + day);
        }
    }

    public void addContent() {
        daylabel();
        sortTableData();

        HBox[] dayContainers = {day1_container, day2_container, day3_container,
                day4_container, day5_container, day6_container, day7_container};
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        int dataIndex = 0;

        // Process each day container
        for (int i = 0; i < dayContainers.length; i++) {
            HBox container = dayContainers[i];
            String currentDay = days[i];

            // Process each hour in the range
            for (int j = range.get(0); j < range.get(1); j++) {
                // Check if we have more data to process
                if (dataIndex < tableData.size()) {
                    ScheduleItem currentItem = tableData.get(dataIndex);

                    // Check if the item belongs to current day
                    if (currentItem.getDay().toLowerCase().equals(currentDay)) {
                        int startHour = Integer.parseInt(currentItem.getStart());

                        // Check if the item starts at current hour
                        if (startHour == j) {
                            // Add the subject
                            addSubject(
                                    currentItem.getName(),
                                    currentItem.getDay(),
                                    currentItem.getStart(),
                                    currentItem.getStop(),
                                    currentItem.getBgcolor(),
                                    currentItem.getTcolor()
                            );

                            // Calculate how many hours to skip
                            int endHour = Integer.parseInt(currentItem.getStop());
                            int duration = endHour - startHour;

                            // Skip hours occupied by this subject
                            j += duration - 1; // -1 because the loop will increment j

                            // Move to next item
                            dataIndex++;
                        } else {
                            // Add empty space for this hour
                            addSpace(currentDay, String.valueOf(j), String.valueOf(j + 1));
                        }
                    } else {
                        // Add empty space for this hour (item is for a different day)
                        addSpace(currentDay, String.valueOf(j), String.valueOf(j + 1));
                    }
                } else {
                    // No more items, add empty space
                    addSpace(currentDay, String.valueOf(j), String.valueOf(j + 1));
                }
            }
        }
    }

    private void sortTableData() {
        tableData.sort((a, b) -> {
            int dayCompare = compareDays(a.getDay(), b.getDay());
            return dayCompare != 0 ? dayCompare :
                    compareTimes(a.getStart(), b.getStart());
        });
    }

    private int compareDays(String dayA, String dayB) {
        return Integer.compare(
                findDayIndex(dayA.toLowerCase()),
                findDayIndex(dayB.toLowerCase())
        );
    }

    private int findDayIndex(String day) {
        String[] days = {"sunday", "monday", "tuesday", "wednesday",
                "thursday", "friday", "saturday"};
        for (int i = 0; i < days.length; i++) {
            if (days[i].equals(day)) return i;
        }
        return -1;
    }

    private int compareTimes(String timeA, String timeB) {
        int hourA = Integer.parseInt(timeA);
        int hourB = Integer.parseInt(timeB);
        return Integer.compare(hourA, hourB);
    }

    public void daylabel() {
        String[] thaiDays = {"อาทิตย์", "จันทร์", "อังคาร", "พุทธ", "พฤหัส", "ศุกร์", "เสาร์"};
        HBox[] dayContainers = {day1_container, day2_container, day3_container, day4_container, day5_container, day6_container, day7_container};

        for (int i = 0; i < thaiDays.length; i++) {
            Label dayLabel = new Label(thaiDays[i]);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setPrefHeight(24.0);
            dayLabel.setPrefWidth(134.0);
            dayLabel.setStyle("-fx-font-name: Noto Sans Thai Bold; -fx-font-size: 14.0; -fx-font-weight: bold;");
            dayContainers[i].getChildren().add(dayLabel);
        }
    }
}


