package Student.dashboard;

import Database.EnrollDB;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import java.io.*;
import java.nio.file.*;

import javafx.util.Duration;
import org.json.*;

public class studyTableEditController implements Initializable{

    public TableView<ScheduleItem> list_table;
    public TableColumn<ScheduleItem, String> list_id;
    public TableColumn<ScheduleItem, String> list_name;
    public TableColumn<ScheduleItem, String> list_day;
    public TableColumn<ScheduleItem, String> list_start;
    public TableColumn<ScheduleItem, String> list_stop;
    public TableColumn<ScheduleItem, String> list_bgcolor;
    public TableColumn<ScheduleItem, String> list_tcolor;
    public ComboBox<Integer> id;
    public Button delete;
    public ComboBox<String> name;
    public ComboBox<String> start;
    public ColorPicker bgcolor;
    public ColorPicker tcolor;
    public ComboBox<String> day;
    public ComboBox<String> stop;
    public Button add;
    public Button clear;
    public Button done;

    private ObservableList<TimeBlock> occupiedTimes = FXCollections.observableArrayList();
    private ObservableList<ScheduleItem> tableData = FXCollections.observableArrayList();

    private static final String DATA_FILE = "studyTable.json";
    private int fromHour = 8;
    private int toHour = 17;

    private int currentUserID;

    public void setCurrentUserID(int userID) {
        this.currentUserID = userID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateItem();
        fillCourseNameComboBox();

        list_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        list_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        list_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        list_start.setCellValueFactory(new PropertyValueFactory<>("start"));
        list_stop.setCellValueFactory(new PropertyValueFactory<>("stop"));
        list_bgcolor.setCellValueFactory(new PropertyValueFactory<>("bgcolor"));
        list_tcolor.setCellValueFactory(new PropertyValueFactory<>("tcolor"));
        setEffect();

        loadData();

        list_table.setItems(tableData);

        updateIdComboBox();
    }

    private void fillCourseNameComboBox() {
        EnrollDB enrollDB = new EnrollDB();
        int userID = currentUserID > 0 ? currentUserID : 1;
        List<String> courseNames = enrollDB.getEnrolledCourseNames(userID);
        name.setItems(FXCollections.observableArrayList(courseNames));
    }

