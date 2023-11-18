public class viewSavingsAccount extends Option {
    private String uname;

    public viewSavingsAccount(String username) {
        super(SecLevel.CLASSIFIED, ActionType.READ, "View Savings Account");
        this.uname = username;
    }
    
    public void start() {
        jdbc.getSavingsBalance(uname);
    }
}
