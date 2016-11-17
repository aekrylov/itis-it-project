package app.models;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/1/16 10:28 PM
 */
public class Products extends DAO<Product> {

    public Products() {
        super("products", Product.class);
    }

}
