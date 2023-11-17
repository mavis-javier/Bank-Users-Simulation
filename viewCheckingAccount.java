public class viewCheckingAccount extends Option {
    private String uname;

    public viewCheckingAccount(String username) {
        super(SecLevel.CLASSIFIED, "View Checking Account");
        this.uname = username;
    }

    public void start() {
        jdbc.getCheckingBalance(uname);
    }
}
