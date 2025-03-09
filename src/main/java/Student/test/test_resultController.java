package Student.test;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
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
    public BubbleChart score_average_chart;
    public Circle teacher_picture;
    public Label teacher_name;
    public Label teacher_description;
    public Label like_count;
    public Button do_button_text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();
        displaySearchBar();
        setPretestDescription(pretestDescription, "1. First rule\n2. Second rule\n3. Third rule");
    }

    private void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/navBar.fxml"));
            VBox navContent = calendarLoader.load();
            leftWrapper.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displaySearchBar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Student/NavAndSearchbar/searchBar.fxml"));
            HBox searchbarContent = calendarLoader.load();
            searchbar_container.getChildren().setAll(searchbarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPretestDescription(Label label, String text){
        label.setText(text);
    }
}
