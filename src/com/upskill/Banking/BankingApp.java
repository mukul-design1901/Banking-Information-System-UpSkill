package com.upskill.Banking;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class BankAccount {
    String accountNumber;
    String name;
    String address;
    String contact;
    String password;
    double balance;
    ArrayList<String> statement;

    public BankAccount(String accountNumber, String name, String address, String contact, String password, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.password = password;
        this.balance = balance;
        this.statement = new ArrayList<>();
    }

    public void addTransaction(String log) {
        this.statement.add(log);
    }
}

public class BankingApp {
    static ArrayList<BankAccount> database = new ArrayList<>();
    static BankAccount loggedInUser = null;
    static Scanner sc = new Scanner(System.in);

    // Week 3 Constants for Storage Persistence
    static final String USER_FILE = "users_database.txt";
    static final String STATEMENT_DIR = "statements/";

    public static void main(String[] args) {
        // App run hote hi local files se data memory me reload hoga
        loadDataFromFile();
        System.out.println("=== BANKING INFORMATION SYSTEM PROTOTYPE ===");

        while (true) {
            if (loggedInUser == null) {
                System.out.println("\n1. Register New Account");
                System.out.println("2. Login to Account");
                System.out.println("3. Exit Application");
                System.out.print("Choose an option (1-3): ");
                int choice = sc.nextInt();
                sc.nextLine();

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
                System.out.println("\n--- Welcome " + loggedInUser.name + " (Acc No: " + loggedInUser.accountNumber + ") ---");
                System.out.println("1. View Profile / Update Account Information");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Fund Transfer");
                System.out.println("5. View Account Statement");
                System.out.println("6. Logout");
                System.out.print("Choose an option (1-6): ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    manageAccount();
                } else if (choice == 2) {
                    deposit();
                } else if (choice == 3) {
                    withdraw();
                } else if (choice == 4) {
                    fundTransfer();
                } else if (choice == 5) {
                    viewStatement();
                } else if (choice == 6) {
                    System.out.println("Logged out successfully.");
                    loggedInUser = null;
                } else {
                    System.out.println("Invalid option!");
                }
            }
        }
    }

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
        sc.nextLine();

        Random rand = new Random();
        long num = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        String generatedAccNum = String.valueOf(num);

        BankAccount newAccount = new BankAccount(generatedAccNum, name, address, contact, password, initialDeposit);
        newAccount.addTransaction("Account Opened with Initial Deposit: ₹" + initialDeposit);
        database.add(newAccount);

        // Auto-save to text files on disk
        saveDataToFile();
        saveStatementToFile(newAccount);

