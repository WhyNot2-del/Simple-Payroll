package Users;

import java.security.NoSuchAlgorithmException;


/*
 * class RegUser [Regular User] (Derived from User)
 * This class represents an employee in our Payroll Program, with properties for their wallet, and how much they are paid.
 * In theory, this could also include more information such as their role in the company, or when they were hired.
 */
public class RegUser extends User {
    private double wallet; // How much money our user has.
    private double payRate; // How much the user is paid.

    /*
     * double Method,
     * Getter for our wallet property.
     */
    public double getWallet() {
        return wallet;
    }

    /*
     * void method
     * Setter for our wallet property.
     * Arguments:
     *  newWallet (double): The new value of our wallet.
     */
    public void setWallet(double newWallet) {
        this.wallet = newWallet;
    }

    /*
     * double Method
     * Getter method for our payRate property.
     */
    public double getPayRate() {
        return payRate;
    }

    /*
     * void Method
     * Setter method for our payRate.
     * Arguments:
     *  newPayRate (double): Our new payRate.
     */
    public void setPayRate(double newPayRate) {
        this.payRate = newPayRate;
    }

    /* 
     * Constructor Method
     * This constructor mostly just calls our superclass's constructor to initialize it's values
     * But we also initilize our RegUser exclusive properties, wallet and payRate.
     * Wallet is always started with a value of $500, while payRate is a parameter.
     * Arguments:
     *  (Refer to User for unlisted Parameters)
     *  payRate (double) Our initial payRate.
     * Exceptions:
     *  BadPasswordException: Thrown when we have an InvalidKeySpec
     *  NoSuchAlgorithmException: Bubbled from hashPassword, thrown if Java cannot use/find "PBKDF2WithHmacSHA1", if throw, program should exit gracefully.
     */
    public RegUser(String username, String password, String email, String ssn, double payRate) throws BadPasswordException, NoSuchAlgorithmException {
        super(username, password, email, ssn);
        this.wallet = 500;
        this.payRate = payRate;
    }

}