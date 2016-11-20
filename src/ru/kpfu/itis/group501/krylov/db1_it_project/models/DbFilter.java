package ru.kpfu.itis.group501.krylov.db1_it_project.models;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/20/16 6:50 PM
 */
public interface DbFilter {
    void addLikeClause(String field, String pattern);

    void addSignClause(String field, String sign, Object value);

    void addNotNullClause(String field);

    void addInClause(String field, Object... params);

    void setOrder(String field, boolean asc);
}