        System.out.println("\n[SUCCESS] Registration Completed Successfully!");
        System.out.println("Your Unique Generated Account Number is: " + generatedAccNum);
    }

    public static void login() {
        System.out.println("\n--- SECURE USER LOGIN ---");
        System.out.print("Enter Your 10-Digit Account Number: ");
        String accNum = sc.nextLine();
        System.out.print("Enter Your Password: ");
        String pass = sc.nextLine();

        boolean found = false;
        for (BankAccount acc : database) {
            if (acc.accountNumber.equals(accNum) && acc.password.equals(pass)) {
                loggedInUser = acc;
                System.out.println("[Login Success] Welcome to your dashboard!");
                found = true;
                break;
            }
        }
        if (!found) System.out.println("[Error] Invalid Account Number or Password. Access Denied!");
    }

    public static void manageAccount() {
        System.out.println("\n--- CURRENT ACCOUNT PROFILE ---");
        System.out.println("Account Number: " + loggedInUser.accountNumber);
        System.out.println("1. Name: " + loggedInUser.name);
        System.out.println("2. Address: " + loggedInUser.address);
        System.out.println("3. Contact Details: " + loggedInUser.contact);
        System.out.println("Current Balance: ₹" + loggedInUser.balance);
        System.out.println("---------------------------------");

        System.out.print("Do you want to update your profile details? (yes/no): ");
        String ans = sc.nextLine();

        if (ans.equalsIgnoreCase("yes")) {
            System.out.print("Enter New Name (or press Enter to keep current): ");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) loggedInUser.name = newName;

            System.out.print("Enter New Address (or press Enter to keep current): ");
            String newAddress = sc.nextLine();
            if (!newAddress.isEmpty()) loggedInUser.address = newAddress;

            System.out.print("Enter New Contact (or press Enter to keep current): ");
            String newContact = sc.nextLine();
            if (!newContact.isEmpty()) loggedInUser.contact = newContact;

            saveDataToFile();
            System.out.println("[SUCCESS] Your account information has been successfully updated!");
        }
    }

    public static void deposit() {
        System.out.print("\nEnter amount to deposit: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount > 0) {
            loggedInUser.balance += amount;
            loggedInUser.addTransaction("Deposited: +₹" + amount + " | Balance: ₹" + loggedInUser.balance);
            saveDataToFile();
            saveStatementToFile(loggedInUser);
            System.out.println("[SUCCESS] ₹" + amount + " deposited successfully!");
        } else {
            System.out.println("[Error] Invalid amount.");
        }
    }

    public static void withdraw() {
        System.out.print("\nEnter amount to withdraw: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount > 0 && amount <= loggedInUser.balance) {
            loggedInUser.balance -= amount;
            loggedInUser.addTransaction("Withdrawn: -₹" + amount + " | Balance: ₹" + loggedInUser.balance);
            saveDataToFile();
            saveStatementToFile(loggedInUser);
            System.out.println("[SUCCESS] ₹" + amount + " withdrawn successfully!");
        } else if (amount > loggedInUser.balance) {
            System.out.println("[Error] Insufficient balance!");
        } else {
            System.out.println("[Error] Invalid parameters.");
        }
    }

    public static void fundTransfer() {
        System.out.print("\nEnter Target Receiver's 10-Digit Account Number: ");
        String targetAcc = sc.nextLine();

        if (targetAcc.equals(loggedInUser.accountNumber)) {
            System.out.println("[Error] Self-transfer is restricted!");
            return;
        }

        BankAccount receiver = null;
        for (BankAccount acc : database) {
            if (acc.accountNumber.equals(targetAcc)) {
                receiver = acc;
                break;
            }
        }

        if (receiver == null) {
            System.out.println("[Error] Receiver account not found.");
            return;
        }

        System.out.print("Enter transaction amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount > 0 && amount <= loggedInUser.balance) {
            loggedInUser.balance -= amount;
            loggedInUser.addTransaction("Transferred: -₹" + amount + " to Acc: " + targetAcc + " | Balance: ₹" + loggedInUser.balance);

            receiver.balance += amount;
            receiver.addTransaction("Received: +₹" + amount + " from Acc: " + loggedInUser.accountNumber + " | Balance: ₹" + receiver.balance);

            saveDataToFile();
            saveStatementToFile(loggedInUser);
            saveStatementToFile(receiver);

            System.out.println("[SUCCESS] ₹" + amount + " transferred successfully to " + receiver.name);
        } else {
            System.out.println("[Error] Transfer failed. Check boundaries or balance.");
        }
    }

    public static void viewStatement() {
        System.out.println("\n--- ACCOUNT TRANSACTION STATEMENT LEDGER ---");
        System.out.println("Account Holder: " + loggedInUser.name + " | Balance: ₹" + loggedInUser.balance);
        System.out.println("--------------------------------------------------");
        for (String log : loggedInUser.statement) {
            System.out.println("-> " + log);
        }
        System.out.println("--------------------------------------------------");
    }

    // ==================== WEEK 3: FILE PERSISTENCE LOGIC (FILE HANDLING) ====================

    private static void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
            for (BankAccount acc : database) {
                writer.println(acc.accountNumber + "," + acc.name + "," + acc.address + "," + acc.contact + "," + acc.password + "," + acc.balance);
            }
        } catch (IOException e) {
            System.out.println("[File Error] Failed to backup user database states.");
        }
    }

    private static void saveStatementToFile(BankAccount acc) {
        File dir = new File(STATEMENT_DIR);
        if (!dir.exists()) dir.mkdir();

        try (PrintWriter writer = new PrintWriter(new FileWriter(STATEMENT_DIR + acc.accountNumber + "_statement.txt"))) {
            for (String log : acc.statement) {
                writer.println(log);
            }
        } catch (IOException e) {
            System.out.println("[File Error] Failed to write chronological records.");
        }
    }

    private static void loadDataFromFile() {
        File file = new File(USER_FILE);
        if (!file.exists()) return;

        database.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 6) {
                    String accNum = tokens[0];
                    String name = tokens[1];
                    String address = tokens[2];
                    String contact = tokens[3];
                    String password = tokens[4];
                    double balance = Double.parseDouble(tokens[5]);

                    BankAccount acc = new BankAccount(accNum, name, address, contact, password, balance);
                    loadStatementsForAccount(acc);
                    database.add(acc);
                }
            }
        } catch (IOException e) {
            System.out.println("[IO Error] Initialization recovery aborted.");
        }
    }

    private static void loadStatementsForAccount(BankAccount acc) {
        File file = new File(STATEMENT_DIR + acc.accountNumber + "_statement.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                acc.addTransaction(line);
            }
        } catch (IOException e) {
            System.out.println("[IO Error] Statement ledger sync fault.");
        }
    }
}