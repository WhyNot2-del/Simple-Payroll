package Users;

abstract class User{
    private String username;
    private String password;
    private String email;
    private String ssn;

    private static String hashPassword(String passwd){
        //TODO: Stub of method, add logic for hashing passwords.
        return passwd;
    }

    public boolean checkPassword(String incomingPassword){
        return this.password.equals(hashPassword(incomingPassword));
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSSN(String newSSN){
        this.ssn = newSSN;
    }

    public User(String username, String password, String email, String ssn){
        this.username = username;
        this.email = email;
        this.ssn = ssn;
        this.password = hashPassword(password);
    }
}

class RegUser extends User {
    private double wallet;
    private double payRate;

    public double getWallet() {
        return wallet;
    }
    public void setWallet(double wallet) {
        this.wallet = wallet;
    }
    public double getPayRate() {
        return payRate;
    }
    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public RegUser(String username, String password, String email, String ssn, double payRate){
        super(username, password, email, ssn);
        this.wallet = 500;
        this.payRate = payRate;
    }

}
