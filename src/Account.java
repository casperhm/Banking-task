public class Account {
    private String customerName;
    private String accountNumber; // phone number (String because -)
    private String adress;
    private int accountType; // 0 - Everyday, 1 - Savings, 2 - Current
    private double balance;

    /**
     * Create fresh account
     * 
     * @param customerName  customer/account name
     * @param accountNumber phone number
     * @param adress        customer adress
     * @param accountType   0 - Everyday, 1 - Savings, 2 - Current
     * @param balance       starting balance
     */
    public Account(String customerName, String adress, String accountNumber, int accountType, double balance) {
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.adress = adress;
        this.accountType = accountType;
        this.balance = balance;
    }
}
