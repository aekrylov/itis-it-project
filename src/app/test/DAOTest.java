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
/*
        Product product = new Product();
        product.setName("TEst1");
        product.setPrice(1750);

        Products.createPost(product);
*/

/*
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            User user = new User(0, "foo", "bar", "baz", "beee", "link", 4.5);
            Users.createPost(user);
        }
        System.out.println("Users.createPost: " + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            User user = new User(0, "foo", "bar", "baz", "beee", "link", 4.5);
            Users.createPost(user);
        }
        System.out.println("Users.createPost: " + (System.currentTimeMillis() - time));
*/
        test1();

    }

    public static void test1() {
        System.out.println(int.class.isAssignableFrom(Integer.class));
    }
}
