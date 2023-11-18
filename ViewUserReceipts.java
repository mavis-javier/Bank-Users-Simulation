public class ViewUserReceipts extends Option {
    private String uname;
    public ViewUserReceipts(String username) {
        super(SecLevel.TOP_SECRET, ActionType.READ, "View User Receipts");
    }

    public void start() {
        JDBC.viewUserReceipts(uname);
    }
}
