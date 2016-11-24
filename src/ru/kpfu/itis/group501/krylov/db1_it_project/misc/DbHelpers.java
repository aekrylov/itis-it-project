package ru.kpfu.itis.group501.krylov.db1_it_project.misc;

import com.sun.istack.internal.NotNull;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Entity;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/18/16 8:28 PM
 */
public abstract class DbHelpers {
    public static String toDbName(@NotNull String name) {
        return name.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
    }

    public static String toColumnDef(@NotNull String name) {
        return String.format("\"%s\"", toDbName(name));
    }

    public static <E extends Entity> String getTableName(Class<E> c) {
        //very simple
        return toDbName(c.getSimpleName()) + "s";
    }
}
