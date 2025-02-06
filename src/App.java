import java.util.ArrayList;
import java.util.Scanner;
import Users.*;
import java.security.NoSuchAlgorithmException;
import UserManager.*;

public class App {

    //Creating an arraylist to use throughout the program, holding user nessesary information
    private final static ArrayList<User> employees = new ArrayList<User>();

    public static void main(String[] args) throws Exception {

        //declaring variables and creating a scanner object
        Scanner input = new Scanner(System.in);
        boolean isAdmin = false;
        User currentUser = null;

        //Must create an account every time for this program 
        System.out.println("Create an account for demo.");
        createAdminUser();

        //login statments and pulling input
        System.out.println("Hello! I hope your day is going well, it's time to log in!");
        System.out.println("Are you an admin or a regular user?(Please enter 'admin', or 'reguser')");
        String adminoruser = input.nextLine();
        
        //Switch within a trycatch. Switch checks the users input to decide if they are an admin or a user, and then logs them in accordingly.
        try {
            switch(adminoruser.toLowerCase()) {
                
                case "admin": currentUser = logInAdmin(); isAdmin = true; break;
                case "reguser": currentUser = logInRegUser(); break;
                default: System.out.println("You didn't enter 'admin' or 'regular'"); break;
            }
        } catch (NoSuchAlgorithmException e) {
        }

        //Do while with an if()else inside that contains menu options for admin users and if the user is not an admin, executes
        //the regular users options.
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

    //Method to grab a user out of the list of users
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

    //Method to spend a regular users paycheck
    public static void spendPaycheck() {
        User employee = getUser();
        if(employee.getClass() == RegUser.class){
            UserManager.spendPaycheck((RegUser)employee);
        }
        System.out.println("User must be a regular user to be paid! Admins work for free.");
    }

    //Method createUser that throws two exceptions to catch hashing and salting of passwords and if the password has incorrect characters in it
    //The main purpose of this method is to ask the user for necessary information, and then send that information to the manager class.
    public static void createUser() throws BadPasswordException, NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);

        System.out.println("Please enter your email");

        String userEmail = input.nextLine();

        System.out.println("Please enter a username");

        String userUsername = input.nextLine();

        System.out.println("Please enter your ssn");

        String userSSN = input.nextLine();

        System.out.println("Please enter a password");

        String userPassword = input.nextLine();

        System.out.print("Please enter your hourly rate: ");

        Double hourlyRate = input.nextDouble();

        AdminManager.addEmployee(employees, userEmail, userUsername, userSSN, userPassword, hourlyRate);

    }

    //Method to create a user of type admin; same as previous method but for an admin user
    public static void createAdminUser() throws BadPasswordException, NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);

        System.out.println("Please enter your email");
        String userEmail = input.nextLine();

        System.out.println("Please enter a username");
        String userUsername = input.nextLine();

        System.out.println("Please enter your ssn");
        String userSSN = input.nextLine();

        System.out.println("Please enter a password");
        String userPassword = input.nextLine();

        System.out.print("Please enter your pay fund amount: ");

        Double payfundAmount = input.nextDouble();

        AdminManager.addAdmin(employees, userEmail, userUsername, userSSN, userPassword, payfundAmount);
    }

    //Method to view the current list of employees held in the array list
    public static void viewEmployeeList() {

        for(int i = 0; i < employees.size(); i++ ) {

            System.out.printf("%s%d%s%s", "(", i, ") ", employees.get(i).getUsername());

        }  
    }

    //Method to view a specific employee within the Employees arraylist
    public static void viewEmployee() {
       
        Scanner input = new Scanner(System.in);
        boolean running = true;
        System.out.println("Do you want to see a specific employee? Type: Y/N");
        String userResponse = input.nextLine();

        if(userResponse.toLowerCase().equals("n")) {
            running = false;
        }

        System.out.println("What employee would you like to see?");
        userResponse = input.nextLine();

        for (User currentEmployee : employees) {

            if(currentEmployee.getUsername().equals(userResponse)) {
                System.out.println(currentEmployee);
                return;
            }   
        }
    }

    //Method to pay a regular user that requires the user to be an admin (because it's only an option in the admin menu) and for the user to input the hours of the employee.
    //It checks for if the person inputs a negative number of hours
    public static void payUser() {   
        
        Scanner input = new Scanner(System.in);

        User employee = getUser();
        if(employee.getClass() != RegUser.class){
            System.out.println("Can only pay a regular user.");
            return;
        }

        System.out.print("Hello, please enter the employees hours: ");
        Double hours = input.nextDouble();

        if(hours < 0) {

            System.out.println("You have inputted a negative numeric value for the amount of hours employee works");
        }
        else {
            AdminManager.modifyEmployeeWallet((RegUser)employee, hours);
        }
    }

    //This method prompts a user for their username and passwords and then checks the arraylist 'employees' against the imputed information
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
        input.close();
        return null;
    }

    //Method that is similar to the one above accept this method is to log in an admin
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
                    }
                }
            }
        } 
        System.out.println("Unable to find user!");
        input.close();
        return null;
    }
    
}
