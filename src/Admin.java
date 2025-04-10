import java.util.*;
import java.io.*;

/**
 * Contains administartor operations such as creating identitys and altering
 * account types
 */
public class Admin {
    /**
     * Reads from identitys file to create array of every unused identity. File is
     * blank by default so to create accounts you must run as admin and fill the
     * identitys file
     */
    public static ArrayList<Integer> getIdentitys() {

        ArrayList<Integer> identitys = new ArrayList<>();

        /* Fill out identitys */
        try {
            File file = new File("settings/identitys.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                identitys.add(Integer.parseInt(line));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return identitys;
    }

    /**
     * Removes and replaces identitys.txt with a blank identitys.txt, then fills it
     * with the contents of identitys
     * 
     * @param identitys the identitys to save
     */
    public static void saveIdentitys(ArrayList<Integer> identitys) {
        try {
            /* Delete file and create blank replacement */
            File file = new File("settings/identitys.txt");
            file.delete();
            file.createNewFile(); // blank new txt

            /* Save accounts to file */
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < identitys.size(); i++) {
                bw.write(Integer.toString(identitys.get(i)));
                /* Insert return */
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("e");
        }
    }

    /**
     * For each number 0-maxIdentitys,
     * 1. Check no account with this identity exists AND it is not already an
     * availible identity (to avoid duplicates)
     * 2. Either skip this number OR add it to the txt file
     * Then read from the file using getIdentitys and return the new ArrayList
     * 
     * @param maxIdentitys how many accounts the bank will support
     * @param identitys    this is for checking if an identity is already availible
     * @param accounts     this is for checking if an account has already taken this
     *                     identity
     * @return New identitys array
     */

    public static ArrayList<Integer> createIdentitys(int maxIdentitys, ArrayList<Integer> identitys,
            ArrayList<Account> accounts) {
        Operations.clearScreen();

        try {
            File file = new File("settings/identitys.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            /* Refil the file as creating a buffered writer clears it */
            for (int i = 0; i < identitys.size(); i++) {
                bw.write(identitys.get(i));
            }

            for (int i = 0; i < maxIdentitys; i++) {
                /* Check if an account exists with identity i */
                boolean identityTaken = false;
                for (Account account : accounts) {
                    if (Account.getID(account) == i) {
                        identityTaken = true;
                    }
                }

                if (!identitys.contains(i) && !identityTaken) {
                    /* Identity not taken or availible for use, add to file */
                    bw.write(Integer.toString(i));
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("e");
        }

        /* Fill arraylist from file */
        return getIdentitys();
    }
}
