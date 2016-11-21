package ru.kpfu.itis.group501.krylov.db1_it_project.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/15/16 6:57 PM
 */
public class SimpleFilter implements DbFilter {

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
    public void addLikeClause(String field, String pattern) {
        if(pattern == null)
            return;
        whereClauses.add(String.format(" LOWER(\"%s\") LIKE ?", field));
        params.add("%"+pattern.toLowerCase()+"%");
    }

    @Override
    public void addSignClause(String field, String sign, Object value) {
        if(value == null)
            return;
        whereClauses.add(String.format(" \"%s\" %s ?", field, sign));
        params.add(value);
    }

    @Override
    public void addNotNullClause(String field) {
        whereClauses.add(String.format(" \"%s\" IS NOT NULL ", field));
    }

    @Override
    public void addInClause(String field, Object... params) {
        String str = String.format(" \"%s\" IN (", field);
        for(Object param: params) {
            str += " ?,";
            this.params.add(param);
        }

        whereClauses.add(str.substring(0, str.length()-1) + ") ");
    }

    public void addAnyFieldLikeClause(String tableName, String like) {
        whereClauses.add(" lower(concat_ws(', ', "+tableName+".*)) LIKE ? ");
        params.add("%"+like.toLowerCase()+"%");
    }

    public void addSimpleClause(String clause, Object... params) {
        whereClauses.add(clause);
        Collections.addAll(this.params, params);
    }

    @Override
    public void setOrder(String field, boolean asc) {
        this.orderBy = String.format(" ORDER BY \"%s\" %s ", field, asc ? "ASC": "DESC");
    }

    @Override
    public String getSQL() {
        String str = getWhere() + getOrderBy();
        if (limit >= 0)
            str += " LIMIT "+limit+" ";
        if(offset > 0)
            str += " OFFSET "+offset+" ";
        return str;
    }

    String getOrderBy() {
        if (orderBy == null)
            return "";
        return orderBy;
    }

    String getWhere() {
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
