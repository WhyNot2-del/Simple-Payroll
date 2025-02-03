import java.lang.classfile.instruction.ThrowInstruction;
import java.util.ArrayList;
import java.util.Scanner;
import Users.User;

public class App {

    
    final static ArrayList<String> users = new ArrayList<String>();

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter the letter that corresponds to the menu option you would like to choose");

        String userAnswer = input.nextLine();

        switch(userAnswer) {

            case ""


        }


    }

    public static void createUser() {

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter your email");

        String userEmail = input.nextLine();

        System.out.print("Please enter a username");

        String userUsername = input.nextLine();

        System.out.print("Please enter your ssn");

        String userSSN = input.nextLine();

        System.out.print("Please enter a password");

        String userPassword = input.nextLine();

        //AdminManage.addEmployee(users, userEmail, userUsername, userSSN, userPassword);

    }

    public static void createAdminUser() {

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter your email");
        String userEmail = input.nextLine();

        System.out.print("Please enter a username");
        String userUsername = input.nextLine();

        System.out.print("Please enter your ssn");
        String userSSN = input.nextLine();

        System.out.print("Please enter a password");
        String userPassword = input.nextLine();

        //AdminManage.addAdmin(users, userEmail, userUsername, userSSN, userPassword);

    }

    public static void viewEmployeeList(int employeeNum) {

        Scanner input = new Scanner(System.in);
        boolean running = true;
        
        while(running) {

            for(int i = 0; i < users.size(); i++ ) {

                System.out.printf("%s%d%s%s", "(", i, ") ", users.get(i).username);
    
            }
    
            System.out.print("Do you want to see a specific employee? Type: Y/N");
    
            String userResponse = input.nextLine();

            if(userResponse.toLowerCase().equals("n")) {
                running = false;
            }

            System.out.print("What employee would you like to see?");

            String userResponse = input.nextLine();
            

        }
        
        



    }

    public static void viewEmployee(int employeeNum) {
        users.
        System.out.print();
  
        System.out.print("Please enter a username");

        System.out.print("Please enter your ssn");

        System.out.print("Please enter a password");


    }

    public void payUser() {



    }

    public void logInEmployee() {



    }

    public void logInAdmin() {

        
    }
}
