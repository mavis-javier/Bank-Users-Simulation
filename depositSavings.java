import java.util.Scanner;

public class depositSavings extends Option {
    private String uname;
    private Scanner kb; //Passed in from Console.java. DO NOT close() it.

    public depositSavings(String username, Scanner keyboard) {
        super(SecLevel.CLASSIFIED, "Deposit Savings Account");
        this.uname = username;
        this.kb = keyboard;
    }

    public void start() {
        System.out.print("Enter your deposit amount :: ");
        int amount = Math.abs(kb.nextInt());

        jdbc.writeSVAccount(uname, amount);
    }
}