//For the "frontend"

import java.util.Scanner;
public class Console {
    private static Option[] optionsMasterList;
    OptionsMasterList = DBManager.getOptions(); //Perhaps?

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter username: ");
        String username = input.nextLine();
        System.out.println("Please enter password: ");
        String password = input.nextLine();
    }

    
}