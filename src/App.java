import java.util.*;
import java.io.*;

public class App {

    /**
     * Teller interface, loop of choosing options
     * 
     * @param scanner   scanner
     * @param accounts  bankData.csv as ArrayList
     * @param identitys all possible accountIDs, default is 100 max can be altered
     *                  in admin mode
     */
    public static void run(Scanner scanner, ArrayList<Account> accounts, ArrayList<Integer> identitys) {
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
            String ID = null;

            /*
             * Chose between view accounts, create new account, close account, get specific
             * balance, alter balance, or save data
             */
            switch (scanner.nextLine()) {
                /*
                 * View accounts, just prints each account
                 */
                case "1":
                    clearScreen();
                    /* Print accounts array */
                    for (Account account : accounts) {
                        System.out.println(Account.getCustomerName(account) + ", " + Account.getAdress(account) + ", " +
                                Account.getAccountNumber(account) + ", " + Account.getAccountType(account) + ", " +
                                Account.getBalance(account) + ", " + Account.getID(account));
                    }
                    scanner.nextLine();
                    clearScreen();
                    break;
                /*
                 * Creates a new account with chosen name, adress, number, type and balance of
                 * 0$ and gives account ID equal to the number of previous account + 1
                 * 
                 * Check on each input for commas as these are the split regex
                 */
                case "2":
                    clearScreen();
                    boolean hasComma = true;

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
                    break;
                /*
                 * Search for an account by ID and delete it
                 */
                case "3":
                    boolean accountFound = false;
                    clearScreen();
                    System.out.println("Input ID of account to be closed");
                    ID = scanner.nextLine();
                    clearScreen();

                    /* Scan accounts for matches and remove if found */
                    for (Account currentAccount : accounts) {
                        if (Integer.toString(Account.getID(currentAccount)).equals(ID)) {
                            /* Accounts match, close account */
                            name = Account.getCustomerName(currentAccount);
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
                /*
                 * Search for an account by ID and print its balance
                 */
                case "4":
                    accountFound = false;
                    clearScreen();
                    System.out.println("Input account ID");
                    ID = scanner.nextLine();
                    clearScreen();

                    /* Scan accounts for matches and remove if found */
                    for (Account currentAccount : accounts) {
                        if (Integer.toString(Account.getID(currentAccount)).equals(ID)) {
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
                /*
                 * Alter balance
                 * Deposit - can add any amount > 0 to account by accountID
                 * Withdraw - remove any amount <= 5000 that leaves balance > 0, unless type
                 * Current then >= -1000
                 */
                case "5":
                    System.out.println("guh");
                    break;
                /*
                 * Save accounts to a new bankData.csv and delete the old one
                 */
                case "6":
                    saveAccounts(accounts);
                    clearScreen();
                    System.out.println("Data saved");
                    scanner.nextLine();
                    clearScreen();
                    break;
                /*
                 * Invalid input
                 */
                default:
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

        /*
         * Create arraylist of all possible accountIDs - Default size is 100 and can be
         * changed in administrator mode
         */
        ArrayList<Integer> identitys = new ArrayList<>();
        /* Fill out identitys */
        for (int i = 0; i < 100; i++) {
            identitys.add(i);
        }

        /* Teller interface loop */
        run(scanner, accounts, identitys);
    }
}
