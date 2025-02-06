/*
 * Authors: Christopher Waschke, Jackson Jenks, Brody Weinkauf
 * Description: Our dataclasses containing information about users, with an Abstract class containing baseline user logic, and subclasses for actual user types.
 * Assignment: CS145-Week 4: Inheritance and Polymorphism
 * Citation: https://docs.oracle.com/javase/8/docs/api/javax/crypto/spec/PBEKeySpec.html
 * Citation: https://stackoverflow.com/a/2861125 (How can I hash a password in Java?)
 */


package Users;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/* 
 * Abstract Class User
 * The basis class of a User, including their username, password, email and SSN.
 * Most of the information for a user will be found is this class, but it doesn't contain useful information for their roles
 * A role should be defined in a subclass, with the User as it's superclass. User is an Abstract class to prevent initialization of a User without a role.
*/
public abstract class User {
    private String username;
    private String password;
    private String email;
    private String ssn; // Currently unused, but in production could be a useful piece of information.

    /* Protected Static String method (utility method)
     * This method is very similar to the citied "How can I hash a password in Java?" answer's first suggestion.
     * Essentially, we create a salt using securely generated random numbers
     * Then we create a Password spec using the desired password, and generated salt. (The spec also says it'll be hashed 255 times, and a length of 255)
     * We then create a SecretKeyFactory object to do the actual hashing, and then use it to return a byte array of the Base64 encoded key.
     * Finally, we use the Base64 Encoder to return a String value of the salt and the password, separated by a ":", for splitting later.
     * Arguments:
     *  incomingPass (String): The password that will be hashed.
     * Exceptions:
     *  InvalidKeySpecException: Thrown when the password doesn't work with the specified Spec.
     *  NoSuchAlgorithmException: Thrown if Java cannot use/find "PBKDF2WithHmacSHA1", if throw, program should exit gracefully.
     */
    protected static String hashPassword(String incomingPass) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecureRandom rand = new SecureRandom();
        byte[] salt = new byte[16]; // Our salt will be 16 bytes long.
        rand.nextBytes(salt); // Generate a random salt, filling the array.
        KeySpec spec = new PBEKeySpec(incomingPass.toCharArray(), salt, 255, 255);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        return String.format("%s:%s", enc.encodeToString(salt), enc.encodeToString(hash));
    }

    /* Overloaded Protected Static String Method (utility method) 
     * This overloaded Method functions very similarly to the other hashPassword method
     * Except this takes the salt as a parameter, and decodes the Base64 back into it's original byte array.
     * Used for comparing an incomingPassword against the existing. For further documentation, refer to the other method.
     * Arguments:
     *  incomingPass (String): The password that will be hashed
     *  saltStr (String): A String encoded Base64 salt.
    */
    protected static String hashPassword(String incomingPass, String saltStr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Base64.Decoder dec = Base64.getDecoder();
        byte[] salt = dec.decode(saltStr);
        KeySpec spec = new PBEKeySpec(incomingPass.toCharArray(), salt, 255, 255);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        return String.format("%s:%s", enc.encodeToString(salt), enc.encodeToString(hash));
    }

    /* Void Method
     * Takes a new password, and then hashes it, setting that hash to the value of the new password.
     * In theory, some form of verification should be applied before this method is called.
     * Arguments:
     *  newPasswd (String): The new desired password, unhashed.
     * Exceptions:
     *  BadPasswordException: Thrown when we have an InvalidKeySpec
     *  NoSuchAlgorithmException: Bubbled from hashPassword, thrown if Java cannot use/find "PBKDF2WithHmacSHA1", if throw, program should exit gracefully.
     */
    public void setPassword(String newPasswd) throws BadPasswordException, NoSuchAlgorithmException {
        // In theory, when this method is called, there should have prior been some form of verification that the request is from the user themselves.
        try {
            this.password = hashPassword(newPasswd);
        } catch (InvalidKeySpecException e){
            throw new BadPasswordException();
        }
    }

    /* Boolean Method
     * Checks if an incoming password is the same as the password contained in the object, by hashing using the salt found in the password.
     * Arguments:
     *  incomingPasswd (String): Password to check against, unhashed.
     * Exceptions:
     *  BadPasswordException: Thrown when we have an InvalidKeySpec
     *  NoSuchAlgorithmException: Bubbled from hashPassword, thrown if Java cannot use/find "PBKDF2WithHmacSHA1", if throw, program should exit gracefully.
     */
    public boolean checkPassword(String incomingPasswd) throws NoSuchAlgorithmException {
        String salt = this.password.split(":")[0];
        try {
            return this.password.equals(hashPassword(incomingPasswd, salt));
        } catch (InvalidKeySpecException e) {
            return false; // We don't really care if it's valid here then, it's just a bad password.
        }
    }

    /* String Method
     * A getter method for the username property.
     */
    public String getUsername() {
        return username;
    }

    /* void Method
     * Setter Method for the username property.
     * Arguments:
     *  username (String): The new username for our user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /* String Method
     * A getter method for the email property.
     */
    public String getEmail() {
        return email;
    }

    /* void method 
     * A setter method for our user's email.
     * Arguments:
     *  email (String): The new email of our user.
    */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * void method
     * Setter for our user's SSN.
     * Arguments:
     *  newSSN (String): The new SSN of our user.
     * TODO:
     *  Consider hashing the SSN for storage.
     */
    public void setSSN(String newSSN) {
        this.ssn = newSSN;
    }

    /*
     * Constructor Method
     * Initializes our user object with the properties passed as parameters.
     * Our password should be provided unhashed, and will be hashed in the initialization.
     * Arguments:
     *  username (String): Our User's Username
     *  email (String): Our user's email
     *  password (String): Our user's password, unhashed 
     *  ssn (String): Our user's SSN.
     * Exceptions:
     *  BadPasswordException: Thrown when we have an InvalidKeySpec
     *  NoSuchAlgorithmException: Bubbled from hashPassword, thrown if Java cannot use/find "PBKDF2WithHmacSHA1", if throw, program should exit gracefully.
     */
    public User(String username, String password, String email, String ssn) throws NoSuchAlgorithmException, BadPasswordException{
        this.username = username;
        this.email = email;
        this.ssn = ssn;
        try {
            this.password = hashPassword(password);
        } catch(InvalidKeySpecException e){
            throw new BadPasswordException();
        }
    }

    public String toString(){
        return String.format("Username: %s,  Email: %s", this.username, this.email);
    }
}