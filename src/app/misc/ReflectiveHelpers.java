package app.misc;

import app.models.DAO;
import app.entities.Entity;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/18/16 8:20 PM
 */
public abstract class ReflectiveHelpers {


    /**
     * Set field of instance to specified value
     */
    public static void setField(Field field, Object instance, Object value) throws SQLException {
        if(value == null) {
            return;
        }

        //for entity, get it by id
        if(Entity.class.isAssignableFrom(field.getType()) &&
                (int.class.isAssignableFrom(value.getClass()) || Integer.class.isAssignableFrom(value.getClass())) ) {
            DAO<?> dao = new DAO(field.getType());
            value = dao.get((Integer) value);
        }

        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            //the ony type that causes trouble
            if(field.getType().equals(double.class) && value instanceof BigDecimal) {
                field.set(instance, ((BigDecimal) value).doubleValue());
            } else {
                field.set(instance, value);
            }
        } catch (IllegalAccessException e) {
            //should never happen
            e.printStackTrace();
        }
        field.setAccessible(accessible);
    }

    /**
     * Get value of instance's field
     */
    public static Object getField(Field field, Object instance) {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object obj = null;
        try {
            obj = field.get(instance);
        } catch (IllegalAccessException e) {
            //should never happen
            e.printStackTrace();
        }
        field.setAccessible(accessible);
        return obj;
    }

    public static <E extends Entity> E fromResultSet(ResultSet rs, Class<E> entityClass) throws SQLException {
        Field [] fields = Entity.getDbFields(entityClass);

        E instance;
        try {
            instance = entityClass.newInstance();
            for (Field field : fields) {
                String label = DbHelpers.toDbName(field.getName());
                setField(field, instance, rs.getObject(label));
            }
            return instance;

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
