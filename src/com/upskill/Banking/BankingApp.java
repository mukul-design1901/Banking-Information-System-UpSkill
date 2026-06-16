package com.upskill.Banking;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Simple class to hold user data in memory
class BankAccount {
    String accountNumber;
    String name;
    String address;
    String contact;
    String password;
    double balance;

    // Constructor to create a new account object
    public BankAccount(String accountNumber, String name, String address, String contact, String password, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.password = password;
        this.balance = balance;
    }
}

public class BankingApp {
    // Session database to store all registered users [cite: 17]
    static ArrayList<BankAccount> database = new ArrayList<>();
    static BankAccount loggedInUser = null;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== BANKING INFORMATION SYSTEM PROTOTYPE ===");

        while (true) {
            if (loggedInUser == null) {
                // Main Menu before logging in [cite: 14, 16]
                System.out.println("\n1. Register New Account");
                System.out.println("2. Login to Account");
                System.out.println("3. Exit Application");
                System.out.print("Choose an option (1-3): ");
                int choice = sc.nextInt();
                sc.nextLine(); // Clear scanner buffer

                if (choice == 1) {
                    register();
                } else if (choice == 2) {
                    login();
                } else if (choice == 3) {
                    System.out.println("Thank you for using our system!");
                    break;
                } else {
                    System.out.println("Invalid option! Please try again.");
                }
            } else {
                // Dashboard Menu after successful login [cite: 16]
                System.out.println("\n--- Welcome " + loggedInUser.name + " (Acc No: " + loggedInUser.accountNumber + ") ---");
                System.out.println("1. View Profile / Update Account Information");
                System.out.println("2. Deposit Money (Week 2 Feature)");
                System.out.println("3. Withdraw Money (Week 2 Feature)");
                System.out.println("4. Fund Transfer (Week 2 Feature)");
                System.out.println("5. View Account Statement (Week 2 Feature)");
                System.out.println("6. Logout");
                System.out.print("Choose an option (1-6): ");
                int choice = sc.nextInt();
                sc.nextLine(); // Clear scanner buffer

                if (choice == 1) {
                    manageAccount();
                } else if (choice == 6) {
                    System.out.println("Logged out successfully.");
                    loggedInUser = null; // Reset the active session
                } else if (choice >= 2 && choice <= 5) {
                    System.out.println("[Notice] This banking operation is scheduled for Week 2 development.");
                } else {
                    System.out.println("Invalid option!");
                }
            }
        }
    }

    // 1. User Registration Feature [cite: 9, 21]
    public static void register() {
        System.out.println("\n--- USER REGISTRATION FORM ---");
        System.out.print("Enter Your Full Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Your Address: ");
        String address = sc.nextLine();
        System.out.print("Enter Your Contact Number: ");
        String contact = sc.nextLine();
        System.out.print("Create a Login Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Initial Deposit Amount: ");
        double initialDeposit = sc.nextDouble();
        sc.nextLine(); // Clear scanner buffer

        // Generate a random 10-digit account number [cite: 10, 23]
        Random rand = new Random();
        long num = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        String generatedAccNum = String.valueOf(num);

        // Save details to memory temporary session array list [cite: 17, 23]
        BankAccount newAccount = new BankAccount(generatedAccNum, name, address, contact, password, initialDeposit);
        database.add(newAccount);

        // Confirmation output display [cite: 24]
        System.out.println("\n[SUCCESS] Registration Completed Successfully! [cite: 24]");
        System.out.println("Your Unique Generated Account Number is: " + generatedAccNum + " [cite: 23]");
        System.out.println("Please write this down to use for logging in.");
    }

    // 2. Basic Login / Password Protection Feature [cite: 14]
    public static void login() {
        System.out.println("\n--- SECURE USER LOGIN ---");
        System.out.print("Enter Your 10-Digit Account Number: ");
        String accNum = sc.nextLine();
        System.out.print("Enter Your Password: ");
        String pass = sc.nextLine();

        boolean found = false;
        // Search matching credentials inside session database [cite: 14]
        for (BankAccount acc : database) {
            if (acc.accountNumber.equals(accNum) && acc.password.equals(pass)) {
                loggedInUser = acc; // Establish active runtime session
                System.out.println("[Login Success] Welcome to your dashboard!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("[Error] Invalid Account Number or Password. Access Denied! [cite: 15]");
        }
    }

    // 3. Account Management / Profile Update Feature [cite: 10, 25]
    public static void manageAccount() {
        System.out.println("\n--- CURRENT ACCOUNT PROFILE ---");
        System.out.println("Account Number: " + loggedInUser.accountNumber);
        System.out.println("1. Name: " + loggedInUser.name);
        System.out.println("2. Address: " + loggedInUser.address);
        System.out.println("3. Contact Details: " + loggedInUser.contact);
        System.out.println("Current Balance: $" + loggedInUser.balance);
        System.out.println("---------------------------------");

        System.out.print("Do you want to update your profile details? (yes/no): ");
        String ans = sc.nextLine();

        if (ans.equalsIgnoreCase("yes")) {
            System.out.print("Enter New Name (or press Enter to keep current): ");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) {
                loggedInUser.name = newName;
            }

            System.out.print("Enter New Address (or press Enter to keep current): ");
            String newAddress = sc.nextLine();
            if (!newAddress.isEmpty()) {
                loggedInUser.address = newAddress;
            }

            System.out.print("Enter New Contact (or press Enter to keep current): ");
            String newContact = sc.nextLine();
            if (!newContact.isEmpty()) {
                loggedInUser.contact = newContact;
            }

            System.out.println("[SUCCESS] Your account information has been successfully updated! [cite: 27]");
        }
    }
}