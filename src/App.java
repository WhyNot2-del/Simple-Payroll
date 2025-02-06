// Authors: Christopher Waschke, Jackson Jenks, Brody Weinkauf
// Assignment: CS145-Week 4: Inheritance and Polymorphism

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Users.*;
import java.security.NoSuchAlgorithmException;
import UserManager.*;

/*
 * Enum QuitType
 * Represents the two types of feedback given when a User exits a menu.
 */
enum QuitType {
    LOGOUT,
    QUIT
}

public class App {

    //Creating an arraylist to use throughout the program, holding user necessary information
    private final static ArrayList<User> employees = new ArrayList<User>();

    /*
     * Static User Method
     * Handles the loging in a user, based upon the users found in the employees Array List
     * Returns the User Object representing the currently logged in user.
     */
    public static User handleLogin() {
        Scanner input = new Scanner(System.in);
        do {
            User checkUser = null;
            System.out.println("Hello! I hope your day is going well, it's time to log in!");
            System.out.println("Are you an admin or a regular user?(Please enter 'admin', or 'reguser')");
            String adminoruser = input.nextLine();
            try {
                switch (adminoruser.toLowerCase()) {
                    case "admin": checkUser = logInAdmin(); return checkUser;
                    case "reguser": checkUser = logInRegUser(); return checkUser;
                    default: System.out.println("You didn't enter 'admin' or 'regular'"); break;
                }
            } catch (NoSuchAlgorithmException e) {
                System.err.println(
                        "Your computer is unable to use our password hasing Algorithm. As such, we'll exit the program.");
                System.exit(1); // Exit Error Code 1, following UNIX standard of non-zero exit codes.
            }
            if (checkUser == null) {
                System.out.println("Unable to login. Please try again.");
            }
        } while(true);
    }

