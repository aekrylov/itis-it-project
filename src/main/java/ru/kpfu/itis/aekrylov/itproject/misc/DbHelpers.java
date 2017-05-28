package ru.kpfu.itis.aekrylov.itproject.misc;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/18/16 8:28 PM
 */
public abstract class DbHelpers {
    public static String toDbName(String name) {
        return name.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
    }

    public static String toColumnDef(String name) {
        return String.format("\"%s\"", toDbName(name));
    }

}
