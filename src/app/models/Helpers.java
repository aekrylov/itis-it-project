package app.models;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 11:16 PM
 */
public class Helpers {

    public static String toDbName(String name) {
        return name.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
    }

    static Object parseString(Class type, String str) {
        if(type.equals(String.class))
            return str;

        Object value = null;
        //todo entities

        if (double.class.isAssignableFrom(type)) {
            value = Double.valueOf(str);
        } else if(int.class.isAssignableFrom(type)) {
            value = Integer.parseInt(str);
        } else if(char.class.isAssignableFrom(type)) {
            value = str.charAt(0);
        }

        return value;
    }
}
