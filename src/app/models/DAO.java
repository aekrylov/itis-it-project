package app.models;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
        //Class c = objectClass;

        Field [] fields = c.getDeclaredFields();
        Class [] types = new Class[fields.length];
        Object [] values = new Object[fields.length];

        ResultSetMetaData metaData = rs.getMetaData();

        for (int i = 0; i < fields.length; i++) {
            String label = fields[i].getName().replaceAll("(A-Z)", "_$1").toLowerCase();
            // String label = fields[i].getAnnotationsByType(DbColumn.class)[0].value();
            int index = rs.findColumn(label);
            types[i] = fields[i].getType();
            values[i] = types[i].cast(rs.getObject(index));
        }

        try {
            Constructor<E> constructor = c.getConstructor(types);
            return constructor.newInstance(values[0], values[1], values[2], values[3], values[4], values[5]);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
