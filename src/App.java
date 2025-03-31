import java.util.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws Exception {
        clearScreen();
        Scanner scanner = new Scanner(System.in);
        /* Scan csv file to create initial accounts array */
        ArrayList<Account> accounts = getAccounts();

        /* Teller interface loop */
        while (true) {
            /* Print UI */
            System.out.println("| * * Welcome Teller * * |");
            System.out.println("View accounts - 1");
            System.out.println("Create new account - 2");
            System.out.println("Close account - 3");
            System.out.println("Get balance - 4");
            System.out.println("Alter balance - 5");

            /* Temporarys */
            String name = null;
            String adress = null;
            String number = null;
            String type = null;

            switch (scanner.nextLine()) {
                case "1": // view accounts
                    clearScreen();
                    /* Print accounts array */
                    for (Account account : accounts) {
                        System.out.println(Account.getCustomerName(account) + ", " + Account.getAdress(account) + ", " +
                                Account.getAccountNumber(account) + ", " + Account.getAccountType(account) + ", " +
                                Account.getBalance(account));
                    }
                    scanner.nextLine();
                    clearScreen();
                    break;
                case "2": // create new account
                    clearScreen();
                    /* Input details */
                    System.out.println("Input name");
                    name = scanner.nextLine();
                    clearScreen();
                    System.out.println("Input adress");
                    adress = scanner.nextLine();
                    clearScreen();
                    System.out.println("Input phone number");
                    number = scanner.nextLine();
                    clearScreen();

                    /* Select valid type from Everyday, Savings, Current */
                    boolean invalidInput = true;
                    while (invalidInput) {
                        System.out.println("Input account type (Everyday/Savings/Current)");
                        switch (scanner.nextLine()) {
                            case "Everyday":
                            case "everyday":
                                type = "Everyday";
                                invalidInput = false;
                                break;
                            case "Savings":
                            case "savings":
                                type = "Savings";
                                invalidInput = false;
                                break;
                            case "Current":
                            case "current":
                                type = "Current";
                                invalidInput = false;
                                break;
                            default:
                                clearScreen();
                                System.out.println("Invalid input");
                                scanner.nextLine();
                                clearScreen();
                                break;
                        }
                    }

                    /* Create new Account and add to accounts */
                    Account account = new Account(name, adress, number, type, "0");
                    accounts.add(account);
                    clearScreen();
                    System.out.println("Account " + "'" + name + "'" + " added");
                    scanner.nextLine();
                    clearScreen();
                    break;

                case "3": // close account
                    boolean accountFound = false;
                    clearScreen();
                    System.out.println("Input name of account to be closed");
                    name = scanner.nextLine();
                    clearScreen();
                    System.out.println("Input adress of account to be closed");
                    adress = scanner.nextLine();
                    clearScreen();

                    /* Scan accounts for matches and remove if found */
                    for (Account currentAccount : accounts) {
                        if (Account.getCustomerName(currentAccount).equals(name)
                                && Account.getAdress(currentAccount).equals(adress)) {
                            /* Accounts match, close account */
                            accounts.remove(currentAccount);
                            accountFound = true;
                            break;
                        }
                    }
                    if (accountFound) {
                        System.out.println("Account " + "'" + name + "'" + " removed");
                        scanner.nextLine();
                        clearScreen();
                    } else {
                        System.out.println("Account not found");
                        scanner.nextLine();
                        clearScreen();
                    }
                    break;

                case "4": // get balance
                    accountFound = false;
                    clearScreen();
                    System.out.println("Input name of account to get balance from");
                    name = scanner.nextLine();
                    clearScreen();
                    System.out.println("Input adress of account to get balance from");
                    adress = scanner.nextLine();
                    clearScreen();

                    /* Scan accounts for matches and remove if found */
                    for (Account currentAccount : accounts) {
                        if (Account.getCustomerName(currentAccount).equals(name)
                                && Account.getAdress(currentAccount).equals(adress)) {
                            /* Accounts match, show balance */
                            clearScreen();
                            System.out.println(Account.getBalance(currentAccount));
                            scanner.nextLine();
                            clearScreen();
                            accountFound = true;
                            break;
                        }
                    }
                    if (!accountFound) {
                        System.out.println("Account not found");
                        scanner.nextLine();
                        clearScreen();
                    }
                    break;
                default: // invalid input
                    clearScreen();
                    System.out.println("Invalid input");
                    scanner.nextLine();
                    clearScreen();
            }
        }
    }

    /**
     * Creates an Arraylist of the account information in the bankData.csv file.
     * Reads each line from the csv and splits on regex "," into temporary details
     * array, then creates a new Account and adds it to accounts
     * 
     * @return ArrayList<Account> accounts
     */
    public static ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList();
        try {
            File file = new File("bankData.csv");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] accountDetails = line.split(",");
                accounts.add(0, new Account(accountDetails[0], accountDetails[1], accountDetails[2],
                        accountDetails[3], accountDetails[4]));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return accounts;
    }

    /**
     * Clears the terminal window
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
