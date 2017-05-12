package ru.kpfu.itis.aekrylov.itproject.models.misc;

import ru.kpfu.itis.aekrylov.itproject.entities.Entity;
import ru.kpfu.itis.aekrylov.itproject.misc.DbHelpers;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/23/16 5:51 PM
 */
public class CustomStatementFactory {

    private Map<String, Integer> columnCounts = new HashMap<>();
    private Connection connection;

    public CustomStatementFactory(Connection connection) {
        this.connection = connection;
    }

    private int getColumnCount(String tableName) throws SQLException {
        if(!columnCounts.containsKey(tableName)) {
            ResultSetMetaData rsmd = connection.createStatement()
                    .executeQuery("SELECT * FROM " +tableName +" WHERE FALSE")
                    .getMetaData();
            columnCounts.put(tableName, rsmd.getColumnCount());
        }
        return columnCounts.get(tableName);
    }

    public <E extends Entity> CustomStatement selectAll(Class<E> entityClass, boolean autoJoin) throws SQLException {
        String tableName = DbHelpers.getTableName(entityClass);
        String sql = "SELECT * FROM "+tableName+" ";

        if(autoJoin) {
            int fieldCount = getColumnCount(tableName);
            ColumnBounds bounds = new ColumnBounds();
            bounds.put(null, 1, fieldCount);

            Field[] fields = Entity.getDbFields(entityClass);
            //count entity types
            //if for some entity type there's only one field with that type, we don't use alias for corresponding table
            Map<Class, Integer> counts = new HashMap<>();
            for(Field field: fields) {
                Class type = field.getType();
                counts.putIfAbsent(type, 0);
                counts.put(type, counts.get(type)+1);
            }

            //add joins
            for(Field field: fields) {
                Class type = field.getType();
                if(Entity.class.isAssignableFrom(type)) {
                    Class<Entity> eType = (Class<Entity>) type;
                    String joinedTable = DbHelpers.getTableName(eType);
                    String tableAlias = counts.get(type) > 1 ? DbHelpers.toDbName("t"+field.getName()) : joinedTable;

                    String lhs = tableName+"." + field.getName();
                    String rhs= tableAlias+".id";

                    sql += String.format(" JOIN %s %s on %s = %s ", joinedTable, tableAlias, lhs, rhs);

                    bounds.put(field, fieldCount+1, fieldCount+getColumnCount(joinedTable));
                    fieldCount += getColumnCount(joinedTable);
                }
            }

            return new CustomStatement(sql, bounds, connection);
        }
        return new CustomStatement(sql, null, connection);
    }

    public CustomStatement selectAll(String tableName) {
        tableName = DbHelpers.toDbName(tableName);
        String sql = "SELECT * FROM "+tableName+" ";

        return new CustomStatement(sql, null, connection);
    }

    public CustomStatement delete(String tableName, SimpleFilter filter) {
        return new CustomStatement("DELETE FROM "+tableName+" ", null, connection).addFilter(filter);
    }

    public CustomStatement update(String tableName, Map<String, Object> set) {
        String sql = "UPDATE "+tableName+" SET ";
        List<Object> params = new ArrayList<>();
        for(Map.Entry<String, Object> entry: set.entrySet()) {
            sql += String.format(" %s = ?,", DbHelpers.toColumnDef(entry.getKey()));
            params.add(entry.getValue());
        }
        return new CustomStatement(sql.substring(0, sql.length()-1), null, connection).addParams(params);
    }


}
