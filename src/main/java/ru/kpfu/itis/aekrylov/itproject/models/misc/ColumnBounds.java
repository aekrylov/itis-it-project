package ru.kpfu.itis.aekrylov.itproject.models.misc;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/23/16 6:00 PM
 */
public class ColumnBounds extends HashMap<Field, int[]> {

    public ColumnBounds() {
        super();
    }

    public int min(Field field) {
        return get(field)[0];
    }

    public int max(Field field) {
        return get(field)[1];
    }

    public void put(Field field, int min, int max) {
        put(field, new int[]{min, max});
    }
}
