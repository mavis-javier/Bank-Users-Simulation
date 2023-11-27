import java.util.Scanner;
public class Console { 
    private static final String[] OPTIONS = {
        "Enter my savings account",
        "Enter my checking account",
        "View my receipts",
        "View all receipts"
    };
    private static void printOptions() {
        System.out.println("Please select an option below");
        for(int i = 0; i < OPTIONS.length; i++) {
            System.out.printf("%d - %s\n", i+1, OPTIONS[i]);
        }
        System.out.printf("%d - %s\n", 0, "Logout");
    }

    public static void main(String[] args) throws Exception {
        JDBC.connect();

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter username: ");
        String username = input.nextLine();
        System.out.println("Please enter password: ");
        String password = input.nextLine();

        Subject currentUser = JDBC.login(username, password);
        if(currentUser == null) {
            System.out.println("Incorrect login credentials! Please try again.");
            System.exit(1);
        }
        System.out.println("Welcome, " + currentUser.getUsername());

        while(currentUser != null) {
            printOptions();

            int selection = input.nextInt();
            Option op = null;
            switch(selection) {
                case 1  :   System.out.flush();
                            System.out.println("Welcome to your Savings Account");
                            System.out.println("Please select an option below");
                            System.out.println("1 - View my balance");
                            System.out.println("2 - Make a deposit");
                            System.out.println("3 - Make a withdrawal");
                            System.out.println("0 - Go back");
                            selection = input.nextInt();
                            switch (selection) {
                                case 1  :   op = new ViewSavingsAccount(currentUser.getUsername()); break;
                                case 2  :   op = new DepositSavings(currentUser.getUsername(), input); break;
                                case 3  :   op = new WithdrawSavings(currentUser.getUsername(), input); break;
                                default :   System.out.println("Going back...");
                            }
                            break;
                case 2  :   System.out.flush();
                            System.out.println("Welcome to your Checking Account");
                            System.out.println("Please select an option below");
                            System.out.println("1 - View my balance");
                            System.out.println("2 - Make a deposit");
                            System.out.println("3 - Make a withdrawal");
                            System.out.println("0 - Go back");
                            selection = input.nextInt();
                            switch (selection) {
                                case 1  :   op = new ViewCheckingAccount(currentUser.getUsername()); break;
                                case 2  :   op = new DepositChecking(currentUser.getUsername(), input); break;
                                case 3  :   op = new WithdrawChecking(currentUser.getUsername(), input); break;
                                default :   System.out.println("Going back...");
                            }
                            break;
                case 3  :   op = new ViewUserReceipts(currentUser.getUsername()); break;
                case 4  :   op = new ViewAllReceipts(); break;

                case 0  :   currentUser = null; break;
                default :   System.out.println("Please enter a valid selection"); continue;
            }
            if(op != null && hasPrivilege(currentUser, op))
                op.start();
        }

        input.close();
    }

    private static boolean hasPrivilege(Subject user, Option option) {
        boolean out = true;
        String message = null;

        SecLevel[] temp = SecLevel.values();
        int userClearanceIndex = 0;
        while(temp[userClearanceIndex] != user.getClearance())  //indexOf(user.getClearance())
            userClearanceIndex++;
        
        int optionClassificationIndex = 0;
        while(temp[optionClassificationIndex] != option.getClassification()) //indexOf(option.getClearance()))
            optionClassificationIndex++;

        temp = null;    //Cleanup with garbage collector

        //Bell-Lapadula, No Read Up, No Write Down
        if(userClearanceIndex < optionClassificationIndex && option.getActionType() == ActionType.READ) {
            message = "Access denied - No read up.";
            out = false;
        }
        else if(userClearanceIndex > optionClassificationIndex && option.getActionType() == ActionType.WRITE) {
            message = "Access denied - No write down.";
            out = false;
        }

        if(message != null)
            System.out.println(message);
        return out;
    }
    
}
