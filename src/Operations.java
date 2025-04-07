import java.util.*;
import java.io.*;

/**
 * Contains operations such as saving, reading, creating accounts and more
 * Created to de-clutter App.java
 */
public class Operations {
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
                        accountDetails[4], accountDetails[5]));
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
                        + Account.getBalance(account) + "," + Account.getID(account));
                /* Insert return */
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("e");
        }
    }

    /**
     * Creates a new account with chosen name, adress, number, type and balance of
     * 0$ and gives a random account ID from identitys
     * If no more identitys are availible the bank has reached the maximum number of
     * accounts allowed and no more account will be created
     * 
     * Check on each input for commas as these are the split regex
     * 
     * @param scanner
     * @param identitys an arraylist with all possible account identitys, size is
     *                  customisable in admin mode
     * @param accounts  arraylist of accounts
     */
    public static void createAccount(Scanner scanner, ArrayList<Integer> identitys, ArrayList<Account> accounts) {
        clearScreen();
        boolean hasComma = true;

        /* Temporarys */
        String name = null;
        String adress = null;
        String number = null;
        String type = null;

        /* Input name */
        while (hasComma) {
            System.out.println("Input name");
            name = scanner.nextLine();
            /* Check for commas, these can break things */
            if (!name.contains(",")) {
                hasComma = false;
                break;
            }
            clearScreen();
            System.out.println("Please do not enter ,");
            scanner.nextLine();
            clearScreen();
        }
        hasComma = true;
        clearScreen();

        /* Input adress */
        while (hasComma) {
            System.out.println("Input adress");
            adress = scanner.nextLine();
            /* Check for commas, these can break things */
            if (!adress.contains(",")) {
                hasComma = false;
                break;
            }
            clearScreen();
            System.out.println("Please do not enter ,");
            scanner.nextLine();
            clearScreen();
        }
        hasComma = true;
        clearScreen();

        /* Input phone number */
        while (hasComma) {
            System.out.println("Input phone number");
            number = scanner.nextLine();
            /* Check for commas, these can break things */
            if (!number.contains(",")) {
                hasComma = false;
                break;
            }
            clearScreen();
            System.out.println("Please do not enter ,");
            scanner.nextLine();
            clearScreen();
        }
        clearScreen();

        /*
         * Select valid type from Everyday, Savings, Current
         */
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

        /* Get new unique accountID from identitys */
        Random random = new Random();
        int index = random.nextInt(identitys.size());
        String accountID = Integer.toString(identitys.get(index));
        identitys.remove(index);

        /* Create new Account and add to accounts */
        Account account = new Account(name, adress, number, type, "0",
                accountID);
        accounts.add(account);
        clearScreen();
        System.out.println("Account " + "'" + name + "'" + " added");
        scanner.nextLine();
        clearScreen();
    }

    /**
     * Search for an Account in accounts by accountID
     * 
     * @param scanner
     * @param accounts arraylist of accounts
     * @return Account that matches inputed ID / null if ID is invalid
     */
    public static Account IDSearch(Scanner scanner, ArrayList<Account> accounts) {
        String ID = null;

        clearScreen();
        System.out.println("Input account ID");
        ID = scanner.nextLine();
        clearScreen();

        /* Scan accounts for matches and remove if found */
        for (Account currentAccount : accounts) {
            if (Integer.toString(Account.getID(currentAccount)).equals(ID)) {
                return currentAccount;
            }
        }

        /* No account found */
        System.out.println("Account not found");
        scanner.nextLine();
        clearScreen();
        return null;
    }

    /**
     * Alter balance
     * Deposit - input any amount > 0
     * Withdraw - input any amount < 0 and >= -5000 that leaves balance >= 0 unless
     * type = Current then >= -1000
     * 
     * @param scanner
     * @param account the account to deposit to / withdraw from
     */
    public static void alterBalance(Scanner scanner, Account account) {
        /* Check for valid input */
        boolean validInput = false;
        double input = 0;
        while (!validInput) {
            clearScreen();
            System.out.println("Input deposit (+) or withdrawal (-)");
            System.out.println(
                    "Withdrawal limited to 5000$ and 1000$ overdraft applies only to Current accounts");
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                clearScreen();
                validInput = true;
            } else {
                clearScreen();
                System.out.println("Please input a number");
                scanner.nextLine();
                scanner.nextLine();
                clearScreen();
            }
        }

        /*
         * Alter balance for Current accounts
         * input > 0
         * input <= account balance
         */
        boolean transactionSuccesfull = false;
        if (Account.getAccountType(account).equals("Current")) {
            if (input > 0
                    || (input < 0 && (Account.getBalance(account) + 1000) + input >= 0) && input >= -5000) {
                Account.setBalance(account, input);
                transactionSuccesfull = true;
            } else { // invalid input due to input == 0 or balance - input < -1000 or input <= -5000
                if (input == 0) {
                    clearScreen();
                    System.out.println("Cannot alter balance by 0");
                    scanner.nextLine();
                    scanner.nextLine();
                    clearScreen();
                } else if (input <= -5000) { // withdrawal limit exceeded
                    System.out.println("This exceeds the withdrawal limit of $5000");
                    scanner.nextLine();
                    scanner.nextLine();
                    clearScreen();
                } else { // overdraft limit exceded
                    System.out.println("This exceeds the overdraft limit of $1000");
                    scanner.nextLine();
                    scanner.nextLine();
                    clearScreen();
                }
            }
        } else { // For non-current accounts
            if (input > 0 || (input < 0 && Account.getBalance(account) + input >= 0) && input >= -5000) {
                Account.setBalance(account, input);
                transactionSuccesfull = true;
            } else { // invalid input due to input == 0 or balance - input < -1000 or input <= -5000
                if (input == 0) {
                    System.out.println("Cannot alter balance by 0");
                    scanner.nextLine();
                    scanner.nextLine();
                    clearScreen();
                } else if (input <= -5000) { // withdrawal limit exceeded
                    System.out.println("This exceeds the withdrawal limit of $5000");
                    scanner.nextLine();
                    scanner.nextLine();
                    clearScreen();
                } else { // negative balance
                    System.out.println("Insufficent funds");
                    scanner.nextLine();
                    scanner.nextLine();
                    clearScreen();
                }
            }
        }

        /* Alteration succesfull */
        if (transactionSuccesfull) {
            if (input > 0) {
                System.out.println("Balance of account " + Account.getCustomerName(account)
                        + " increased by " + input);
            } else {
                System.out.println("Balance of account " + Account.getCustomerName(account)
                        + " decreased by " + input);
            }
            scanner.nextLine();
            scanner.nextLine();
            clearScreen();
        }
    }

    /**
     * Clears the terminal window
     * For macOS use \033[H\033[2J
     * For blueJ on windows use \u000c
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
