import java.util.Scanner;

public class DepositChecking extends Option {
    private String uname;
    private Scanner kb; //Passed in from Console.java. DO NOT close() it.

    public DepositChecking(String username, Scanner keyboard) {
        super(SecLevel.CLASSIFIED, ActionType.WRITE, "Deposit Checking Account");
        this.uname = username;
        this.kb = keyboard;
    }

    public void start() {
        System.out.print("Enter your deposit amount :: ");
        int amount = Math.abs(kb.nextInt());

        jdbc.writeCKAccount(uname, amount);
    }
}