    private void loadData() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                return;
            }

            String content = new String(Files.readAllBytes(Paths.get(DATA_FILE)));
            JSONObject jsonData = new JSONObject(content);

            if (jsonData.has("fromHour")) {
                fromHour = jsonData.getInt("fromHour");
            }
            if (jsonData.has("toHour")) {
                toHour = jsonData.getInt("toHour");
            }

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
        }
    }

    private void saveData() {
        try {
            JSONObject jsonData = new JSONObject();

            int minStartHour = calculateMinStartHour();
            int maxStopHour = calculateMaxStopHour();

            if (tableData.isEmpty()) {
                minStartHour = 0;
                maxStopHour = 0;
            }

            jsonData.put("fromHour", minStartHour);
            jsonData.put("toHour", maxStopHour);

            JSONArray items = new JSONArray();
            for (ScheduleItem item : tableData) {
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("name", item.getName());
                jsonItem.put("day", item.getDay());

                String start = item.getStart().split(":")[0];
                String stop = item.getStop().split(":")[0];

                jsonItem.put("start", start);
                jsonItem.put("stop", stop);
                jsonItem.put("bgcolor", item.getBgcolor());
                jsonItem.put("tcolor", item.getTcolor());
                items.put(jsonItem);
            }
            jsonData.put("scheduleItems", items);

            try (FileWriter file = new FileWriter(DATA_FILE)) {
                file.write(jsonData.toString(2));
            }

        } catch (Exception e) {
            System.err.println("Error saving data: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Error");
            alert.setHeaderText("Error Saving Data");
            alert.setContentText("Failed to save schedule data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private int calculateMinStartHour() {
        if (tableData.isEmpty()) {
            return fromHour;
        }

        int minHour = 24;

        for (ScheduleItem item : tableData) {
            String startStr = item.getStart();
            int startHour = Integer.parseInt(startStr.split(":")[0]);
            if (startHour < minHour) {
                minHour = startHour;
            }
        }

        return minHour;
    }

    private int calculateMaxStopHour() {
        if (tableData.isEmpty()) {
            return toHour;
        }

        int maxHour = 0;

        for (ScheduleItem item : tableData) {
            String stopStr = item.getStop();
            int stopHour = Integer.parseInt(stopStr.split(":")[0]);
            if (stopHour > maxHour) {
                maxHour = stopHour;
            }
        }

        return maxHour;
    }

    public void generateItem() {
        ObservableList<String> timeOptions = FXCollections.observableArrayList();
        for (int hour = 0; hour <= 24; hour++) {
            String time = String.format("%02d:00", hour);
            timeOptions.add(time);
        }

        start.setItems(timeOptions);
        stop.setItems(timeOptions);

        ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        );

        day.setItems(daysOfWeek);
    }

    @FXML
    private void handleAdd() {
        if (!validateInputs()) {
            return;
        }

        String startStr = start.getSelectionModel().getSelectedItem();
        String stopStr = stop.getSelectionModel().getSelectedItem();
        Color selectedbgColor = bgcolor.getValue();
        Color selectedtColor = tcolor.getValue();

        if (startStr == null || stopStr == null || selectedbgColor == null || selectedtColor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select the day and time.");
            alert.showAndWait();
            return;
        }

        int startHour = Integer.parseInt(startStr.split(":")[0]);
        int stopHour = Integer.parseInt(stopStr.split(":")[0]);

        if (!validateTime(startHour, stopHour)) {
            return;
        }


        if (hasTimeConflict(startHour, stopHour)) {
            return;
        }

        ScheduleItem newItem = new ScheduleItem(
                tableData.size() + 1,
                name.getSelectionModel().getSelectedItem(),
                day.getSelectionModel().getSelectedItem(),
                startStr,
                stopStr,
                selectedbgColor.toString(),
                selectedtColor.toString()
        );
        tableData.add(newItem);
        occupiedTimes.add(new TimeBlock(startHour, stopHour));

        updateTableIds();
    }

    @FXML
    private void handleDelete() {
        if (id.getSelectionModel().isEmpty() || tableData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Deletion Unsuccessful");
            alert.setContentText("There is no row in the table or the id ComboBox is unselected.");
            alert.showAndWait();
            return;
        }

        Integer selectedId = id.getSelectionModel().getSelectedItem();

        int indexToDelete = -1;
        for (int i = 0; i < tableData.size(); i++) {
            if (tableData.get(i).getId().equals(String.valueOf(selectedId))) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete != -1) {
            tableData.remove(indexToDelete);
            occupiedTimes.remove(indexToDelete);

            updateTableIds();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Deletion Unsuccessful");
            alert.setContentText("Item with selected ID not found in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleClear() {
        if (tableData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Clear Table");
            alert.setHeaderText("Table is already empty");
            alert.setContentText("No rows to clear.");
            alert.showAndWait();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Clear Table");
        confirmation.setHeaderText("Confirm Clear Table");
        confirmation.setContentText("Are you sure you want to clear all rows from the table?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                tableData.clear();
                occupiedTimes.clear();

                updateTableIds();
            }
        });
    }

    @FXML
    private void handleDone() {
        saveData();
        try {
            Stage stage = (Stage) done.getScene().getWindow();
            Stage mainStage = (Stage) stage.getOwner();
            if (mainStage != null) {
                studyTableController controller = findStudyTableController(mainStage.getScene());
                if (controller != null) {
                    controller.initialize(null, null);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reinitializing study table: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Stage stage = (Stage) done.getScene().getWindow();
            stage.close();
        }
    }

    private studyTableController findStudyTableController(Scene scene) {
        if (scene != null && scene.getRoot() != null) {
            Object controller = scene.getUserData();
            if (controller instanceof studyTableController) {
                return (studyTableController) controller;
            }
        }
        return null;
    }

    private void updateIdComboBox() {
        ObservableList<Integer> ids = FXCollections.observableArrayList();
        for (int i = 1; i <= tableData.size(); i++) {
            ids.add(i);
        }
        id.setItems(ids);
    }

    private void updateTableIds() {
        for (int i = 0; i < tableData.size(); i++) {
            tableData.get(i).setId(String.valueOf(i + 1));
        }
        list_table.refresh();
        updateIdComboBox();
    }

    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();
        if (name.getSelectionModel().isEmpty()) {
            errors.append("Name selection is empty\n");
        }
        if (day.getSelectionModel().isEmpty()) {
            errors.append("Day selection is empty\n");
        }
        if (start.getSelectionModel().isEmpty()) {
            errors.append("Start time selection is empty\n");
        }
        if (stop.getSelectionModel().isEmpty()) {
            errors.append("Stop time selection is empty\n");
        }
        if (bgcolor.getValue() == null) {
            errors.append("Background Color selection is empty\n");
        }
        if (tcolor.getValue() == null) {
            errors.append("Text Color selection is empty\n");
        }

        if (!errors.toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please fix these errors:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateTime(int startHour, int stopHour) {
        if (stopHour <= startHour) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Time Validation Error");
            alert.setHeaderText("Invalid time range:");
            alert.setContentText("Stop time must be after start time");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean hasTimeConflict(int startHour, int stopHour) {
        String currentDay = day.getSelectionModel().getSelectedItem();

        for (ScheduleItem existingItem : tableData) {
            if (currentDay.equals(existingItem.getDay())) {
                String existingStartStr = existingItem.getStart();
                String existingStopStr = existingItem.getStop();
                int existingStartHour = Integer.parseInt(existingStartStr.split(":")[0]);
                int existingStopHour = Integer.parseInt(existingStopStr.split(":")[0]);

                if (startHour < existingStopHour && stopHour > existingStartHour) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Time Conflict");
                    alert.setHeaderText("Time slot is already occupied");
                    alert.setContentText("Conflicting with existing schedule");
                    alert.showAndWait();
                    return true;
                }
            }
        }
        return false;
    }

    public void hoverEffect(Button btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.05);
        scaleDown.setFromY(1.05);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
            dropShadow.setColor(Color.web("#8100CC", 0.25));
            btn.setEffect(dropShadow);
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
            dropShadow.setColor(Color.web("#c4c4c4", 0.25));
            btn.setEffect(dropShadow);
            scaleDown.play();
        });
    }

    private void setEffect(){
        hoverEffect(delete);
        hoverEffect(clear);
        hoverEffect(add);
        hoverEffect(done);
    }

    private static class TimeBlock {
        private final int startHour;
        private final int stopHour;

        public TimeBlock(int startHour, int stopHour) {
            this.startHour = startHour;
            this.stopHour = stopHour;
        }

        public int getStartHour() {
            return startHour;
        }

        public int getStopHour() {
            return stopHour;
        }
    }
}
