package finalproject.suppliersystem.core.errorhandling;

/**
 * Custom Exception to throw
 * https://nixmash.com/java/custom-404-exception-handling-in-spring-mvc/
 */
public class UnknownResourceException extends Exception
{
    /**
     * The serialVersionUID is used to verify that the de-serialized
     * https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/io/Serializable.html
     */
    private static final long serialVersionUID = 100L;

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
