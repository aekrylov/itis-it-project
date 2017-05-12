package ru.kpfu.itis.aekrylov.itproject.models.misc;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/20/16 6:50 PM
 */
public interface DbFilter<T extends DbFilter> {
    T addLikeClause(String field, String pattern);

    T addSignClause(String field, String sign, Object value);

    T addNotNullClause(String field);

    T addInClause(String field, Object... params);

    T setOrder(String field, boolean asc);

    String toSQL();
}
