package app.models;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:17 PM
 */
public class DAO<T extends Entity> implements IDao<T> {

    protected static Connection connection = DB.getInstance().getConnection();

    // new architecture, still WIP
    private String tableName;
    private Class<T> entityClass;

    public DAO(String tableName, Class<T> entityClass) {
        this.tableName = tableName;
        this.entityClass = entityClass;
    }

    protected DAO(Class<T> entityClass) {
        this.tableName = Helpers.toDbName(entityClass.getSimpleName()) + "s";
        this.entityClass = entityClass;
    }

    @Override
    public List<T> get() throws SQLException {
        PreparedStatement st = connection.prepareStatement(String.format("SELECT * FROM %s", tableName));
        ResultSet rs = st.executeQuery();

        return getList(rs);
    }

    @Override
    public T get(int id ) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return fromResultSet(rs, entityClass);
        }
        throw new NoSuchElementException(entityClass.getSimpleName() + " not found");
    }

    @Override
    public boolean update(T instance) throws SQLException {
        Field [] fields = Entity.getDbFieldsWithoutId(instance.getClass());
        Object [] values = new Object[fields.length];
        String set = "";

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            set += String.format(" \"%s\" = ?,", Helpers.toDbName(field.getName()));
            values[i] = getField(field, instance);
        }

        PreparedStatement st = connection.prepareStatement("UPDATE "+tableName+" SET "+set.substring(0, set.length()-1)
            +" WHERE id = ?");

        int i;
        for (i = 0; i < fields.length; i++) {
            st.setObject(i+1, values[i]);
        }
        st.setInt(i+1, instance.getId());

        return st.executeUpdate() > 0;
    }

    public boolean update(Map<String, String> map) throws SQLException {
        return update((T) Entity.getEntity(map, entityClass));
    }

    private List<T> getList(ResultSet rs) throws SQLException {
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

    public boolean create(Map<String, String> map) throws SQLException {
        return create((T) Entity.getEntity(map, entityClass));
    }

    @Override
    public boolean create(T instance) throws SQLException {
        Field [] fields = Entity.getDbFieldsWithoutId(entityClass);

        String columnString = "(";
        String valueString = "(";

        //get table schema
        ResultSet columns = connection
                .createStatement()
                .executeQuery("SELECT * FROM "+tableName+" WHERE FALSE ");
        ResultSetMetaData metaData = columns.getMetaData();

        Object[] values = new Object[fields.length];

        //compose prepared statement
        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String columnName = Helpers.toDbName(field.getName());
            values[i] = getField(field, instance);

            int index = columns.findColumn(columnName);
            String columnType = metaData.getColumnTypeName(index);

            columnString = columnString + "\"" + columnName + "\",";
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
                setField(entityClass.getDeclaredField("id"), instance, rs.getInt(1));
            } catch (NoSuchFieldException ignored) { }
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(T object) throws SQLException {
        return delete(object.getId());
    }

    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM "+tableName+" WHERE id = ?");
        st.setInt(1, id);

        return st.executeUpdate() > 0;
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

    /**
     * Set field of instance to specified value
     */
    protected static void setField(Field field, Object instance, Object value) throws SQLException {
        if(value == null) {
            return;
        }

        //for entity, get it by id
        if(Entity.class.isAssignableFrom(field.getType()) &&
                (int.class.isAssignableFrom(value.getClass()) || Integer.class.isAssignableFrom(value.getClass())) ) {
            DAO dao = new DAO(field.getType());
            value = dao.get((Integer) value);
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
