package app.test;

import app.models.User;
import app.models.Users;

import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 8:02 PM
 */
public class DAOTest {
    public static void main(String[] args) throws SQLException {

        test1();

    }

    public static void test1() {
        Integer value = 3;
        System.out.println(Integer.class.isAssignableFrom(value.getClass()));
        System.out.println(Integer.class.isInstance(value));
        System.out.println(Integer.class.isInstance(3));
        System.out.println(int.class.isInstance(value));
    }
}
