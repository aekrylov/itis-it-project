package ru.kpfu.itis.group501.krylov.db1_it_project.models.misc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/23/16 5:14 PM
 */
public class CustomStatement {

    private String sql;
    private List<Object> params = new ArrayList<>();
    private ColumnBounds bounds;
    private Map<String, String> aliases;

    public CustomStatement(String sql, ColumnBounds bounds) {
        this.sql = sql;
        this.bounds = bounds;
    }

    public CustomStatement(String sql, ColumnBounds bounds, Map<String, String> aliases) {
        this.sql = sql;
        this.bounds = bounds;
        this.aliases = aliases;
    }

    public CustomStatement addFilter(SimpleFilter filter) {
        sql += filter.toSQL();
        params.addAll(filter.getParams());
        return this;
    }

    public PreparedStatement toPS(Connection connection) throws SQLException {
        PreparedStatement st = connection.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            st.setObject(i+1, params.get(i));
        }

        return st;
    }

    public ColumnBounds getBounds() {
        return bounds;
    }
}
