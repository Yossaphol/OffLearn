package Teacher.showBalance;

import Teacher.navigator.Navigator;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//Swing
import javax.swing.*;
import javax.swing.table.*;



public class withdrawHistoryController implements Initializable {
    public HBox navbarContainer;
    public Label back;
    public HBox tableContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayNavbar();
        route();
        setupTable();
        refreshTable();
    }

    public void setupTable() {
        SwingNode swingNode = new SwingNode();


        SwingUtilities.invokeLater(() -> {
            //Data
            String[] columns = { "Date/Time", "Account No.", "Account Name", "Amount" };
            Object[][] data = {
                    { "2025-03-27 13:30", "123456", "John Doe", "1000.00" },
                    { "2025-03-26 20:04", "654321", "Jane Smith", "500.50" },
                    { "2025-03-25 18:27", "789012", "Alice Brown", "250.75" }
            };


            JTable table = new JTable(new DefaultTableModel(data, columns));
            JScrollPane scrollPane = new JScrollPane(table);
            //Style table
            JTableHeader header = table.getTableHeader();
            header.setBackground(new Color(6, 117, 222));
            header.setForeground(Color.WHITE);

            table.setRowHeight(30);
            table.setGridColor(new Color(6, 117, 222));

            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0) {
                        cell.setBackground(new Color(230, 245, 255));
                    } else {
                        cell.setBackground(Color.WHITE);
                    }
                    if (isSelected) {
                        cell.setBackground(new Color(100, 180, 255));
                        cell.setForeground(Color.BLACK);
                    } else {
                        cell.setForeground(Color.BLACK);
                    }
                    return cell;
                }
            });
            swingNode.setContent(scrollPane);
        });

        javafx.application.Platform.runLater(() -> {
            tableContainer.getChildren().add(swingNode);
        });
    }

    public void refreshTable() {
        tableContainer.getChildren().clear();
        setupTable();
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
