package Users;


/*
 * Class BadPasswordException (Derived from Exception)
 * An Exception class, which essentially is an alias for a InvalidKeySpecException, thrown while hashing our password.
 * I decided to create a custom Exception for this, to help make it more clear what type of error is actually happening, instead of a value exception name.
 */
public class BadPasswordException extends Exception {
    public BadPasswordException(){
        super("Invalid Password provided for hashing.");
    }
}