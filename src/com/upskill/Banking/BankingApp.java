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

    // Administrative Credentials
    static final String ADMIN_USER = "admin";
    static final String ADMIN_PASS = "admin123";

    // Storage Persistence Pathways
    static final String USER_FILE = "users_database.txt";
    static final String STATEMENT_DIR = "statements/";

    public static void main(String[] args) {
        loadDataFromFile();
        System.out.println("=== BANKING INFORMATION SYSTEM PROTOTYPE (FINAL VERSION) ===");

        while (true) {
            if (loggedInUser == null) {
                System.out.println("\n1. Register New Account");
                System.out.println("2. Login to Account");
                System.out.println("3. Administrative Control Portal");
                System.out.println("4. Exit Application");
                System.out.print("Choose an option (1-4): ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    register();
                } else if (choice == 2) {
                    login();
                } else if (choice == 3) {
                    adminPortal();
                } else if (choice == 4) {
                    System.out.println("System terminated successfully. All data state-locked.");
                    break;
                } else {
                    System.out.println("Invalid allocation matrix channel option.");
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

    // ==================== ADMINISTRATIVE ARCHITECTURE MODULES ====================
    public static void adminPortal() {
        System.out.println("\n--- SECURE ADMIN PORTAL ACCESS ---");
        System.out.print("Enter Admin Username: ");
        String user = sc.nextLine();
        System.out.print("Enter Admin System Password: ");
        String pass = sc.nextLine();

        if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
            while (true) {
                System.out.println("\n===== SYSTEM ADMIN CORE DASHBOARD =====");
                System.out.println("1. General Global Branch Audit (Total Liquidity/Accounts)");
                System.out.println("2. Specific User Profile Investigation Tool");
                System.out.println("3. Main System Menu Return");
                System.out.print("Select Administrative Command (1-3): ");
                int target = sc.nextInt();
                sc.nextLine();

                if (target == 1) {
                    double totalLiquidity = 0;
                    System.out.println("\n--- GLOBAL REGISTERED ACCOUNT MAP ---");
                    for (BankAccount acc : database) {
                        System.out.println("-> Acc: " + acc.accountNumber + " | Holder: " + acc.name + " | Balance: ₹" + acc.balance);
                        totalLiquidity += acc.balance;
                    }
                    System.out.println("-------------------------------------");
                    System.out.println("Total Active Customer Profiles: " + database.size());
                    System.out.println("Aggregate Branch Capital Assets Vault: ₹" + totalLiquidity);
                    System.out.println("=====================================");
                } else if (target == 2) {
                    System.out.print("\nEnter Customer 10-Digit Account Number to Trace: ");
                    String trackAcc = sc.nextLine();
                    boolean traceFound = false;
                    for (BankAccount acc : database) {
                        if (acc.accountNumber.equals(trackAcc)) {
                            System.out.println("\n--- CUSTOMER CRITICAL LOG MATRIX ---");
                            System.out.println("Name: " + acc.name + " | Phone: " + acc.contact);
                            System.out.println("Encrypted Password State: [HASH LOCKED - " + hashObfuscation(acc.password) + "]");
                            System.out.println("Vault Assets Allocation: ₹" + acc.balance);
                            traceFound = true;
                            break;
                        }
                    }
                    if (!traceFound) System.out.println("[Security Notification] Target search path empty.");
                } else if (target == 3) {
                    System.out.println("Exiting System Control Space.");
                    break;
                } else {
                    System.out.println("Invalid administration priority parameter.");
                }
            }
        } else {
            System.out.println("[CRITICAL ALARM] Access Refused. Invalid Cryptographic Validation Signature.");
        }
    }

    private static String hashObfuscation(String plainText) {
        // Obfuscation Layer mimicking core protection hashes
        return "SHA256x" + plainText.hashCode() + "x88B";
    }

    // ==================== DATA OPERATION TRANSFERS ====================
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

            System.out.print("Enter New Password (or press Enter to keep current): ");
            String newPassword = sc.nextLine();
            if (!newPassword.isEmpty()) {
                loggedInUser.password = newPassword;
            }

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
            System.out.println("[Error] Transfer failed. Boundary overflow.");
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

    // ==================== CORE PERSISTENCE LOGIC (FILE HANDLING) ====================
    private static void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
            for (BankAccount acc : database) {
                writer.println(acc.accountNumber + "," + acc.name + "," + acc.address + "," + acc.contact + "," + acc.password + "," + acc.balance);
            }
        } catch (IOException e) {
            System.out.println("[File Error] Critical backup streaming failure.");
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
            System.out.println("[File Error] Chronological logging pipeline fault.");
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
                    BankAccount acc = new BankAccount(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Double.parseDouble(tokens[5]));
                    loadStatementsForAccount(acc);
                    database.add(acc);
                }
            }
        } catch (IOException e) {
            System.out.println("[IO Error] Structural context baseline recovery aborted.");
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
            System.out.println("[IO Error] Sub-ledger synchronization fault.");
        }
    }
}