    /*
     * QuitType adminMenu
     * The menu and main loop of when a user logs in as an admin user.
     * Returns which option the user choose to leave the loop, allowing for the main loop to determine where it's going.
     */
    public static QuitType adminMenu() {
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("Please enter the letter that corresponds to the menu option you would like to choose");
            System.out.println("~~         Type CU to create a user        ~~");
            System.out.println("~~     Type CAU to create an admin user    ~~");
            System.out.println("~~    Type VEL to view an employee list    ~~");
            System.out.println("~~   Type VE to view a specific employee   ~~");
            System.out.println("~~       Type PU to pay an employee        ~~");
            System.out.println("~~       Type LO to logout                 ~~");
            System.out.println("~~       Type Q to exit the program        ~~");
            String answer = input.nextLine();

            switch (answer.toLowerCase()) {

                case "cu":
                    try {
                        createUser();
                    } catch (BadPasswordException | NoSuchAlgorithmException e) {
                        System.out.println("Unable to create user.");
                    }
                    break;
                case "cau":
                    try {
                        createAdminUser();
                    } catch (BadPasswordException | NoSuchAlgorithmException e){
                        System.out.println("Unable to create user.");
                    }
                    break;
                case "vel": viewEmployeeList(); break;
                case "ve": viewEmployee(); break;
                case "pu": payUser(); break;
                case "lo": return QuitType.LOGOUT;
                case "q": return QuitType.QUIT;
                default: System.out.println("You did not correctly enter one of the options. Please try again."); break;
            }
        }
    }

    // Our Main Method, Entry point.
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.println("Create an account for demo.");
        createAdminUser();

        boolean running = true;
        do {
            User currUser = handleLogin();
            if(currUser == null){
                System.out.println("Invalid User info");
                continue;
            }
            if(currUser.getClass() == AdminUser.class){
                QuitType adminQuitType = adminMenu();
                if(adminQuitType == QuitType.QUIT){
                    running = false;
                    break;
                }
            } else if(currUser.getClass() == RegUser.class){
                    System.out.println("Would you like to spend your paycheck? :-) (y/n)");
                    String answer = input.nextLine();

                    if (answer.toLowerCase().equals("y")) {
                        spendPaycheck((RegUser)currUser); //This is a safe cast, due to the checking of the class type above.
                    }
                    System.out.println("Logging out Regular User.");
            }
        } while (running);
    }

    //Method to grab a user out of the list of users
    public static User getUser() {
        Scanner input = new Scanner(System.in);

        System.out.print("What employee would you like to get?: ");
        String userResponse = input.nextLine();

        for (User currentEmployee : employees) {

            if (currentEmployee.getUsername().equals(userResponse)) {
                return currentEmployee;
            }
        }
        return null;
    }

    //Method to spend a regular users paycheck
    public static void spendPaycheck(RegUser employee) {
        if (employee == null) {
            System.out.println("Sorry, you've given an invalid username.");
            return;
        }
        if (employee.getClass() == RegUser.class) {
            UserManager.spendPaycheck((RegUser) employee);
            return;
        }
        System.out.println("User must be a regular user to be paid! Admins work for free.");
    }

    //Method createUser that throws two exceptions to catch hashing and salting of passwords and if the password has incorrect characters in it
    //The main purpose of this method is to ask the user for necessary information, and then send that information to the manager class.
    public static void createUser() throws BadPasswordException, NoSuchAlgorithmException {

        String userEmail, userUsername, userSSN, userPassword;
        Double payRate = Double.NaN;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Please enter your email");
            userEmail = input.nextLine();
        } while (userEmail.isBlank());

        do {
            System.out.println("Please enter a username");
            userUsername = input.nextLine();
        } while (userUsername.isBlank());

        do {
            System.out.println("Please enter your ssn");
            userSSN = input.nextLine();
        } while (userSSN.isBlank());

        do {
            System.out.println("Please enter a password");
            userPassword = input.nextLine();
        } while (userPassword.isBlank());

        do {
            Scanner doubleScanner = new Scanner(System.in);
            try {
                System.out.print("Please enter your pay rate: ");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a numeric value. (I.E. 2, or 2.25)");
            }
            payRate = doubleScanner.nextDouble();
        } while (payRate.isNaN());

        AdminManager.addEmployee(employees, userEmail, userUsername, userSSN, userPassword, payRate);

    }

    //Method to create a user of type admin; same as previous method but for an admin user
    public static void createAdminUser() throws BadPasswordException, NoSuchAlgorithmException {

        String userEmail, userUsername, userSSN, userPassword;
        Double payfundAmount = Double.NaN;
        Scanner input = new Scanner(System.in);
        
        do {
            System.out.println("Please enter your email");
            userEmail = input.nextLine();
        } while (userEmail.isBlank());
        
        do {
            System.out.println("Please enter a username");
            userUsername = input.nextLine();
        } while (userUsername.isBlank());
        
        do {
            System.out.println("Please enter your ssn");
            userSSN = input.nextLine();
        } while (userSSN.isBlank());
        
        do {
            System.out.println("Please enter a password");
            userPassword = input.nextLine();
        } while (userPassword.isBlank());
        
        do {
            Scanner doubleScanner = new Scanner(System.in);
            System.out.print("Please enter your pay fund amount: ");
            try {
                payfundAmount = doubleScanner.nextDouble();
            } catch (InputMismatchException e){
                System.out.println("Please enter a numeric value. (I.E. 2, or 2.25)");
            }
        } while (payfundAmount.isNaN());

        AdminManager.addAdmin(employees, userEmail, userUsername, userSSN, userPassword, payfundAmount);
    }

    //Method to view the current list of employees held in the array list
    public static void viewEmployeeList() {

        for (int i = 0; i < employees.size(); i++) {

            System.out.printf("%s%d%s%s\n", "(", i, ") ", employees.get(i).getUsername());

        }
    }

    //Method to view a specific employee within the Employees arraylist
    public static void viewEmployee() {

        Scanner input = new Scanner(System.in);

        System.out.println("What employee would you like to see?");
        String userResponse = input.nextLine();

        for (User currentEmployee : employees) {

            if (currentEmployee.getUsername().equals(userResponse)) {
                System.out.println(currentEmployee);
                return;
            }
        }
        System.out.println("Sorry, you've given an invalid username.");
    }

    //Method to pay a regular user that requires the user to be an admin (because it's only an option in the admin menu) and for the user to input the hours of the employee.
    //It checks for if the person inputs a negative number of hours
    public static void payUser() {   
        
        Scanner input = new Scanner(System.in);

        User employee = getUser();
        if (employee == null) {
            System.out.println("Unable to find User.");
            return;
        }
        if (employee.getClass() != RegUser.class) {
            System.out.println("Can only pay a regular user.");
            return;
        }

        Double hours = Double.NaN;
        do {
            Scanner doubleScanner = new Scanner(System.in);
            try {
                System.out.print("Hello, please enter the employees hours: ");
                hours = doubleScanner.nextDouble();
            } catch(InputMismatchException e){
                System.out.println("Please enter a numeric value. (I.E. 2, or 2.25)");
            }
        } while(hours.isNaN());

        if (hours < 0) {

            System.out.println("You have inputted a negative numeric value for the amount of hours employee works");
        } else {
            AdminManager.modifyEmployeeWallet((RegUser) employee, hours);
        }
    }

    //This method prompts a user for their username and passwords and then checks the arraylist 'employees' against the imputed information
    public static RegUser logInRegUser() throws NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);

        System.out.print("Hello, please enter your username!: ");
        String username = input.nextLine();

        System.out.print("Please enter your password!: ");
        String password = input.nextLine();

        for (User currentEmployee : employees) {
            if (currentEmployee.getUsername().equals(username)) {
                if (currentEmployee.checkPassword(password)) {
                    if (currentEmployee.getClass() == RegUser.class) {
                        return (RegUser) currentEmployee;
                    } else {
                        System.out.println(
                                "You've selected login as a regular user, but you've entered an admin username.");
                        return null;
                    }
                }
            }
        }
        System.out.println("Unable to find user!");
        return null;
    }

    //Method that is similar to the one above accept this method is to log in an admin
    public static AdminUser logInAdmin() throws NoSuchAlgorithmException {

        Scanner input = new Scanner(System.in);

        System.out.print("Hello, please enter your username!: ");
        String username = input.nextLine();

        System.out.print("Please enter your password!: ");
        String password = input.nextLine();

        for (User currentEmployee : employees) {
            if (currentEmployee.getUsername().equals(username)) {
                if (currentEmployee.checkPassword(password)) {
                    if (currentEmployee.getClass() == AdminUser.class) {
                        return (AdminUser) currentEmployee;
                    } else {
                        System.out.println(
                                "You've selected login as a Admin user, but you've entered an Regular User username.");
                        return null;
                    }
                }
            }
        }
        System.out.println("Unable to find user!");
        return null;

    }
    
}
