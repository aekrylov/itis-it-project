package ru.kpfu.itis.group501.krylov.db1_it_project.misc;

import ru.kpfu.itis.group501.krylov.db1_it_project.models.ColumnBounds;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.DAO;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Entity;
import sun.reflect.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
        //if not found, set to null
        if(Entity.class.isAssignableFrom(field.getType()) &&
                (int.class.isAssignableFrom(value.getClass()) || Integer.class.isAssignableFrom(value.getClass())) ) {
            DAO<?> dao = new DAO(field.getType());
            try {
                value = dao.get((Integer) value);
            } catch (NotFoundException e) {
                value = null;
            }
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

    /*
    * TODO merge following two methods
     */

    /**
     * Should be used when there can be several columns with the same name in the rs
     */
    public static <E extends Entity> E fromResultSet2(ResultSet rs, Class<E> entityClass, int colMin, int colMax)
            throws SQLException {
        Field [] fields = Entity.getDbFields(entityClass);

        //get column indices
        ResultSetMetaData rsmd = rs.getMetaData();
        Map<String, Integer> cols = new HashMap<>();
        for (int i = colMin; i <= colMax; i++) {
            cols.put(rsmd.getColumnName(i), i);
        }

        E instance = null;
        try {
            instance = entityClass.newInstance();
            for (Field field : fields) {
                //todo nested joins?
                String label = DbHelpers.toDbName(field.getName());
                setField(field, instance, rs.getObject(cols.get(label)));
            }

        } catch (ReflectiveOperationException ignored) { }

        return instance;

    }

    /**
     * Should be used when there can be several columns with the same name in the rs
     */
    public static <E extends Entity> E fromResultSet2(ResultSet rs, Class<E> entityClass, ColumnBounds bounds)
            throws SQLException {
        Field [] fields = Entity.getDbFields(entityClass);

        //get column indices
        ResultSetMetaData rsmd = rs.getMetaData();
        Map<String, Integer> cols = new HashMap<>();
        for (int i = bounds.min(null); i <= bounds.max(null); i++) {
            cols.put(rsmd.getColumnName(i), i);
        }

        E instance = null;
        try {
            instance = entityClass.newInstance();
            for (Field field : fields) {
                Class<?> type = field.getType();
                if(Entity.class.isAssignableFrom(type)) {
                    Class<? extends Entity> eType = (Class<? extends Entity>) type;
                    Entity entity = fromResultSet2(rs, eType, bounds.min(field), bounds.max(field));
                    setField(field, instance, entity);
                } else {
                    String label = DbHelpers.toDbName(field.getName());
                    setField(field, instance, rs.getObject(cols.get(label)));
                }
            }

        } catch (ReflectiveOperationException ignored) { }

        return instance;
    }

    /**
     * Converts str to desired type
     * @param type desired type (int, double, chat and String supported)
     * @param str string to convert
     * @return converted Object, or null if type is not supported
     */
    public static Object parseString(Class type, String str) {
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
