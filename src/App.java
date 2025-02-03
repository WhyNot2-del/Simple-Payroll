import java.util.ArrayList;
import java.util.Scanner;

public class App {

    
    final static ArrayList<User> users = new ArrayList<User>();

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

        AdminManage.addEmployee(users, userEmail, userUsername, userSSN, userPassword);

    }

    public void logInEmployee() {



    }

    public void logInAdmin() {

        
    }
}
