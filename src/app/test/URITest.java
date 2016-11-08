package app.test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/8/16 5:55 PM
 */
public class URITest {

    public static void main(String[] args) throws URISyntaxException, MalformedURLException {
        URI uri = new URI("фоо?bar=baz baz");
        System.out.println(uri);
    }
}
