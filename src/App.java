import java.util.ArrayList;
import java.util.Scanner;
import Users.*;
import java.security.NoSuchAlgorithmException;
import UserManager.*;

public class App {

    private final static ArrayList<User> employees = new ArrayList<User>();

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        boolean isAdmin = false;
        User currentUser = null;

        System.out.print("Create an account for demo.");
        createAdminUser();

        System.out.print("Hello! I hope your day is going well, it's time to log in!");
        System.out.print("Are you an admin or a regular user?(Please enter 'admin', or 'reguser')");
        String adminoruser = input.nextLine();
        
        try {
            switch(adminoruser.toLowerCase()) {
                
                case "admin": currentUser = logInAdmin(); isAdmin = true; break;
                case "reguser": currentUser = logInRegUser(); break;
                default: System.out.print("You didn't enter 'admin' or 'regular'"); break;
            }
        } catch (NoSuchAlgorithmException e) {
        }

        if(isAdmin) {
            
            boolean running = true;
            
            do {
                System.out.print("Please enter the letter that corresponds to the menu option you would like to choose");
                System.out.println("~~         Type CU to create a user        ~~");
                System.out.println("~~     Type CAU to create an admin user    ~~");
                System.out.println("~~    Type VEL to view an employee list    ~~");
                System.out.println("~~   Type VE to view a specific employee   ~~");
                System.out.println("~~       Type PU to pay an employee        ~~");
                String answer = input.nextLine();

                switch(answer.toLowerCase()){

                    case "cu": createUser(); break;
                    case "cau": createAdminUser(); break;
                    case "vel": viewEmployeeList(); break;
                    case "ve": viewEmployee(); break;
                    case "pu": running = payUser(); break;
                    case "q": running = false;
                    default: System.out.println("You did not correctly enter one of the options. Please try again.");
                }
            } while(running);
        } 
        else{
            System.out.print("Would you like to spend your paycheck? :-) (y/n)");
            String answer = input.nextLine();

            if(answer.toLowerCase().equals("y")) {
                spendPaycheck();
            }
        }
    }

    public static User getUser() {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        System.out.print("What employee would you like to get?: ");
        String userResponse = input.nextLine();

        for (User currentEmployee : employees) {

            if(currentEmployee.getUsername().equals(userResponse)) {
                return currentEmployee;
            }   
        }
        return null;
    }

    public static void spendPaycheck() {
        User employee = getUser();
        if(employee.getClass() == RegUser.class){
            UserManager.spendPaycheck((RegUser)employee);
        }
        System.out.println("User must be a regular user to be paid! Admins work for free.");
    }

    public static void createUser() throws BadPasswordException, NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter your email");

        String userEmail = input.nextLine();

        System.out.print("Please enter a username");

        String userUsername = input.nextLine();

        System.out.print("Please enter your ssn");

        String userSSN = input.nextLine();

        System.out.print("Please enter a password");

        String userPassword = input.nextLine();

        System.out.print("Please enter your wallet amount: ");

        Double walletAmount = input.nextDouble();

        AdminManager.addEmployee(employees, userEmail, userUsername, userSSN, userPassword, walletAmount);

    }

    public static void createAdminUser() throws BadPasswordException, NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter your email");
        String userEmail = input.nextLine();

        System.out.print("Please enter a username");
        String userUsername = input.nextLine();

        System.out.print("Please enter your ssn");
        String userSSN = input.nextLine();

        System.out.print("Please enter a password");
        String userPassword = input.nextLine();

        System.out.print("Please enter your pay fund amount: ");

        Double payfundAmount = input.nextDouble();

        AdminManager.addAdmin(employees, userEmail, userUsername, userSSN, userPassword, payfundAmount);
    }

    public static void viewEmployeeList() {

        for(int i = 0; i < employees.size(); i++ ) {

            System.out.printf("%s%d%s%s", "(", i, ") ", employees.get(i).getUsername());

        }  
    }

    public static void viewEmployee() {
       
        Scanner input = new Scanner(System.in);
        boolean running = true;
        System.out.print("Do you want to see a specific employee? Type: Y/N");
        String userResponse = input.nextLine();

        if(userResponse.toLowerCase().equals("n")) {
            running = false;
        }

        System.out.print("What employee would you like to see?");
        userResponse = input.nextLine();

        for (User currentEmployee : employees) {

            if(currentEmployee.getUsername().equals(userResponse)) {
                System.out.print(currentEmployee);
                return;
            }   
        }
    }

    public static void payUser() {   
        
        Scanner input = new Scanner(System.in);

        System.out.print("Hello, please enter employee hours: ");
        Double hours = input.nextDouble();

        if(hours < 0) {

            System.out.print("You have inputted a negative numeric value for the amount of hours employee works");
        }
        else {
            UserManager.modifyEmployeeWallet(employee, hours);
        }
    }

    public static RegUser logInRegUser() throws NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);

        System.out.print("Hello, please enter your username!: ");
        String username = input.nextLine();

        System.out.print("Please enter your password!: ");
        String password = input.nextLine();

        for(User currentEmployee : employees) {
            if(currentEmployee.getUsername().equals(username)){
                if(currentEmployee.checkPassword(password)) {
                    if(currentEmployee.getClass() == RegUser.class) {
                        return (RegUser)currentEmployee;
                    }
                }
            }
        }
        return null;
    }

    public static AdminUser logInAdmin() throws NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);
        boolean passwordTrue = false;

        System.out.print("Hello, please enter your username!: ");
        String username = input.nextLine();

        System.out.print("Please enter your password!: ");
        String password = input.nextLine();

        for(User currentEmployee : employees) {
            if(currentEmployee.getUsername().equals(username)){
                if(currentEmployee.checkPassword(password)) {
                    if(currentEmployee.getClass() == AdminUser.class) {
                        return (AdminUser)currentEmployee;
                    }
                }
            }
        } 
        System.out.print("Unable to find user!");
        return null;
        
    }
}
