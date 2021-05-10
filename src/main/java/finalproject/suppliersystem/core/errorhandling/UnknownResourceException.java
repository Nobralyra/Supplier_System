package finalproject.suppliersystem.core;

/**
 * Custom Exception to throw
 * https://nixmash.com/java/custom-404-exception-handling-in-spring-mvc/
 */
public class UnknownResourceException extends Exception
{
    private static final long serialVersionUID = 3613978896775863909L;

    private String message;

    public UnknownResourceException() {
        super();
    }

    public UnknownResourceException(String message) {
        this.message = System.currentTimeMillis()
                + ": " + message;
    }

    public String getMessage() {
        return message;
    }
}
