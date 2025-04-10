public class Account {
    private String customerName;
    private String accountNumber; // phone number (String because -)
    private String adress;
    private String accountType; // Savings, Everyday, or Current
    private double balance; // converted to double in constructor

    /**
     * Create fresh account
     * 
     * @param customerName  customer/account name
     * @param accountNumber phone number
     * @param adress        customer adress
     * @param accountType   Savings, Everyday, or Current
     * @param balance       starting balance
     */
    public Account(String customerName, String adress, String accountNumber, String accountType, String balance) {
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.adress = adress;
        this.accountType = accountType;
        this.balance = Double.parseDouble(balance);
    }

    /* Getters */
    public static String getCustomerName(Account account) {
        return account.customerName;
    }

    public static String getAccountNumber(Account account) {
        return account.accountNumber;
    }

    public static String getAdress(Account account) {
        return account.adress;
    }

    public static String getAccountType(Account account) {
        return account.accountType;
    }

    public static double getBalance(Account account) {
        return account.balance;
    }

    /* Setters */
    public static void setBalance(Account account, double amount) {
        account.balance += amount;
    }
}
