package ru.kpfu.itis.group501.krylov.db1_it_project.models;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.DbHelpers;

import java.sql.Connection;
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
    private String orderBy;
    private List<Object> params = new LinkedList<>();

    private int limit = -1;
    private int offset = 0;

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public SimpleFilter addLikeClause(String field, String pattern) {
        if(pattern == null)
            return this;
        whereClauses.add(String.format(" LOWER(\"%s\") LIKE ?", field));
        params.add("%"+pattern.toLowerCase()+"%");
        return this;
    }

    @Override
    public SimpleFilter addSignClause(String field, String sign, Object value) {
        if(value == null)
            return this;
        String columnName = field.indexOf('.') != -1 ? field : DbHelpers.toColumnDef(field);
        whereClauses.add(String.format(" %s %s ?", columnName, sign));
        params.add(value);
        return this;
    }

    @Override
    public SimpleFilter addNotNullClause(String field) {
        whereClauses.add(String.format(" \"%s\" IS NOT NULL ", field));
        return this;
    }

    @Override
    public SimpleFilter addInClause(String field, Object... params) {
        String str = String.format(" \"%s\" IN (", field);
        for(Object param: params) {
            str += " ?,";
            this.params.add(param);
        }

        whereClauses.add(str.substring(0, str.length()-1) + ") ");
        return this;
    }

    public SimpleFilter addAnyFieldLikeClause(String tableName, String like) {
        whereClauses.add(" lower(concat_ws(', ', "+tableName+".*)) LIKE ? ");
        params.add("%"+like.toLowerCase()+"%");
        return this;
    }

    @Override
    public SimpleFilter setOrder(String field, boolean asc) {
        this.orderBy = String.format(" ORDER BY \"%s\" %s ", field, asc ? "ASC": "DESC");
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
        for (String clause: whereClauses) {
            str += clause + " AND ";
        }
        //workaround AND at the end, dirty way
        str += " TRUE";

        return str;
    }

    List<Object> getParams() {
        return params;
    }
}
