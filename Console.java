//For the "frontend"

import java.util.Scanner;
public class Console {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        //java.io.Console console = System.console();
        System.out.println("Please enter username: ");
        //String username = console.readLine("Username: ");
        String username = input.nextLine();
        System.out.println("Please enter password: ");
        //String password = new String(console.readPassword("Password: "));
        String password = input.nextLine();
    }
}