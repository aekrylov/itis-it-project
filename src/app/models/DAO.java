package app.models;

import java.sql.Connection;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:17 PM
 */
public class DAO {
    protected DAO() {}
    protected static Connection connection = DB.getInstance().getConnection();
}
