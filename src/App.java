import java.util.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws Exception {
        /* Scan csv file to create accounts array */
        ArrayList<Account> accounts = getAccounts();
    }

    public static ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList();
        try {
            File file = new File("bankData.csv");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] accountDetails = line.split(",");
                accounts.add(new Account(accountDetails[0], accountDetails[1], accountDetails[2],
                        Integer.parseInt(accountDetails[3]), Integer.parseInt(accountDetails[4])));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return accounts;
    }
}
