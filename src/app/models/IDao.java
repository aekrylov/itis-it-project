package app.models;

import app.entities.Entity;
import app.misc.NotFoundException;

import java.sql.SQLException;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:30 PM
 */
public interface IDao<T extends Entity> {

    List<T> get() throws SQLException;

    T get(int id) throws SQLException, NotFoundException;

    boolean create(T instance) throws SQLException;

    boolean update(T instance) throws SQLException;

    boolean delete(T instance) throws SQLException;

    boolean delete(int id) throws SQLException;
}
