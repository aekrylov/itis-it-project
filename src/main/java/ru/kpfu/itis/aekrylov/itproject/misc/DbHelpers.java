package ru.kpfu.itis.aekrylov.itproject.misc;

import ru.kpfu.itis.aekrylov.itproject.entities.Entity;

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

    public static <E extends Entity> String getTableName(Class<E> c) {
        //very simple
        return toDbName(c.getSimpleName()) + "s";
    }
}
