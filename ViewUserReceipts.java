public class ViewUserReceipts extends Option {
    private String uname;
    public ViewUserReceipts(String username) {
        super(SecLevel.CLASSIFIED, ActionType.READ, "View User Receipts");
    }

    public void start() {
        jdbc.viewUserReceipts(uname);
    }
}
