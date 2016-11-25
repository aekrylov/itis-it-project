package ru.kpfu.itis.group501.krylov.db1_it_project.models;

import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Entity;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.DbHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.NotFoundException;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ParameterMap;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ReflectiveHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.misc.*;

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
    protected static CustomStatementFactory statementFactory = new CustomStatementFactory(connection);

    private ResultSetMetaData rsmd;

    private String tableName;
    private Class<T> entityClass;

    public String getTableName() {
        return tableName;
    }

    public DAO(String tableName, Class<T> entityClass) {
        this.tableName = tableName;
        this.entityClass = entityClass;
    }

    public DAO(Class<T> entityClass) {
        this.tableName = DbHelpers.getTableName(entityClass);
        this.entityClass = entityClass;
    }

    protected DAO(String tableName) {
        this.tableName = tableName;
        this.entityClass = getTypeParameter(this);
    }

    protected DAO() {
        this.entityClass = getTypeParameter(this);
        this.tableName = DbHelpers.getTableName(entityClass);
    }

    // This magic works for subclasses only
    private static Class getTypeParameter(Object instance) {
        Type superclass =  instance.getClass().getGenericSuperclass();
        return (Class) ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @Override
    public List<T> get() throws SQLException {
        CustomStatement statement = statementFactory.selectAll(entityClass, true);
        ResultSet rs = statement.toPS().executeQuery();

        return getList(rs, statement.getBounds());
    }

    @Override
    public T get(int id ) throws SQLException, NotFoundException {
        CustomStatement statement = statementFactory.selectAll(entityClass, true);
        statement.addFilter(new SimpleFilter(this).addSignClause("id", "=", id));

        PreparedStatement st = statement.toPS();
        ResultSet rs = st.executeQuery();
        return get(rs, statement);
    }

    protected T get(ResultSet rs, CustomStatement statement) throws NotFoundException, SQLException {
        if(rs.next()) {
            return ReflectiveHelpers.fromResultSet2(rs, entityClass, statement.getBounds());
        }
        throw new NotFoundException(entityClass.getSimpleName() + " not found");
    }

    @Override
    public List<T> get(SimpleFilter filter) throws SQLException {
        CustomStatement statement = statementFactory.selectAll(entityClass, true).addFilter(filter);
        PreparedStatement st = statement.toPS();

        ResultSet rs = st.executeQuery();
        return getList(rs, statement.getBounds());
    }

    @Override
    public Map<String, String> getMap(int id) throws SQLException {
        PreparedStatement st = statementFactory.selectAll(tableName).addFilter(
                new SimpleFilter(this).addSignClause("id", "=", id)).toPS();

        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return toStringMap(rs);
        }
        throw new NoSuchElementException(entityClass.getSimpleName() + " not found");
    }

    @Override
    public int count() throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT count(1) FROM "+ tableName);
        ResultSet rs = st.executeQuery();
        if(!rs.next()){
            return 0;
        }
        return rs.getInt(1);
    }

    @Override
    public int count(SimpleFilter filter) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT count(1) FROM "+ tableName +" "+filter.toSQL());

        filter.fillParams(st);

        ResultSet rs = st.executeQuery();
        if(!rs.next()) {
            return 0;
        }
        return rs.getInt(1);
    }

    @Override
    public boolean update(T instance) throws SQLException {
        Field [] fields = Entity.getDbFieldsWithoutId(instance.getClass());

        Map<String, Object> set = new HashMap<>();

        for (Field field : fields) {
            set.put(field.getName(), ReflectiveHelpers.getField(field, instance));
        }

        PreparedStatement st = statementFactory.update(tableName, set)
                .addFilter(new SimpleFilter().addSignClause("id", "=", instance.getId())).toPS();
        return st.executeUpdate() > 0;
    }

    //TODO remove SQL
    @Override
    public boolean update(ParameterMap map) throws SQLException {
        //return update((T) Entity.getEntity(map, entityClass));
        int id = Integer.parseInt(map.get("id"));

        ResultSetMetaData rsmd = getTableMetaData();
        int [] sqlTypes = new int[rsmd.getColumnCount()];
        Object [] values = new Object[rsmd.getColumnCount()];

        String set = "";
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            String columnName = rsmd.getColumnName(i+1);
            sqlTypes[i] = rsmd.getColumnType(i+1);
            values[i] = map.get(columnName);

            set += String.format(" %s = ?,", DbHelpers.toColumnDef(columnName));
        }

        PreparedStatement st = connection.prepareStatement(
                "UPDATE "+tableName+" SET "+set.substring(0, set.length()-1)+" WHERE id = ?");
        for (int i = 0; i < sqlTypes.length; i++) {
            st.setObject(i+1, values[i], sqlTypes[i]);
        }
        st.setInt(values.length+1, id);

        return st.executeUpdate() > 0;
    }

    protected List<T> getList(ResultSet rs, ColumnBounds bounds) throws SQLException {
        List<T> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            list.add(ReflectiveHelpers.fromResultSet2(rs, entityClass, bounds));
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

    @Override
    public List<Object[]> getTable() throws SQLException {
        return getTableStatic(tableName);
    }

    @Override
    public List<Object[]> getTable(SimpleFilter filter) throws SQLException {
        return getTableStatic(tableName, filter);
    }

    public static List<Object[]> getTableStatic(String tableName) throws SQLException {
        PreparedStatement st = statementFactory.selectAll(tableName).toPS();

        ResultSet rs = st.executeQuery();
        return getTableStatic(tableName, rs);
    }

    public static List<Object[]> getTableStatic(String tableName, SimpleFilter filter) throws SQLException {
        PreparedStatement st = statementFactory.selectAll(tableName).addFilter(filter).toPS();

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

    @Override
    public boolean create(Map<String, String> map) throws SQLException {
        return create(Entity.getEntity(map, entityClass));
    }

    //TODO remove SQL
    @Override
    public boolean create(T instance) throws SQLException {
        Field [] fields = Entity.getDbFieldsWithoutId(entityClass);

        String columnString = "(";
        String valueString = "(";

        //get table schema
        ResultSet columns = statementFactory.selectAll(tableName).addFilter(new SimpleFilter(this).setLimit(0))
                .toPS().executeQuery();
        ResultSetMetaData metaData = getTableMetaData();

        Object[] values = new Object[fields.length];
        int[] sqlTypes = new int[fields.length];

        //compose prepared statement
        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String columnName = DbHelpers.toDbName(field.getName());
            values[i] = ReflectiveHelpers.getField(field, instance);

            int index = columns.findColumn(columnName);

            sqlTypes[i] = metaData.getColumnType(index);

            columnString = columnString + "\"" + columnName + "\",";
            valueString = valueString + "?,";
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
                st.setObject(i+1, values[i], sqlTypes[i]);
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
        PreparedStatement st = statementFactory.delete(tableName, new SimpleFilter().addSignClause("id", "=", id)).toPS();

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
        PreparedStatement st = statementFactory.selectAll(tableName).addFilter(new SimpleFilter().setLimit(0)).toPS();
        return st.getMetaData();
    }

}
