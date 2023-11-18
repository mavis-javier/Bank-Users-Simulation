public class ViewAllReceipts extends Option {
    public ViewAllReceipts() {
        super(SecLevel.TOP_SECRET, ActionType.READ, "View All Receipts");
    }

    public void start() {
        JDBC.getALLReceipt();
    }
}
