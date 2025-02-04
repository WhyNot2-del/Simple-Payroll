package Users;

import java.security.NoSuchAlgorithmException;

/*
 * class AdminUser (Derived from User)
 * An Adminstrative User in our Payroll Program
 * An Administrative User is the user from which the Regular Users will be paid, using their property of the payFund.
 */
public class AdminUser extends User {
    private double payFund; // The fund from which payment is given.


    /*
     * double Method
     * Getter for our payFund property.
     */
    public double getPayFund() {
        return payFund;
    }

    /*
     * void Method
     * Setter for our payFund property
     * Arguments:
     *  newPayFund (double): The value of our new payFund.
     */
    public void setPayFund(double newPayFund) {
        this.payFund = newPayFund;
    }

    /*
     * Constructor Method
     * This constructor mostly just calls our superclass's constructor to initialize it's values.
     * But we also initialize our AdminUser exclusive value, payFund.
     * Arguments:
     *  (Refer to superclass for unlisted parameters)
     * payFund (double): Our initial value for our payfund.
     */
    public AdminUser(String username, String password, String email, String ssn, double payFund) throws BadPasswordException, NoSuchAlgorithmException {
        super(username, password, email, ssn);
        this.payFund = payFund;
    }

    @Override
    public String toString(){
        return super.toString() + String.format("Type: Administrator User, Pay Fund: %.2f", this.payFund);
    }
}
