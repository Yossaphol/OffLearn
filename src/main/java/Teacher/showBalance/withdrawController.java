package Teacher.showBalance;

import Database.EnrollDB;
import Database.UserDB;
import Database.withdraw;
import Database.withdrawDB;
import Student.HomeAndNavigation.HomeController;
import Teacher.navigator.Navigator;
import a_Session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class withdrawController implements Initializable {

    @FXML
    public HBox navbarContainer;
    public Button withdrawBtn;
    public Button edit;
    public Button withdrawHistory;
    public Label accountNo;
    public Label accountName;
    public Label bankname;
    public Label totalMoney;

    EnrollDB enrollDB = new EnrollDB();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayNavbar();

        setEffect();
        route();

        showacct();

    }

    private void showacct() {
        String sessionUsername = SessionManager.getInstance().getUsername();
        UserDB userDB = new UserDB();
        int userId = userDB.getUserId(sessionUsername);
        withdraw userWithdraw = withdrawDB.getWithdrawInfo((userId));
        String acctname = userWithdraw.getAccountFName() + " " + userWithdraw.getAccountLName();
        accountNo.setText(userWithdraw.getAccountNumber());
        accountName.setText(acctname);
        bankname.setText(userWithdraw.getBankName());

        double totalCost = enrollDB.getTotalEnrollments(userId);
        totalMoney.setText(String.format("%.2f", totalCost));
    }

    private void route(){
        Navigator nav = new Navigator();
        edit.setOnMouseClicked(nav::settingRoute);
        withdrawBtn.setOnMouseClicked(nav::withdrawDetailRoute);
        withdrawHistory.setOnMouseClicked(nav::withdrawHistoryRoute);
    }

    private void setEffect(){
        HomeController hm = new HomeController();
        hm.hoverEffect(withdrawBtn);
        hm.hoverEffect(withdrawHistory);
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
