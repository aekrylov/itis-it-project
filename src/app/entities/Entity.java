package app.entities;

import app.misc.DbHelpers;
import app.misc.ReflectiveHelpers;
import app.models.Helpers;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 7:44 PM
 *
 * Basic entity class, used in app.models.DAO.
 * Every field of every subclass used in DAO should have a default constructor
 * and should represent a DB field with the same name
 */
public abstract class Entity {
    public abstract int getId();

    public static <E extends Entity> E getEntity(Map<String, String> map, Class<E> c) throws SQLException {
        Field[] fields = getDbFields(c);
        try {
            E instance = c.newInstance();
            for(Field field: fields) {
                String key = DbHelpers.toDbName(field.getName());
                if(map.containsKey(key) && !map.get(key).equals("")) {
                    ReflectiveHelpers.setField(field, instance, Helpers.parseString(field.getType(), map.get(key)));
                }
            }

            return instance;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return null;
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
