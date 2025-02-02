package Users;

abstract class User{
    private String username;
    private String password;
    private String email;
    private String ssn;

    boolean checkPassword(String incomingPassword){
        //TODO: Stub of method, add logic for checking password.
        return false;
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

}