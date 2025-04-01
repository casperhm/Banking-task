import java.util.*;
import java.io.*;

public class App {

    /**
     * Teller interface, loop of choosing options
     * 
     * @param scanner
     * @param accounts bankData.csv as ArrayList
     */
    public static void run(Scanner scanner, ArrayList<Account> accounts) {
        while (true) {
            /* Print UI */
            System.out.println("| * * Welcome Teller * * |");
            System.out.println("View accounts - 1");
            System.out.println("Create new account - 2");
            System.out.println("Close account - 3");
            System.out.println("Get balance - 4");
            System.out.println("Alter balance - 5");
            System.out.println("Save - 6");

            /* Temporarys */
            String name = null;
            String adress = null;
            String number = null;
            String type = null;

            /*
             * Chose between view accounts, create new account, close account, get specific
             * balance, alter balance, or save data
             */
            switch (scanner.nextLine()) {
                /* View accounts, just prints each account */
                case "1":
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
                /*
                 * Creates a new account with chosen name, adress, number, type and balance of
                 * 0$
                 */
                case "2":
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
                /* Search for an account by name and adress and delete it */
                case "3":
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
                /* Search for an account by name and adress and print its balance */
                case "4":
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
                /* guh */
                case "5":
                    System.out.println("guh");
                    break;
                /* Save accounts to a new bankData.csv and delete the old one */
                case "6":
                    saveAccounts(accounts);
                    clearScreen();
                    System.out.println("Data saved");
                    scanner.nextLine();
                    clearScreen();
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
                accounts.add(0, new Account(accountDetails[0], accountDetails[1], accountDetails[2], accountDetails[3],
                        accountDetails[4]));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return accounts;
    }

    /**
     * Removes and replaces bankData.csv with a blank bankData.csv, then fills it
     * with the contents of accounts
     * 
     * @param accounts the accounts to save
     */
    public static void saveAccounts(ArrayList<Account> accounts) {
        try {
            /* Delete file and create blank replacement */
            File file = new File("bankData.csv");
            file.delete();
            file.createNewFile(); // blank new csv

            /* Save accounts to file */
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Account account : accounts) {
                bw.write(Account.getCustomerName(account) + "," + Account.getAdress(account) + "," +
                        Account.getAccountNumber(account) + "," + Account.getAccountType(account) + ","
                        + Account.getBalance(account));
                /* Insert return */
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("e");
        }
    }

    /**
     * Clears the terminal window
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws Exception {
        clearScreen();
        Scanner scanner = new Scanner(System.in);
        /* Scan csv file to create initial accounts array */
        ArrayList<Account> accounts = getAccounts();

        /* Teller interface loop */
        run(scanner, accounts);
    }
}
