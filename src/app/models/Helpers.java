package app.models;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 11:16 PM
 */
public class Helpers {

    public static String getColumnName(String name) {
        return name.replaceAll("^.+([A-Z])", "_$1").toLowerCase();
    }
}
