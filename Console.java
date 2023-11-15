//For the "frontend"

import java.util.Scanner;
public class Console {
    private static DBManager myDBM;
    private static Option[] optionsMasterList;
    

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter username: ");
        String username = input.nextLine();
        System.out.println("Please enter password: ");
        String password = input.nextLine();
        
        Subject currentUser = myDBM.login(username, password);
        if(currentUser == null) {
            System.out.println("Incorrect login credentials! Please try again.");
            System.exit(1);
        }
        System.out.println("Welcome, " + username);

        System.out.println("Select an option below");
        int i = 0;
        for(Option o : optionsMasterList)
            System.out.println(++i + " - " + o);
        System.out.println("0 - Logout");

        int selection = input.nextInt();
        switch(selection) {

        }

        input.close();
    }

    
}