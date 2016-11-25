package ru.kpfu.itis.group501.krylov.db1_it_project.models.misc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/23/16 5:14 PM
 */
public class CustomStatement {

    private String sql;
    private List<Object> params = new ArrayList<>();
    private ColumnBounds bounds;
    private Connection connection;

    CustomStatement(String sql, ColumnBounds bounds, Connection connection) {
        this.sql = sql;
        this.bounds = bounds;
        this.connection = connection;
    }

    public CustomStatement addParams(List<Object> add) {
        params.addAll(add);
        return this;
    }

    public CustomStatement addFilter(SimpleFilter filter) {
        sql += filter.toSQL();
        params.addAll(filter.getParams());
        return this;
    }

    public PreparedStatement toPS() throws SQLException {
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
