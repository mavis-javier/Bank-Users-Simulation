public class ViewCheckingAccount extends Option {
    private String uname;

    public ViewCheckingAccount(String username) {
        super(SecLevel.CLASSIFIED, ActionType.READ, "View Checking Account");
        this.uname = username;
    }

    public void start() {
        JDBC.getCheckingBalance(uname);
    }
}
