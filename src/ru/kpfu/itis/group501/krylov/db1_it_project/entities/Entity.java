package ru.kpfu.itis.group501.krylov.db1_it_project.entities;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.DbHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ReflectiveHelpers;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 7:44 PM
 *
 * Basic entity class, used in DAO.
 * Every field of every subclass used in DAO should have a default constructor
 * and should represent a DB field with the same name
 */
public abstract class Entity {
    public abstract int getId();

    public static <E extends Entity> E getInstance(Class<E> c) {
        E instance = null;
        try {
            instance = c.newInstance();
        } catch (ReflectiveOperationException ignored) {
            //since all sub classes have () constructor by contract, this will never happen
        }
        return instance;
    }

    public static <E extends Entity> E getEntity(Map<String, String> map, Class<E> c) throws SQLException {
        Field[] fields = getDbFields(c);
        E instance = getInstance(c);
        for(Field field: fields) {
            String key = DbHelpers.toDbName(field.getName());
            if(map.containsKey(key) && !map.get(key).equals("")) {
                ReflectiveHelpers.setField(field, instance, ReflectiveHelpers.parseString(field.getType(), map.get(key)));
            }
        }

        return instance;
    }

    public static Field[] getDbFields(Class<? extends Entity> c) {
        List<Field> list = new ArrayList<>();

        for (Field field: c.getDeclaredFields()) {
            if(!field.isAnnotationPresent(OwnField.class)) {
                list.add(field);
            }

        }
        return list.toArray(new Field[0]);
    }

    public static Field[] getDbFieldsWithoutId(Class<? extends Entity> c) {
        List<Field> list = new ArrayList<>();

        for (Field field: c.getDeclaredFields()) {
            if(!(field.isAnnotationPresent(OwnField.class) || field.getName().equals("id"))) {
                list.add(field);
            }

        }
        return list.toArray(new Field[0]);
    }

}
