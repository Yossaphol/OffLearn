package Teacher.showBalance;

import Teacher.navigator.Navigator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//Swing
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javafx.embed.swing.SwingNode;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class withdrawHistoryController implements Initializable {
    public HBox navbarContainer;
    public Label back;
    public HBox tableContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();
        route();
        setupTable();
    }

    public void setupTable() {
        SwingNode swingNode = new SwingNode();
        javafx.application.Platform.runLater(() -> {
            String[] columns = { "Date", "Account No.", "Account Name", "Amount" };
            Object[][] data = {
                    { "2025-03-27", "123456", "John Doe", "$1000.00" },
                    { "2025-03-26", "654321", "Jane Smith", "$500.50" },
                    { "2025-03-25", "789012", "Alice Brown", "$250.75" }
            };

            JTable table = new JTable(new DefaultTableModel(data, columns));
            JScrollPane scrollPane = new JScrollPane(table);
            swingNode.setContent(scrollPane);
        });

        tableContainer.getChildren().add(swingNode);
    }


    private void route(){
        Navigator nav = new Navigator();
        back.setOnMouseClicked(nav::withdrawRoute);
    }

    public void displayNavbar(){
        try {
            FXMLLoader calendarLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/navBar/navBar.fxml"));
            HBox navContent = calendarLoader.load();
            navbarContainer.getChildren().setAll(navContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
