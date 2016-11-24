package ru.kpfu.itis.group501.krylov.db1_it_project.models;

import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Entity;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.DbHelpers;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
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

    public <E extends Entity> CustomStatement selectAll(Class<E> entityClass) throws SQLException {
        String tableName = DbHelpers.getTableName(entityClass);
        String sql = "SELECT * FROM "+tableName+" ";

        int fieldCount = getColumnCount(tableName);
        ColumnBounds bounds = new ColumnBounds();
        bounds.put(null, 1, fieldCount);

        Field[] fields = Entity.getDbFields(entityClass);
        for(Field field: fields) {
            if(Entity.class.isAssignableFrom(field.getType())) {
                Class<Entity> eType = (Class<Entity>) field.getType();
                // add join
                String joinedTable = DbHelpers.getTableName(eType);
                String tableAlias = DbHelpers.toDbName("t"+field.getName());

                String lhs = tableName+"." + field.getName();
                String rhs= tableAlias+".id";

                sql += String.format(" JOIN %s %s on %s = %s ", joinedTable, tableAlias, lhs, rhs);

                bounds.put(field, fieldCount+1, fieldCount+getColumnCount(joinedTable));
                fieldCount += getColumnCount(joinedTable);
            }
        }

        return new CustomStatement(sql, bounds);
    }


}
