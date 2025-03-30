package Teacher.showBalance;

import Database.EnrollDB;
import Database.UserDB;
import Database.withdraw;
import Database.withdrawDB;
import Student.HomeAndNavigation.HomeController;
import Teacher.dashboard.dashboardController;
import Teacher.navigator.Navigator;
import a_Session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class withdrawDetailController implements Initializable {
    public Label back;
    public VBox changeAccount;
    public VBox currentAccount;
    public Label accountNo;
    public Label accountName;
    public Label totalMoney;
    public TextField requestMoneyWithdraw;
    public TextField nameacct;
    public TextField numacct;
    public TextField bankacct;
    public Button withdrawBtn;

    EnrollDB enrollDB = new EnrollDB();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        route();
        setEffect();
        setWithdrawInfo();
    }
    
    private void setEffect(){
        HomeController ef = new HomeController();
        ef.hoverEffect(withdrawBtn);
        dashboardController d = new dashboardController();
        d.hoverEffect(currentAccount);
        d.hoverEffect(changeAccount);
    }

    private void route(){
        Navigator nav = new Navigator();
        back.setOnMouseClicked(nav::withdrawRoute);
        changeAccount.setOnMouseClicked(nav::settingRoute);
    }

    public void setWithdrawInfo() {
        String sessionUsername = SessionManager.getInstance().getUsername();
        UserDB userDB = new UserDB();
        int userId = userDB.getUserId(sessionUsername);
        withdraw userWithdraw = withdrawDB.getWithdrawInfo(userId);

        double totalCost = enrollDB.getTotalEnrollments(userId);

        if (userWithdraw != null) {
            accountNo.setText(userWithdraw.getAccountNumber());
            numacct.setText(userWithdraw.getAccountNumber());
            String fullname = userWithdraw.getAccountFName() + " " + userWithdraw.getAccountLName();
            accountName.setText(fullname);
            nameacct.setText(fullname);
            bankacct.setText(userWithdraw.getBankName());
            totalMoney.setText(String.format("%.2f", totalCost));
        } else {
            System.out.println("Not found data");
        }
    }

    public void withdrawBtn(ActionEvent event) {
        try {
            double requestAmount = Double.parseDouble(requestMoneyWithdraw.getText());
            double totalCost = Double.parseDouble(totalMoney.getText());

            if (requestAmount > totalCost) {
                showAlert("Fail", "The withdrawal amount exceeds the available balance.", Alert.AlertType.ERROR);
                System.out.println("Fail");
            } else {
//                double newBalance = totalCost - requestAmount;
//                enrollDB.updateUserBalance(SessionManager.getInstance().getUsername(), newBalance);
//                totalMoney.setText(String.format("%.2f", newBalance));
                showAlert("Successfully", "Withdraw successfully!",  Alert.AlertType.INFORMATION);
                System.out.println("Withdraw successfully!");
            }
        } catch (NumberFormatException e) {
            showAlert("Fail", "Sorry, something went wrong!", Alert.AlertType.ERROR);
            System.out.println("Something went wrong");
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
