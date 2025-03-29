package Database;

public class withdraw {
    private String accountNumber;
    private String accountFName;
    private String accountLName;
    private String bankName;

    public withdraw() {
        this.accountNumber = accountNumber;
        this.accountFName = accountFName;
        this.accountLName = accountLName;
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountFName() {
        return accountFName;
    }

    public String getAccountLName() {
        return accountLName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountFName(String accountFName) {
        this.accountFName = accountFName;
    }

    public void setAccountLName(String accountLName) {
        this.accountLName = accountLName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
