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
    public static ArrayList<Account> getAccounts(ArrayList<AccountType> accountTypes) {
        ArrayList<Account> accounts = new ArrayList();
        try {
            File file = new File("bankData.csv");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] accountDetails = line.split(",");
                AccountType type = null;
                /*
                 * Find the transaction limits of the named accountType (accountDetails[3]) in
                 * the file and create a new AccountType object to make an Account with
                 */
                /* Find the transaction limits from accountTypes */
                for (AccountType accountType : accountTypes) {
                    /* AccountType match */
                    if (accountDetails[3].equals(AccountType.getName(accountType))) {
                        type = new AccountType(accountDetails[3], AccountType.getWithdrawLimit(accountType),
                                AccountType.getOverdraftLimit(accountType));
                    }
                }

                /* Add new account */
                accounts.add(
                        new Account(accountDetails[0], accountDetails[1], accountDetails[2], type, accountDetails[4]));
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
                        Account.getAccountNumber(account) + "," + AccountType.getName(Account.getAccountType(account))
                        + ","
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
     * Creates a new account with chosen name, adress, number, type and balance of
     * 0$
     * 
     * Check on each input for commas as these are the split regex
     * When creating phone number make sure is integer and not taken already
     * 
     * @param scanner
     * @param accounts     arraylist of accounts
     * @param accountTypes each possible account type from txt file
     */
    public static void createAccount(Scanner scanner, ArrayList<Account> accounts,
            ArrayList<AccountType> accountTypes) {
        clearScreen();
        boolean hasComma = true;

        /* Temporarys */
        String name = null;
        String adress = null;
        String number = null;
        AccountType type = null;

        /* Input name */
        /* Check for no commas and input not null */
        while (hasComma) {
            System.out.println("Input name");
            name = scanner.nextLine();
            if (!name.contains(",") && name != "") {
                hasComma = false;
                break;
            }
            clearScreen();
            System.out.println("Please do not enter , or leave field blank");
            scanner.nextLine();
            clearScreen();
        }
        hasComma = true;
        clearScreen();

        /* Input adress */
        /* Check for no commas and input not null */
        while (hasComma) {
            System.out.println("Input adress");
            adress = scanner.nextLine();
            /* Check for commas, these can break things */
            if (!adress.contains(",") && adress != "") {
                hasComma = false;
                break;
            }
            clearScreen();
            System.out.println("Please do not enter ,  or leave field blank");
            scanner.nextLine();
            clearScreen();
        }
        hasComma = true;
        clearScreen();

        /* Input phone number */
        while (hasComma) {
            boolean numberTaken = false;
            boolean validInput = false;
            while (!validInput) {
                clearScreen();
                if (!numberTaken) {
                    System.out.println("Input phone number");
                } else {
                    System.out.println("Input unused number");
                }
                /* Check phone number actually is a number */
                if (scanner.hasNextInt()) {
                    number = Integer.toString(scanner.nextInt());

                    /* Check if number is taken */
                    numberTaken = false;
                    for (Account account : accounts) {
                        if (Account.getAccountNumber(account).equals(number)) {
                            numberTaken = true;
                        }
                    }

                    /* Check for commas, these can break things */
                    if (!number.contains(",") && number != "" && !numberTaken) {
                        hasComma = false;
                        validInput = true;
                    }
                } else {
                    clearScreen();
                    System.out.println("Please input a unused number");
                    scanner.nextLine();
                    scanner.nextLine();
                    clearScreen();
                }

            }

            clearScreen();
            System.out.println("Please do not enter , or leave field blank");
            scanner.nextLine();
            clearScreen();
        }
        clearScreen();

        /*
         * Choose account type
         * First print all possible account types
         * Then pick one
         */
        String input = null;

        boolean validAccountType = false;
        while (!validAccountType) {
            /* Print all account types */
            clearScreen();
            System.out.println("Choose from possible account types:");
            for (AccountType accountType : accountTypes) {
                System.out.println(AccountType.getName(accountType));
            }

            input = scanner.nextLine();
            /* Check input is a valid account type */
            for (AccountType accountType : accountTypes) {
                if (input.equals(AccountType.getName(accountType))) {
                    type = accountType;
                    validAccountType = true;
                }
            }

            /* If is invalid */
            if (type == null) {
                clearScreen();
                System.out.println("Invalid account type");
                scanner.nextLine();
            }
        }

        /* Create new Account and add to accounts */
        Account account = new Account(name, adress, number, type, "0");
        accounts.add(account);
        clearScreen();
        System.out.println("Account " + "'" + name + "'" + " added");
        scanner.nextLine();
        clearScreen();

        /* Save the account */
        saveAccounts(accounts);
    }

    /**
     * Search for an Account in accounts by accountNumber
     * 
     * @param scanner
     * @param accounts arraylist of accounts
     * @return Account that matches inputed number / null if number is invalid
     */
    public static Account Search(Scanner scanner, ArrayList<Account> accounts) {
        String number = null;

        clearScreen();
        System.out.println("Input account number");
        number = scanner.nextLine();
        clearScreen();

        /* Scan accounts for matches and remove if found */
        for (Account currentAccount : accounts) {
            if (Account.getAccountNumber(currentAccount).equals(number)) {
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
    public static void alterBalance(Scanner scanner, Account account, ArrayList<AccountType> accountTypes) {
        /* Check for valid input */
        boolean validInput = false;
        double input = 0;
        while (!validInput) {
            clearScreen();
            System.out.println("Input deposit (+) or withdrawal (-)");

            /* Print transaction limits */
            for (AccountType accountType : accountTypes) {
                System.out.println(AccountType.getName(accountType) + " has a withdrawal limit of "
                        + AccountType.getWithdrawLimit(accountType) + " and a overdraft limit of "
                        + AccountType.getOverdraftLimit(accountType));
            }

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

        /* Alter balance by input, within bounds of withdrawal/overdraft limits */
        int withdrawLimit = Integer.parseInt(AccountType.getWithdrawLimit(Account.getAccountType(account)));
        int overdraftLimit = Integer.parseInt(AccountType.getOverdraftLimit(Account.getAccountType(account)));

        /* Transaction is within bounds, proceed */
        if ((Account.getBalance(account) + input >= overdraftLimit) && (input >= withdrawLimit)) {
            /* Alteration succesfull */
            Account.setBalance(account, input);
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
        } else {
            clearScreen();
            System.out.println("Transaction is not within bounds");
            scanner.nextLine();
            scanner.nextLine();
            clearScreen();
            return;
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
