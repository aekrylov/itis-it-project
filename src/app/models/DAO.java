package app.models;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:17 PM
 */
public abstract class DAO {
    protected static Class objectClass;
    protected DAO(Class objectClass) {
        this.objectClass = objectClass;
    }
    protected DAO() {}

    protected static Connection connection = DB.getInstance().getConnection();

    protected static <E> E fromResultSet(ResultSet rs, Class c) throws SQLException {
        Field [] fields = c.getDeclaredFields();

        Object instance;
        try {
            instance = c.newInstance();
            for (Field field : fields) {
                String label = field.getName().replaceAll("(A-Z)", "_$1").toLowerCase();
                int index = rs.findColumn(label);

                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(instance, rs.getObject(index));
                field.setAccessible(accessible);
            }
            return (E) instance;

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
