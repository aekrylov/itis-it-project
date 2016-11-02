package app.misc;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 10:02 PM
 */
public class ValidationException extends Exception {

    private final String field;

    public ValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }




}
