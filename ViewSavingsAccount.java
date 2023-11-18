public class ViewSavingsAccount extends Option {
    private String uname;

    public ViewSavingsAccount(String username) {
        super(SecLevel.CLASSIFIED, ActionType.READ, "View Savings Account");
        this.uname = username;
    }
    
    public void start() {
        JDBC.getSavingsBalance(uname);
    }
}
