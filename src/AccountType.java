import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/* Contains the name and transation limits of an account type */
public class AccountType {
    private String name;
    private String withdrawLimit;
    private String overdraftLimit;

    /**
     * Create fresh account type, used when reading types from file as this is how
     * to add new types
     * 
     * @param name
     * @param withdrawLimit  I think these are self-explanatory
     * @param overdraftLimit
     */
    public AccountType(String name, String withdrawLimit, String overdraftLimit) {
        this.name = name;
        this.withdrawLimit = withdrawLimit;
        this.overdraftLimit = overdraftLimit;
    }

    /*
     * Reads every account type from the accountTypes.txt file, returns them as an
     * ArrayList<AccountType>
     * Does this at the start of every session meaning a system restart is required
     * to load new types
     */
    public static ArrayList<AccountType> getAccountTypes() {
        ArrayList<AccountType> accountTypes = new ArrayList();
        try {
            File file = new File("accountTypes.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] accountTypeDetails = line.split(",");
                accountTypes.add(new AccountType(accountTypeDetails[0], accountTypeDetails[1], accountTypeDetails[2]));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return accountTypes;
    }

    /* Getters */
    public static String getName(AccountType accountType) {
        return accountType.name;
    }

    public static String getWithdrawLimit(AccountType accountType) {
        return accountType.withdrawLimit;
    }

    public static String getOverdraftLimit(AccountType accountType) {
        return accountType.overdraftLimit;
    }
}
