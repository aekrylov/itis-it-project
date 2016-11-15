package app.models;

import java.util.LinkedList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/15/16 6:57 PM
 */
public class SimpleFilter {

    private List<String> whereClauses = new LinkedList<>();
    private List<Object> params = new LinkedList<>();

    public void addLikeClause(String field, String pattern) {
        if(pattern == null)
            return;
        whereClauses.add(String.format(" \"%s\" LIKE ?", field));
        params.add("%"+pattern+"%");
    }

    public void addSignClause(String field, String sign, Object value) {
        if(value == null)
            return;
        whereClauses.add(String.format(" \"%s\" %s ?", field, sign));
        params.add(value);
    }

    String getWhere() {
        String str = "WHERE ";
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
