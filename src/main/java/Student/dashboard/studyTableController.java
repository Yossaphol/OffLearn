package Student.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.shape.Line;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import java.io.*;
import java.nio.file.*;
import java.util.stream.Collectors;

import org.json.*;

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

    private static final String DATA_FILE = "studyTable.json";

    public int fromHour = 8;
    public int toHour = 17;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDataFromFile();

        clearDayContainers();

        int width = 134 * (1 + range.get(1) - range.get(0));
        setWidthAll(width);

        addHour(range.get(0), range.get(1));
        addContent();
    }

    private void loadDataFromFile() {
        try {
            tableData.clear();
            range.clear();

            File file = new File(DATA_FILE);
            if (!file.exists()) {
                range.add(8);
                range.add(17);
                return;
            }

            String content = new String(Files.readAllBytes(Paths.get(DATA_FILE)));
            JSONObject jsonData = new JSONObject(content);

            int fromHour = jsonData.getInt("fromHour");
            int toHour = jsonData.getInt("toHour");
            range.add(fromHour);
            range.add(toHour);

            JSONArray items = jsonData.getJSONArray("scheduleItems");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                ScheduleItem scheduleItem = new ScheduleItem(
                        i + 1,
                        item.getString("name"),
                        item.getString("day"),
                        item.getString("start"),
                        item.getString("stop"),
                        item.getString("bgcolor"),
                        item.getString("tcolor")
                );
                tableData.add(scheduleItem);
            }

        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();

            range.clear();
            range.add(8);
            range.add(17);
        }
    }

    private void clearDayContainers() {

        day1_container.getChildren().clear();
        day2_container.getChildren().clear();
        day3_container.getChildren().clear();
        day4_container.getChildren().clear();
        day5_container.getChildren().clear();
        day6_container.getChildren().clear();
        day7_container.getChildren().clear();
        hour_container.getChildren().clear();
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
        Label st = new Label();
        st.setPrefSize(134, 24);
        st.setText("Study Table");
        st.setStyle(String.format(
                "-fx-font-size: 18; " +
                        "-fx-text-fill: #8100cc; " +
                        "-fx-font-weight: bold; " +
                        "-fx-alignment: CENTER;"
        ));
        hour_container.getChildren().add(st);
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

        String bgColorCSS = convertToCSS(bgcolor);
        String textColorCSS = convertToCSS(tcolor);

        subject.setStyle(String.format(
                "-fx-font-size: 14; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: %s; " +
                        "-fx-background-color: %s; " +
                        "-fx-background-radius: 10; " +
                        "-fx-alignment: CENTER;",
                textColorCSS, bgColorCSS));

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

    private String convertToCSS(String colorString) {
        if (colorString.startsWith("0x")) {
            return "#" + colorString.substring(2, 8);
        } else if (colorString.startsWith("Color[")) {
            try {
                String[] parts = colorString.substring(6, colorString.length() - 1).split(",");
                double r = Double.parseDouble(parts[0].split("=")[1].trim());
                double g = Double.parseDouble(parts[1].split("=")[1].trim());
                double b = Double.parseDouble(parts[2].split("=")[1].trim());

                String hex = String.format("#%02X%02X%02X",
                        (int)(r * 255), (int)(g * 255), (int)(b * 255));
                return hex;
            } catch (Exception e) {
                return colorString;
            }
        }
        return colorString;
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

        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};


        for (String currentDay : days) {
            List<ScheduleItem> dayItems = tableData.stream()
                    .filter(item -> item.getDay().toLowerCase().equals(currentDay))
                    .sorted((a, b) -> compareTimes(a.getStart(), b.getStart()))
                    .collect(Collectors.toList());

            int currentHour = range.get(0);

            HBox dayContainer = getDayContainer(currentDay);

            for (ScheduleItem item : dayItems) {
                int startHour = Integer.parseInt(item.getStart());
                int stopHour = Integer.parseInt(item.getStop());

                if (startHour > currentHour) {
                    addSpace(currentDay, String.valueOf(currentHour), String.valueOf(startHour));
                }

                // Add the subject
                addSubject(
                        item.getName(),
                        item.getDay(),
                        item.getStart(),
                        item.getStop(),
                        item.getBgcolor(),
                        item.getTcolor()
                );

                currentHour = stopHour;
            }

            if (currentHour < range.get(1)) {
                addSpace(currentDay, String.valueOf(currentHour), String.valueOf(range.get(1)));
            }
        }
    }

    private HBox getDayContainer(String day) {
        switch (day.toLowerCase()) {
            case "sunday": return day1_container;
            case "monday": return day2_container;
            case "tuesday": return day3_container;
            case "wednesday": return day4_container;
            case "thursday": return day5_container;
            case "friday": return day6_container;
            case "saturday": return day7_container;
            default: return null;
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