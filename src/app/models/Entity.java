package app.models;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 7:44 PM
 *
 * Basic entity class, used in app.models.DAO.
 * Every field of every subclass used in DAO should have a default constructor
 * and should represent a DB field with the same name
 */
abstract class Entity {
    public abstract int getId();

    public static Entity getEntity(Map<String, String> map, Class<? extends Entity> c) {
        Field[] fields = c.getDeclaredFields();
        try {
            Object instance = c.newInstance();
            for(Field field: fields) {
                String key = field.getName().replaceAll("(A-Z)", "_$1").toLowerCase();
                if(map.containsKey(key) && !map.get(key).equals("")) {
                    DAO.setField(field, instance, field.getType().isAssignableFrom(int.class) ?
                            Integer.parseInt(map.get(key)) : map.get(key));
                }
            }

            return (Entity) instance;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
