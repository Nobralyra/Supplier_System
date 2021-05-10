package finalproject.suppliersystem.core.errorhandling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Custom error message
 */
@Getter
@Setter
public class ApiError
{
    /**
     * The HTTP status code
     */
    private HttpStatus status;

    /**
     * The error message associated with exception
     */
    private String message;

    /**
     * List of constructed error messages
     */
    private List<String> errors;

    public ApiError()
    {
        super();
    }


    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
