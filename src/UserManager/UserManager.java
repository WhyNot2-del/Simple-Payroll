package UserManager;

import java.util.ArrayList;

class UserManager {
    
    public void spendPaycheck(ArrayList<User> users, int Employee, int spentPay) {
        users.get(Employee).setWallet(users.get(Employee).getWallet() - spentPay);
    } //  ends spendPaycheck method
} //  ends UserManager class

class AdminManager {

    public void modifyEmployeeUsername(ArrayList<User> users, String name, int employee) {
        users.get(employee).setUserName(name);
    } //  ends modifyEmployeeUsername method

    public void modifyEmployeePass(ArrayList<User> users, String pass, int employee) {
        users.get(employee).setPassword(pass);
    } //  ends modifyEmployeePass method

    public void modifyEmployeeEmail(ArrayList<User> users, String email, int employee) {
        users.get(employee).setEmail(email);
    } //  ends modifyEmployeeEmail method

    public void modifyEmployeePayRate(ArrayList<User> users, String payRate, int employee) {
        users.get(employee).setPayRate(payRate);
    } //  ends modifyEmployeePayrate method

    public void modifyEmployeeWallet(ArrayList<User> users, double wallet, int employee) {
        users.get(employee).setWallet(wallet);
        /* 
         * Possible better version of Wallet/paying employees. 
         * In the test class, prompt admin to input the # of hours employee worked which would then be passed.
         * In the manager class, multiply the employee's payrate, hours worked, and 0.88 (income tax) to generate what will be added 
         * users.get(Employee).setWallet(users.get(Employee).getWallet() + (users.get(Employee).getPayRate * hours * 0.88));
         */
    } //  ends modifyEmployeePayrate method

    // creates a new regUser object and places it into the users arraylist
    public void addEmployee(ArrayList<User> users, String email, String name, String ssn, String pass, double payRate) {
        users.add(new regUser(name, pass, email, ssn, payRate));
    } //  ends addEmployee method

    //  removes specified user object from the users arraylist
    public void removeEmployee(ArrayList<User> users, int employee) {
        users.remove(employee);
    } //  ends removeEmployee method
} //  ends AdminManager class
