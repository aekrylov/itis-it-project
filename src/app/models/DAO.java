package app.models;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:17 PM
 */
public abstract class DAO {

    protected DAO() {}

    protected static Connection connection = DB.getInstance().getConnection();

    protected static <E> E fromResultSet(ResultSet rs, Class<? extends Entity> c) throws SQLException {
        Field [] fields = Entity.getDbFields(c);

        Object instance;
        try {
            instance = c.newInstance();
            for (Field field : fields) {
                String label = Helpers.getColumnName(field.getName());
                setField(field, instance, rs.getObject(label));
            }
            return (E) instance;

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static boolean create(Object instance, Class<? extends Entity> c) throws SQLException {
        Field [] fields = Entity.getDbFields(c);
        instance.getClass();

        if(fields[0].getName().equals("id")) {
            fields = Arrays.copyOfRange(fields, 1, fields.length);
        }

        String columnString = "(";
        String valueString = "(";
        String tableName = c.getSimpleName().toLowerCase() + "s";

        //get table schema
        ResultSet columns = connection
                .createStatement()
                .executeQuery("SELECT * FROM "+tableName+" WHERE FALSE ");
        ResultSetMetaData metaData = columns.getMetaData();

        Object[] values = new Object[fields.length];

        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = Helpers.getColumnName(field.getName());
            values[i] = getField(field, instance);

            int index = columns.findColumn(name);
            String columnType = metaData.getColumnTypeName(index);

            columnString = columnString + "\"" + name + "\",";
            valueString = valueString + "?::"+columnType+",";
        }

        columnString = columnString.substring(0, columnString.length()-1) + ")";
        valueString = valueString.substring(0, valueString.length()-1) + ")";


        PreparedStatement st = connection.prepareStatement("INSERT INTO " + tableName
        + columnString + " VALUES " + valueString + " RETURNING id");

        for (int i = 0; i < fields.length; i++) {
            if(values[i] instanceof Entity) {
                st.setInt(i+1, ((Entity)values[i]).getId());
            } else {
                st.setObject(i+1, values[i]);
            }
        }

        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            try {
                setField(c.getDeclaredField("id"), instance, rs.getInt(1));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    protected static void setField(Field field, Object instance, Object value) {
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

    protected static Object getField(Field field, Object instance) {
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

}
