import java.util.Scanner;

public class WithdrawSavings extends Option {
    private String uname;
    private Scanner kb; //Passed in from Console.java. DO NOT close() it.

    public WithdrawSavings(String username, Scanner keyboard) {
        super(SecLevel.CLASSIFIED, ActionType.WRITE, "Withdraw Savings Account");
        this.uname = username;
        this.kb = keyboard;
    }

    public void start() {
        System.out.print("Enter your withdrawal amount :: ");
        int amount = -1 * Math.abs(kb.nextInt());

        JDBC.writeSVBalance(uname, amount);
    }
}
