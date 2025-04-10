import java.util.*;

public class App {

    /**
     * Interface loop for tellers
     * 
     * @param scanner      scanner
     * @param accounts     bankData.csv as ArrayList
     * @param accountTypes accountTypes.txt as ArrayList
     */
    public static void run(Scanner scanner, ArrayList<Account> accounts, ArrayList<AccountType> accountTypes) {
        Operations.clearScreen();
        while (true) {
            /* Print UI */
            System.out.println("| * * Welcome Teller * * |");
            System.out.println("View accounts - 1");
            System.out.println("Create new account - 2");
            System.out.println("Close account - 3");
            System.out.println("Get balance - 4");
            System.out.println("Alter balance - 5");

            /* Main choice loop */
            switch (scanner.nextLine()) {
                /*
                 * View accounts, just prints each account
                 */
                case "1":
                    Operations.clearScreen();
                    /* Print accounts array */
                    for (Account account : accounts) {
                        System.out.println(Account.getCustomerName(account) + ", " + Account.getAdress(account)
                                + ", " +
                                Account.getAccountNumber(account) + ", "
                                + AccountType.getName(Account.getAccountType(account)) + ", " +
                                Account.getBalance(account));
                    }
                    scanner.nextLine();
                    Operations.clearScreen();
                    break;
                /*
                 * Creates a new account with chosen name, adress, number, type and balance of
                 * 
                 * Check on each input for commas as these are the split regex
                 */
                case "2":
                    Operations.createAccount(scanner, accounts, accountTypes);
                    break;
                /*
                 * Search for an account by number and delete it
                 */
                case "3":
                    /* Get account by number */
                    Account account = Operations.Search(scanner, accounts);
                    if (account != null) {
                        /* Account found */
                        System.out.println("Account " + "'" + Account.getCustomerName(account) + "'" + " removed");
                        /* Remove the account */
                        accounts.remove(account);
                        /* Save changes */
                        Operations.saveAccounts(accounts);
                        scanner.nextLine();
                        Operations.clearScreen();
                    }
                    break;
                /*
                 * Search for an account by number and print its balance
                 */
                case "4":
                    /* Get account by number */
                    account = Operations.Search(scanner, accounts);
                    if (account != null) {
                        /* Account found */
                        System.out
                                .println("Account " + "'" + Account.getCustomerName(account) + "'"
                                        + " has balance of "
                                        + Account.getBalance(account));
                        scanner.nextLine();
                        Operations.clearScreen();
                    }
                    break;
                /*
                 * Alter balance - search by accountNumber
                 * Deposit - input any amount > 0
                 * Withdraw - input any amount < 0 and >= -5000 that leaves balance >= 0 unless
                 * type = Current then >= -1000
                 */
                case "5":
                    Operations.clearScreen();
                    /* Get account by number */
                    account = Operations.Search(scanner, accounts);
                    if (account != null) {
                        /* Deopsit or withdraw */
                        Operations.alterBalance(scanner, account, accountTypes);
                        /* Save accounts */
                        Operations.saveAccounts(accounts);
                    }
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
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        Operations.clearScreen();
        Scanner scanner = new Scanner(System.in);

        /* Scan accountTypes.txt for possible account varietys */
        ArrayList<AccountType> accountTypes = AccountType.getAccountTypes();

        /* Scan csv file to create initial accounts array */
        ArrayList<Account> accounts = Operations.getAccounts(accountTypes);

        /* Teller interface loop */
        run(scanner, accounts, accountTypes);
    }
}
