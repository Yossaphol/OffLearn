package Teacher.showBalance;

import Database.historyPaymentDB;
import Database.EnrollDB;
import Database.UserDB;
import Database.withdraw;
import Database.WithdrawDB;
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
    WithdrawDB withdrawDB = new WithdrawDB();

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
        withdraw userWithdraw = WithdrawDB.getWithdrawInfo(userId);

        double totalCost = withdrawDB.getAvailableBalance(userId);

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
        String sessionUsername = SessionManager.getInstance().getUsername();

        UserDB userDB = new UserDB();
        int userId = userDB.getUserId(sessionUsername);

        withdraw userWithdraw = WithdrawDB.getWithdrawInfo(userId);

        String acctno = userWithdraw.getAccountNumber();
        String acctname = userWithdraw.getAccountFName() + " " + userWithdraw.getAccountLName();
        String bankname = userWithdraw.getBankName();
        try {
            double requestAmount = Double.parseDouble(requestMoneyWithdraw.getText());
            double totalCost = Double.parseDouble(totalMoney.getText());

            if (requestAmount > totalCost) {
                showAlert("Fail", "The withdrawal amount exceeds the available balance.", Alert.AlertType.ERROR);
            } else {
                withdrawDB = new WithdrawDB();
                boolean success = withdrawDB.withdrawAmount(userId, acctno, acctname, bankname, requestAmount);
                if (success) {
                    showAlert("Successfully", "Withdraw successfully!", Alert.AlertType.INFORMATION);
                    requestMoneyWithdraw.setText("");
                    setWithdrawInfo();
                } else {
                    showAlert("Fail", "Could not record withdraw!", Alert.AlertType.ERROR);
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Fail", "Sorry, something went wrong!", Alert.AlertType.ERROR);
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
