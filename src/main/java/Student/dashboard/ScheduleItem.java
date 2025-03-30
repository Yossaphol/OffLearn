package Student.dashboard;

import javafx.beans.property.SimpleStringProperty;

public class ScheduleItem {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty day;
    private final SimpleStringProperty start;
    private final SimpleStringProperty stop;
    private final SimpleStringProperty bgcolor;
    private final SimpleStringProperty tcolor;

    public ScheduleItem(int id, String name, String day, String start, String stop, String bgcolor, String tcolor) {
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.name = new SimpleStringProperty(name);
        this.day = new SimpleStringProperty(day);
        this.start = new SimpleStringProperty(start);
        this.stop = new SimpleStringProperty(stop);
        this.bgcolor = new SimpleStringProperty(bgcolor);
        this.tcolor = new SimpleStringProperty(tcolor);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String newId) {
        this.id.set(newId);
    }

    public String getName() {
        return name.get();
    }

    public String getDay() {
        return day.get();
    }

    public String getStart() {
        return start.get();
    }

    public String getStop() {
        return stop.get();
    }
    public String getBgcolor() {
        return bgcolor.get();
    }

    public String getTcolor() {
        return tcolor.get();
    }
}
