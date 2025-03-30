package Student.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class studyTableEditController implements Initializable{

    public ComboBox<String> from;
    public ComboBox<String> to;
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
    public TextField name;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateItem();

        list_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        list_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        list_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        list_start.setCellValueFactory(new PropertyValueFactory<>("start"));
        list_stop.setCellValueFactory(new PropertyValueFactory<>("stop"));
        list_bgcolor.setCellValueFactory(new PropertyValueFactory<>("bgcolor"));
        list_tcolor.setCellValueFactory(new PropertyValueFactory<>("tcolor"));
        // Set table data
        list_table.setItems(tableData);
    }

    public void generateItem() {
        ObservableList<String> timeOptions = FXCollections.observableArrayList();
        for (int hour = 0; hour < 24; hour++) {
            String time = String.format("%02d:00", hour);
            timeOptions.add(time);
        }

        from.setItems(timeOptions);
        to.setItems(timeOptions);
        start.setItems(timeOptions);
        stop.setItems(timeOptions);

        ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        );

        day.setItems(daysOfWeek);
    }
    @FXML
    private void handleAdd() {
        // Validate inputs
        if (!validateInputs()) {
            return;
        }

        // Parse time values
        String startStr = start.getSelectionModel().getSelectedItem();
        String stopStr = stop.getSelectionModel().getSelectedItem();
        String fromStr = from.getSelectionModel().getSelectedItem();
        String toStr = to.getSelectionModel().getSelectedItem();
        Color selectedbgColor = bgcolor.getValue();
        Color selectedtColor = tcolor.getValue();


        if (startStr == null || stopStr == null || fromStr == null || toStr == null || selectedbgColor == null || selectedtColor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select the day and time.");
            alert.showAndWait();
            return;
        }

        String[] startParts = startStr.split(":");
        String[] stopParts = stopStr.split(":");
        String[] fromParts = fromStr.split(":");
        String[] toParts = toStr.split(":");

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int stopHour = Integer.parseInt(stopParts[0]);
        int stopMinute = Integer.parseInt(stopParts[1]);
        int fromHour = Integer.parseInt(fromParts[0]);
        int fromMinute = Integer.parseInt(fromParts[1]);
        int toHour = Integer.parseInt(toParts[0]);
        int toMinute = Integer.parseInt(toParts[1]);

        // Check time validity
        if (!validateTime(startHour, startMinute, stopHour, stopMinute)) {
            return;
        }

        // Check schedule within range
        if (startHour < fromHour || toHour < stopHour) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Range Error");
            alert.setHeaderText("Scheduling out of range");
            alert.setContentText("Subject is not within range.");
            alert.showAndWait();
            return;
        }

        // Check time conflicts
        if (hasTimeConflict(startHour, startMinute, stopHour, stopMinute)) {
            return;
        }

        // Add new schedule item
        ScheduleItem newItem = new ScheduleItem(
                tableData.size() + 1,
                name.getText(),
                day.getSelectionModel().getSelectedItem(),
                startStr,
                stopStr,
                selectedbgColor.toString(),
                selectedtColor.toString()
        );
        tableData.add(newItem);
        occupiedTimes.add(new TimeBlock(startHour, startMinute, stopHour, stopMinute));

        //update the ID after adding schedule
        updateTableIds();
    }
    @FXML
    private void handleDelete() {
        // Check if the id ComboBox is empty or the table is empty
        if (id.getSelectionModel().isEmpty() || tableData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Deletion Unsuccessful");
            alert.setContentText("There is no row in the table or the id ComboBox is unselected.");
            alert.showAndWait();
            return;
        }

        // Get the selected id from the ComboBox
        Integer selectedId = id.getSelectionModel().getSelectedItem();

        // Find the index of the item with the selected id
        int indexToDelete = -1;
        for (int i = 0; i < tableData.size(); i++) {
            if (tableData.get(i).getId().equals(String.valueOf(selectedId))) {
                indexToDelete = i;
                break;
            }
        }

        // If the item is found, delete it
        if (indexToDelete != -1) {
            tableData.remove(indexToDelete);
            occupiedTimes.remove(indexToDelete);

            //update the Table ID after deletion
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

                //update the Table ID after clearing
                updateTableIds();
            }
        });
    }
    @FXML
    private void handleDone() {
        // Get from and to times
        String fromStr = from.getSelectionModel().getSelectedItem();
        String toStr = to.getSelectionModel().getSelectedItem();

        if (fromStr == null || toStr == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select 'From' and 'To' times.");
            alert.showAndWait();
            return;
        }

        String[] fromParts = fromStr.split(":");
        String[] toParts = toStr.split(":");

        int fromHour = Integer.parseInt(fromParts[0]);
        int fromMinute = Integer.parseInt(fromParts[1]);
        int toHour = Integer.parseInt(toParts[0]);
        int toMinute = Integer.parseInt(toParts[1]);

        // List to store IDs of out-of-range subjects
        List<String> outOfRangeSubjects = new ArrayList<>();

        // Check each schedule item
        for (ScheduleItem item : tableData) {
            String startStr = item.getStart();
            String stopStr = item.getStop();

            String[] startParts = startStr.split(":");
            String[] stopParts = stopStr.split(":");

            int startHour = Integer.parseInt(startParts[0]);
            int startMinute = Integer.parseInt(startParts[1]);
            int stopHour = Integer.parseInt(stopParts[0]);
            int stopMinute = Integer.parseInt(stopParts[1]);

            // Check if start or stop time is out of range
            if (startHour < fromHour || stopHour > toHour) {
                outOfRangeSubjects.add(item.getId());
            }
        }

        // If there are out-of-range subjects, show an error message
        if (!outOfRangeSubjects.isEmpty()) {
            String message = "The following subjects are out of range: " + String.join(", ", outOfRangeSubjects);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Range Error");
            alert.setHeaderText("Subjects out of range");
            alert.setContentText(message);
            alert.showAndWait();
            return;
        }

        // If all subjects are within range, close the window
        Stage stage = (Stage) done.getScene().getWindow();
        stage.close();
    }

    // Helper method to update the id ComboBox items
    private void updateIdComboBox() {
        ObservableList<Integer> ids = FXCollections.observableArrayList();
        for (int i = 1; i <= tableData.size(); i++) {
            ids.add(i);
        }
        id.setItems(ids);
    }

    //Helper function to update the table ID
    private void updateTableIds() {
        for (int i = 0; i < tableData.size(); i++) {
            tableData.get(i).setId(String.valueOf(i + 1));
        }
        list_table.refresh(); // Refresh the table to reflect changes
        updateIdComboBox(); // Update the id ComboBox
    }

    // Helper methods
    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();
        if (name.getText().isEmpty()) {
            errors.append("Name field is empty\n");
        }
        if (day.getSelectionModel().isEmpty()) {
            errors.append("Day selection is empty\n");
        }
        if (start.getSelectionModel().isEmpty()) {
            errors.append("Start time selection is empty\n");
        }
        if (from.getSelectionModel().isEmpty()) {
            errors.append("From time selection is empty\n");
        }
        if (to.getSelectionModel().isEmpty()) {
            errors.append("To time selection is empty\n");
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

    private boolean validateTime(int startHour, int startMinute, int stopHour, int stopMinute) {
        StringBuilder errors = new StringBuilder();
        if (stopHour < startHour) {
            errors.append("Stop time must be after start time\n");
        } else if (stopHour == startHour && stopMinute <= startMinute) {
            errors.append("Stop time must be after start time\n");
        }

        if (!errors.toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Time Validation Error");
            alert.setHeaderText("Invalid time range:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean hasTimeConflict(int startHour, int startMinute, int stopHour, int stopMinute) {
        String currentDay = day.getSelectionModel().getSelectedItem();

        for (ScheduleItem existingItem : tableData) {
            if (currentDay.equals(existingItem.getDay())) { // Check if it's the same day
                // Extract hours and minutes from existing item's start and stop times
                String existingStartStr = existingItem.getStart();
                String existingStopStr = existingItem.getStop();
                String[] existingStartParts = existingStartStr.split(":");
                String[] existingStopParts = existingStopStr.split(":");
                int existingStartHour = Integer.parseInt(existingStartParts[0]);
                int existingStopHour = Integer.parseInt(existingStopParts[0]);

                // Check for time conflict
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

    // Helper classes

    private static class TimeBlock {
        private final int startHour;
        private final int startMinute;
        private final int stopHour;
        private final int stopMinute;

        public TimeBlock(int startHour, int startMinute, int stopHour, int stopMinute) {
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.stopHour = stopHour;
            this.stopMinute = stopMinute;
        }

        public int getStartHour() {
            return startHour;
        }

        public int getStartMinute() {
            return startMinute;
        }

        public int getStopHour() {
            return stopHour;
        }

        public int getStopMinute() {
            return stopMinute;
        }
    }
}
