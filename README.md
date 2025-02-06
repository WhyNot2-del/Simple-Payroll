# Simple Payroll Program
Authors: Christopher Waschke, Jackson Jenks, Brody Weinkauf

## Description
Our program manages Regular Users and Administrator Users, allowing the Admin users to pay the regular users.

## For Extra Credit
* We used an Abstract class to Represent Users (See src/Users/User.java)
* We used libraries to hash the passwords. (See src/Users/User.java lines 44 - 53)
* We used method over*loading* (not overriding) to be able to check passwords (See src/Users/User.java lines 63-71)
    (Overriding is also used in AdminUser & RegUser, overriding ToString())
* We used a custom exception, and handled it (See src/Users/BadPasswordException.java & src/App.java lines 42-46)
* We used multiple packages in our project, Users and UserManager.
* We used an ArrayList as a simple database of users. (See src/App.java line 22)
* We used an enum to signify information about leaving a loop. (See src/App.java line 14-17, 90-91) 