package app.models;

import app.entities.Entity;
import app.misc.DbHelpers;
import app.misc.NotFoundException;
import app.misc.ReflectiveHelpers;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:17 PM
 */
public class DAO<T extends Entity> implements IDao<T> {

    protected static Connection connection = DB.getInstance().getConnection();

    private ResultSetMetaData rsmd;

    private String tableName;
    private Class<T> entityClass;

    public DAO(String tableName, Class<T> entityClass) {
        this.tableName = tableName;
        this.entityClass = entityClass;
    }

    public DAO(Class<T> entityClass) {
        this.tableName = DbHelpers.toDbName(entityClass.getSimpleName()) + "s";
        this.entityClass = entityClass;
    }

    protected DAO(String tableName) {
        this.tableName = tableName;
        this.entityClass = getTypeParameter(this);
    }

    protected DAO() {
        this.entityClass = getTypeParameter(this);
        this.tableName = DbHelpers.toDbName(entityClass.getSimpleName()) + "s";;
    }

    // This magic works for subclasses only
    private static Class getTypeParameter(Object instance) {
        Type superclass =  instance.getClass().getGenericSuperclass();
        return (Class) ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @Override
    public List<T> get() throws SQLException {
        PreparedStatement st = connection.prepareStatement(String.format("SELECT * FROM %s", tableName));
        ResultSet rs = st.executeQuery();

        return getList(rs);
    }

    @Override
    public T get(int id ) throws SQLException, NotFoundException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return ReflectiveHelpers.fromResultSet(rs, entityClass);
        }
        throw new NotFoundException(entityClass.getSimpleName() + " not found");
    }

    public List<T> get(SimpleFilter filter) throws SQLException {
        String sql = "SELECT * FROM "+tableName+" ";

        sql += filter.getWhere() + filter.getOrderBy();
        PreparedStatement st = connection.prepareStatement(sql);

        List<Object> params = filter.getParams();
        for (int i = 0; i < params.size(); i++) {
            st.setObject(i+1, params.get(i));
        }

        ResultSet rs = st.executeQuery();
        return getList(rs);

    }

    public Map<String, String> getMap(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return toStringMap(rs);
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
            set += String.format(" \"%s\" = ?,", DbHelpers.toDbName(field.getName()));
            values[i] = ReflectiveHelpers.getField(field, instance);
            if(values[i] instanceof Entity) {
                values[i] = ((Entity)values[i]).getId();
            }
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
        //return update((T) Entity.getEntity(map, entityClass));
        int id = Integer.parseInt(map.get("id"));

        ResultSetMetaData rsmd = getTableMetaData();
        int [] sqlTypes = new int[rsmd.getColumnCount()];
        Object [] values = new Object[rsmd.getColumnCount()];

        String set = "";
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            String columnName = rsmd.getColumnName(i+1);
            sqlTypes[i] = rsmd.getColumnType(i+1);
            values[i] = app.Helpers.getString(map, columnName);

            set += String.format(" \"%s\" = ?,", columnName);
        }

        PreparedStatement st = connection.prepareStatement(
                "UPDATE "+tableName+" SET "+set.substring(0, set.length()-1)+" WHERE id = ?");
        for (int i = 0; i < sqlTypes.length; i++) {
            st.setObject(i+1, values[i], sqlTypes[i]);
        }
        st.setInt(values.length+1, id);

        return st.executeUpdate() > 0;
    }

    protected List<T> getList(ResultSet rs) throws SQLException {
        List<T> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            list.add(ReflectiveHelpers.fromResultSet(rs, entityClass));
        }

        return list;
    }

    public List<String> getColumnNames() throws SQLException {
        ResultSetMetaData rsmd = getTableMetaData();

        int count = rsmd.getColumnCount();
        List<String> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String columnName = rsmd.getColumnName(i+1);
            list.add(columnName);
        }
        return list;
    }

    public List<Object[]> getTable(String str) throws SQLException {
        if(str == null || str.equals(""))
            return getTable();

        PreparedStatement st = connection.prepareStatement(
                "SELECT * FROM "+tableName+" WHERE lower(concat_ws(', ', "+tableName+".*)) LIKE ?");
        st.setString(1, "%"+str.toLowerCase()+"%");

        return getTable(st.executeQuery());
    }

    public List<Object[]> getTable() throws SQLException {
        return getTableStatic(tableName);
    }

    public static List<Object[]> getTableStatic(String tableName) throws SQLException {
        PreparedStatement st = connection.prepareStatement(String.format("SELECT * FROM %s", tableName));

        ResultSet rs = st.executeQuery();
        return getTableStatic(tableName, rs);
    }

    private static List<Object[]> getTableStatic(String tableName, ResultSet rs) throws SQLException {
        int columnCount = getTableMetaData(tableName).getColumnCount();
        List<Object[]> list = new ArrayList<>(rs.getFetchSize());

        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < row.length; i++) {
                row[i] = rs.getString(i+1);
            }
            list.add(row);
        }
        return list;
    }

    private List<Object[]> getTable(ResultSet rs) throws SQLException {
        return getTableStatic(tableName, rs);
    }

    public boolean create(Map<String, String> map) throws SQLException {
        return create(Entity.getEntity(map, entityClass));
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
        ResultSetMetaData metaData = getTableMetaData();

        Object[] values = new Object[fields.length];

        //compose prepared statement
        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String columnName = DbHelpers.toDbName(field.getName());
            values[i] = ReflectiveHelpers.getField(field, instance);

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
                ReflectiveHelpers.setField(entityClass.getDeclaredField("id"), instance, rs.getInt(1));
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

    protected Map<String, String> toStringMap(ResultSet rs) throws SQLException {
        Field [] fields = Entity.getDbFields(entityClass);

        Map<String, String> map = new HashMap<>();

        for (Field field : fields) {
            String label = DbHelpers.toDbName(field.getName());
            map.put(label, rs.getString(label));
        }
        return map;
    }

    protected ResultSetMetaData getTableMetaData() throws SQLException {
        if(rsmd == null)
            rsmd = getTableMetaData(tableName);
        return rsmd;
    }

    protected static ResultSetMetaData getTableMetaData(String tableName) throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM "+tableName+" WHERE FALSE").getMetaData();
    }

}
