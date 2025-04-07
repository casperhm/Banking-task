import java.util.*;
import java.io.*;

public class App {

    /**
     * Teller interface, loop of choosing options for non-admin mode
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

            /*
             * Chose between view accounts, create new account, close account, get specific
             * balance, alter balance, or save data
             */
            switch (scanner.nextLine()) {
                /*
                 * View accounts, just prints each account
                 */
                case "1":
                    Operations.clearScreen();
                    /* Print accounts array */
                    for (Account account : accounts) {
                        System.out.println(Account.getCustomerName(account) + ", " + Account.getAdress(account) + ", " +
                                Account.getAccountNumber(account) + ", " + Account.getAccountType(account) + ", " +
                                Account.getBalance(account) + ", " + Account.getID(account));
                    }
                    scanner.nextLine();
                    Operations.clearScreen();
                    break;
                /*
                 * Creates a new account with chosen name, adress, number, type and balance of
                 * 0$ and gives a random account ID from identitys
                 * 
                 * Check on each input for commas as these are the split regex
                 */
                case "2":
                    Operations.createAccount(scanner, identitys, accounts);
                    break;
                /*
                 * Search for an account by ID and delete it
                 */
                case "3":
                    /* Get account by ID */
                    Account account = Operations.IDSearch(scanner, accounts);
                    if (account != null) {
                        /* Account found */
                        System.out.println("Account " + "'" + Account.getCustomerName(account) + "'" + " removed");
                        /* Remove the account */
                        accounts.remove(account);
                        scanner.nextLine();
                        Operations.clearScreen();
                    }
                    break;
                /*
                 * Search for an account by ID and print its balance
                 */
                case "4":
                    /* Get account by ID */
                    account = Operations.IDSearch(scanner, accounts);
                    if (account != null) {
                        /* Account found */
                        System.out
                                .println("Account " + "'" + Account.getCustomerName(account) + "'" + " has balance of "
                                        + Account.getBalance(account));
                        scanner.nextLine();
                        Operations.clearScreen();
                    }
                    break;
                /*
                 * Alter balance - search by accountID
                 * Deposit - input any amount > 0
                 * Withdraw - input any amount < 0 and >= -5000 that leaves balance >= 0 unless
                 * type = Current then >= -1000
                 */
                case "5":
                    Operations.clearScreen();
                    /* Get account by ID */
                    account = Operations.IDSearch(scanner, accounts);
                    if (account != null) {
                        /* Deopsit or withdraw */
                        Operations.alterBalance(scanner, account);
                    }
                    break;
                /*
                 * Save accounts to a new bankData.csv and delete the old one
                 */
                case "6":
                    Operations.saveAccounts(accounts);
                    Operations.clearScreen();
                    System.out.println("Data saved");
                    scanner.nextLine();
                    Operations.clearScreen();
                    break;
                /*
                 * Invalid input
                 */
                default:
                    Operations.clearScreen();
                    System.out.println("Invalid input");
                    scanner.nextLine();
                    Operations.clearScreen();
            }
        }
    }

    /**
     * Can choose to run in admin mode to alter things such as overdraft and
     * withdrawal limits, possible account types and more
     * 
     * @param administrator
     * @throws Exception
     */
    public static void main(Boolean administrator) throws Exception {
        Operations.clearScreen();
        Scanner scanner = new Scanner(System.in);
        /* Scan csv file to create initial accounts array */
        ArrayList<Account> accounts = Operations.getAccounts();

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
