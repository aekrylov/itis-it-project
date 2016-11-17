package app.models;

import java.sql.SQLException;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:30 PM
 */
public interface IDao {

    List<Entity> get() throws SQLException;

    boolean create(Object instance, Class<? extends Entity> c) throws SQLException;
}
