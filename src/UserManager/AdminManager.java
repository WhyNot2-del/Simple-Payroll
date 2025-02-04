package UserManager;

import Users.*;
import java.util.ArrayList;

public class AdminManager {

    public void modifyEmployeeUsername(User user, String name) {
        user.setUsername(name);
    } //  ends modifyEmployeeUsername method

    public void modifyEmployeePass(User user, String pass) throws Exception {
        user.setPassword(pass);
    } //  ends modifyEmployeePass method

    public void modifyEmployeeEmail(User user, String email) {
        user.setEmail(email);
    } //  ends modifyEmployeeEmail method

    public void modifyEmployeePayRate(RegUser user, double payRate) {
        user.setPayRate(payRate);
    } //  ends modifyEmployeePayrate method

    public void modifyEmployeeWallet(RegUser user, double hours) {
        user.setWallet(user.getWallet() + (user.getPayRate() * hours * 0.88));
    } //  ends modifyEmployeePayrate method

    // creates a new regUser object and places it into the users arraylist
    public void addEmployee(ArrayList<User> users, String email, String name, String ssn, String pass, double payRate) throws Exception {
        users.add(new RegUser(name, pass, email, ssn, payRate));
    } //  ends addEmployee method

    //  removes specified user object from the users arraylist
    public void removeEmployee(ArrayList<User> users, RegUser user) {
        users.remove(user);
    } //  ends removeEmployee method
} //  ends AdminManager class
