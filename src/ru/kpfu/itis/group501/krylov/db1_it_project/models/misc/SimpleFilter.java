package ru.kpfu.itis.group501.krylov.db1_it_project.models.misc;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.DbHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/15/16 6:57 PM
 */
public class SimpleFilter implements DbFilter<SimpleFilter> {

    private List<String> whereClauses = new LinkedList<>();
    private List<String> columns = new LinkedList<>();
    private String orderBy;
    private List<Object> params = new LinkedList<>();
    private String tableName;

    public SimpleFilter() {
    }
    public SimpleFilter(DAO dao) {
        this.tableName = dao.getTableName();
    }

    private int limit = -1;
    private int offset = 0;

    public SimpleFilter setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public SimpleFilter setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    private String getColumnName(String field) {
        if(field.contains("."))
            return field;
        if(tableName != null) {
            return DbHelpers.toColumnDef(tableName) + "." + DbHelpers.toColumnDef(field);
        }
        return DbHelpers.toColumnDef(field);
    }

    public SimpleFilter addSimpleClause(String clause, Object... params) {
        whereClauses.add(clause);
        columns.add(null);
        Collections.addAll(this.params, params);
        return this;
    }

    private SimpleFilter addClause(String column, String template, Object... params) {
        whereClauses.add(template);
        columns.add(column);
        Collections.addAll(this.params, params);
        return this;
    }

    @Override
    public SimpleFilter addLikeClause(String field, String pattern) {
        if(pattern == null)
            return this;
        return addClause(field, " LOWER(%s) LIKE ?", "%"+pattern.toLowerCase()+"%");
    }

    @Override
    public SimpleFilter addSignClause(String field, String sign, Object value) {
        if(value == null)
            return this;
        return addClause(field, " %s "+sign+" ?", value);
    }

    @Override
    public SimpleFilter addNotNullClause(String field) {
        return addClause(field, " %s IS NOT NULL ");
    }

    @Override
    public SimpleFilter addInClause(String field, Object... params) {
        String str = " %s IN (";
        for(Object ignored : params) {
            str += " ?,";
        }
        return addClause(field, str.substring(0, str.length()-1) + ") ", params);
    }

    public SimpleFilter addAnyFieldLikeClause(String tableName, String like) {
        return addSimpleClause(" lower(concat_ws(', ', "+tableName+".*)) LIKE ? ", "%"+like.toLowerCase()+"%");
    }

    @Override
    public SimpleFilter setOrder(String field, boolean asc) {
        this.orderBy = String.format(" ORDER BY %s %s ", getColumnName(field), asc ? "ASC": "DESC");
        return this;
    }

    @Override
    public String toSQL() {
        String str = getWhere() + getOrderBy();
        if (limit >= 0)
            str += " LIMIT "+limit+" ";
        if(offset > 0)
            str += " OFFSET "+offset+" ";
        return str;
    }

    public void fillParams(PreparedStatement st) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            st.setObject(i+1, param);
        }
    }

    private String getOrderBy() {
        if (orderBy == null)
            return "";
        return orderBy;
    }

    private String getWhere() {
        String str = " WHERE ";
        for (int i = 0; i < whereClauses.size(); i++) {
            String clause = whereClauses.get(i);
            String column = columns.get(i);
            if(column == null) {
                str += clause;
            } else {
                str += String.format(clause, getColumnName(column));
            }
            str += " AND ";
        }
        //workaround AND at the end, dirty way
        str += " TRUE";

        return str;
    }

    List<Object> getParams() {
        return params;
    }
}
