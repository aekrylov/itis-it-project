package app.models;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:17 PM
 */
public abstract class DAO<T extends Entity> {

    protected static Connection connection = DB.getInstance().getConnection();

    // new architecture, still WIP
    private String tableName;
    private Class<T> entityClass;

    public DAO(String tableName, Class<T> entityClass) {
        this.tableName = tableName;
        this.entityClass = entityClass;
    }

    protected DAO(Class<T> entityClass) {
        this.tableName = Helpers.toDbName(getClass().getSimpleName());
        this.entityClass = entityClass;
    }

    public List<T> get() throws SQLException {
        PreparedStatement st = connection.prepareStatement(String.format("SELECT * FROM %s", tableName));
        ResultSet rs = st.executeQuery();

        return getList(rs);
    }

    protected List<T> getList(ResultSet rs) throws SQLException {
        List<T> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            list.add(fromResultSet(rs, entityClass));
        }

        return list;
    }

    public List<String> getColumnNames() throws SQLException {
        PreparedStatement st = connection.prepareStatement(String.format("SELECT * FROM %s LIMIT 1", tableName));
        ResultSetMetaData rsmd = st.getMetaData();

        int count = rsmd.getColumnCount();
        List<String> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String columnName = rsmd.getColumnName(i+1);
            list.add(columnName);
        }
        return list;

    }

    public List<Object[]> getTable() throws SQLException {
        PreparedStatement st = connection.prepareStatement(String.format("SELECT * FROM %s", tableName));

        ResultSet rs = st.executeQuery();
        int columnCount = rs.getMetaData().getColumnCount();
        List<Object[]> list = new ArrayList<>(rs.getFetchSize());


        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < row.length; i++) {
                row[i] = rs.getObject(i+1);
            }
            list.add(row);
        }
        return list;
    }

    public boolean create1(T entity) throws SQLException {
        return create(entity, entityClass);
    }

    public boolean create1(Map<String, String> map) throws SQLException {
        return create1((T) Entity.getEntity(map, entityClass));
    }

    private static <E> List<E> getList(ResultSet rs, Class<? extends Entity> c) throws SQLException {
        List<E> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            list.add(fromResultSet(rs, c));
        }

        return list;
    }

    protected static <E> E fromResultSet(ResultSet rs, Class<? extends Entity> c) throws SQLException {
        Field [] fields = Entity.getDbFields(c);

        Object instance;
        try {
            instance = c.newInstance();
            for (Field field : fields) {
                String label = Helpers.toDbName(field.getName());
                setField(field, instance, rs.getObject(label));
            }
            return (E) instance;

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean create(Object instance, Class<? extends Entity> c) throws SQLException {
        Field [] fields = Entity.getDbFields(c);

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

        //compose prepared statement
        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = Helpers.toDbName(field.getName());
            values[i] = getField(field, instance);

            int index = columns.findColumn(name);
            String columnType = metaData.getColumnTypeName(index);

            columnString = columnString + "\"" + name + "\",";
            valueString = valueString + "?::"+columnType+",";
        }

        //remove trailing commas
        columnString = columnString.substring(0, columnString.length()-1) + ")";
        valueString = valueString.substring(0, valueString.length()-1) + ")";


        //prepare statement and set fields to respective values
        PreparedStatement st = connection.prepareStatement("INSERT INTO " + tableName
        + columnString + " VALUES " + valueString + " RETURNING id");

        for (int i = 0; i < fields.length; i++) {
            if(values[i] instanceof Entity) {
                st.setInt(i+1, ((Entity)values[i]).getId());
            } else {
                st.setObject(i+1, values[i]);
            }
        }

        //execute insert query and set id attribute if it exists
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

    /**
     * Set field of instance to specified value
     */
    protected static void setField(Field field, Object instance, Object value) {
        if(value == null || Entity.class.isAssignableFrom(field.getType())) { //todo entities
            return;
        }
        //boolean accessible = field.isAccessible();
        //field.setAccessible(true);
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
        //field.setAccessible(accessible);
    }

    /**
     * Get value of instance's field
     */
    protected static Object getField(Field field, Object instance) {
        //boolean accessible = field.isAccessible();
        //field.setAccessible(true);
        Object obj = null;
        try {
            obj = field.get(instance);
        } catch (IllegalAccessException e) {
            //should never happen
            e.printStackTrace();
        }
        //field.setAccessible(accessible);

        return obj;
    }

}
