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

        System.out.println("Create an account for demo.");
        createAdminUser();

        System.out.println("Hello! I hope your day is going well, it's time to log in!");
        System.out.println("Are you an admin or a regular user?(Please enter 'admin', or 'reguser')");
        String adminoruser = input.nextLine();
        
        try {
            switch(adminoruser.toLowerCase()) {
                
                case "admin": logInAdmin(); isAdmin = true; break;
                case "reguser": logInRegUser(); break;
                default: System.out.println("You didn't enter 'admin' or 'regular'"); break;
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Yoru computer is unable to use our password hasing Algorithm. As such, we'll exit the program.");
            System.exit(1); // Exit Error Code 1, following UNIX standard of non-zero exit codes.
        }

        if(isAdmin) {
            
            boolean running = true;
            
            do {
                System.out.println("Please enter the letter that corresponds to the menu option you would like to choose");
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
                    case "pu": payUser(); break;
                    case "q": running = false;
                    default: System.out.println("You did not correctly enter one of the options. Please try again.");
                }
            } while(running);
        } 
        else{
            System.out.println("Would you like to spend your paycheck? :-) (y/n)");
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
        if(employee == null){
            System.out.println("Sorry, you've given an invalid username.");
            return;
        }
        if(employee.getClass() == RegUser.class){
            UserManager.spendPaycheck((RegUser)employee);
        }
        System.out.println("User must be a regular user to be paid! Admins work for free.");
    }

    public static void createUser() throws BadPasswordException, NoSuchAlgorithmException {

        String  userEmail, userUsername, userSSN, userPassword;
        Double walletAmount = null;
        Scanner input = new Scanner(System.in);

        do{
            System.out.println("Please enter your email");
            userEmail = input.nextLine();
        } while(userEmail.isBlank());


        do{
            System.out.println("Please enter a username");
            userUsername = input.nextLine();
        } while(userUsername.isBlank());

        do{
            System.out.println("Please enter your ssn");
            userSSN = input.nextLine();
        }while(userSSN.isBlank());

        do{
            System.out.println("Please enter a password");
            userPassword = input.nextLine();
        }while(userPassword.isBlank());

        do{
            System.out.print("Please enter your wallet amount: ");
            walletAmount = input.nextDouble();
        }while(walletAmount.isNaN());

        AdminManager.addEmployee(employees, userEmail, userUsername, userSSN, userPassword, walletAmount);

    }

    public static void createAdminUser() throws BadPasswordException, NoSuchAlgorithmException {

        String  userEmail, userUsername, userSSN, userPassword;
        Double payfundAmount = null;
        Scanner input = new Scanner(System.in);

        do{
            System.out.println("Please enter your email");
            userEmail = input.nextLine();
        } while(userEmail.isBlank());


        do{
            System.out.println("Please enter a username");
            userUsername = input.nextLine();
        } while(userUsername.isBlank());

        do{
            System.out.println("Please enter your ssn");
            userSSN = input.nextLine();
        }while(userSSN.isBlank());

        do{
            System.out.println("Please enter a password");
            userPassword = input.nextLine();
        }while(userPassword.isBlank());

        do{
            System.out.print("Please enter your wallet amount: ");
            payfundAmount = input.nextDouble();
        }while(payfundAmount.isNaN());

        AdminManager.addAdmin(employees, userEmail, userUsername, userSSN, userPassword, payfundAmount);
    }

    public static void viewEmployeeList() {

        for(int i = 0; i < employees.size(); i++ ) {

            System.out.printf("%s%d%s%s", "(", i, ") ", employees.get(i).getUsername());

        }  
    }

    public static void viewEmployee() {
       
        Scanner input = new Scanner(System.in);
        String userResponse = input.nextLine();

        System.out.println("What employee would you like to see?");
        userResponse = input.nextLine();

        for (User currentEmployee : employees) {

            if(currentEmployee.getUsername().equals(userResponse)) {
                System.out.println(currentEmployee);
                return;
            }   
        }
        System.out.println("Sorry, you've given an invalid username.");
    }

    public static void payUser() {   
        
        Scanner input = new Scanner(System.in);

        User employee = getUser();
        if(employee.getClass() != RegUser.class){
            System.out.println("Can only pay a regular user.");
            return;
        }

        System.out.print("Hello, please enter employee hours: ");
        Double hours = input.nextDouble();

        if(hours < 0) {

            System.out.println("You have inputted a negative numeric value for the amount of hours employee works");
        }
        else {
            AdminManager.modifyEmployeeWallet((RegUser)employee, hours);
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
                    } else {
                        System.out.println("You've selected login as a regular user, but you've entered an admin username.");
                        return null;
                    }
                }
            }
        }
        System.out.println("Unable to find user!");
        return null;
    }

    public static AdminUser logInAdmin() throws NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);

        System.out.print("Hello, please enter your username!: ");
        String username = input.nextLine();

        System.out.print("Please enter your password!: ");
        String password = input.nextLine();

        for(User currentEmployee : employees) {
            if(currentEmployee.getUsername().equals(username)){
                if(currentEmployee.checkPassword(password)) {
                    if(currentEmployee.getClass() == AdminUser.class) {
                        return (AdminUser)currentEmployee;
                    } else {
                        System.out.println("You've selecetd login as a Admin user, but you've entered an Regular User username.");
                        return null;
                    }
                }
            }
        } 
        System.out.println("Unable to find user!");
        return null;
        
    }
}
