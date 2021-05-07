package finalproject.suppliersystem.core;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.List;

/**
 * Global error handling component
 * <p>
 * https://www.baeldung.com/exception-handling-for-rest-with-spring
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    /**
     * ToDo: Make custom error site:
     * https://zetcode.com/springboot/controlleradvice/
     * https://medium.com/@jovannypcg/understanding-springs-controlleradvice-cd96a364033f
     */

    /*
     * Probably some exceptions that can be added to the exceptionhandler
     * https://stackoverflow.com/questions/55610797/what-exceptions-can-be-thrown-by-exchange-on-webclient
     */

    /**
     * HttpStatus.UNPROCESSABLE_ENTITY = 422
     * Where the exception handler comes from
     * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
     * <p>
     * MethodArgumentNotValidException: This exception is thrown when argument annotated with @Valid failed validation
     *
     * @param exception MethodArgumentNotValidException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return ResponseEntity<Object>
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        List<String> errors = new ArrayList<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors())
        {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : exception.getBindingResult().getGlobalErrors())
        {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, exception.getLocalizedMessage(), errors);
        return handleExceptionInternal(exception, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * HttpStatus.BAD_REQUEST = 400
     * <p>
     * HttpMessageNotReadableException: This exception is thrown when request body is invalid
     *
     * @param exception HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return ResponseEntity<Object>
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        String exceptionCause = getExceptionCause(exception.getCause());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), exceptionCause);
        return handleExceptionInternal(exception, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * HttpStatus.METHOD_NOT_ALLOWED = 405
     * <p>
     * HttpRequestMethodNotSupportedException: This exception is thrown when you send a requested with an unsupported HTTP method
     *
     * @param exception HttpRequestMethodNotSupportedException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return ResponseEntity<Object>
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(exception.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        exception.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, exception.getLocalizedMessage(), builder.toString());
        return handleExceptionInternal(exception, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * HttpStatus.UNSUPPORTED_MEDIA_TYPE = 415
     * <p>
     * HttpMediaTypeNotSupportedException – which occurs when the client send a request with unsupported media type
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return ResponseEntity<Object>
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception, final HttpHeaders headers, final HttpStatus status, final WebRequest request)
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(exception.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        exception.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return handleExceptionInternal(exception, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * <p>
     * https://www.baeldung.com/spring-response-status-exception
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception Exception
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({ResponseStatusException.class})
    public String handleResponseStatusException(ResponseStatusException exception, Model model)
    {
        ApiError apiError = new ApiError(exception.getStatus(), exception.getLocalizedMessage(), exception.getReason());

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());
        return "error";
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception DataIntegrityViolationException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    public String handleDataIntegrityViolationException(DataIntegrityViolationException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception InvalidDataAccessResourceUsageException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({InvalidDataAccessResourceUsageException.class})
    public String handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    /**
     * HttpStatus.BAD_REQUEST = 400
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLGrammarException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({SQLGrammarException.class})

    public String handleSQLGrammarException(SQLGrammarException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    /**
     * HttpStatus.INTERNAL_SERVER_ERROR = 500
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLGrammarException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({ConnectException.class})
    public String handleConnectException(ConnectException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    /**
     * HttpStatus.INTERNAL_SERVER_ERROR = 500
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLNonTransientConnectionException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({SQLNonTransientConnectionException.class})
    public String handleSQLNonTransientConnectionException(SQLNonTransientConnectionException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    /**
     * HttpStatus.INTERNAL_SERVER_ERROR = 500
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({SQLException.class})
    public String handleSQLException(SQLException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    /**
     * HttpStatus.INTERNAL_SERVER_ERROR = 500
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({PersistenceException.class})
    public String handlePersistenceException(PersistenceException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    /**
     * HttpStatus.NOT_FOUND = 404
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception EntityNotFoundException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({EntityNotFoundException.class})
    public String handleEntityNotFoundException(EntityNotFoundException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    /**
     * Custom 404 Page Not Found with Custom exception class
     * https://nixmash.com/java/custom-404-exception-handling-in-spring-mvc/
     *
     * @param exception UnknownResourceException
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({UnknownResourceException.class})
    public String handleUnknownResourceException(UnknownResourceException exception, Model model)
    {
        String exceptionCause = getExceptionCause(exception.getCause());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(), exceptionCause);

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "404";
    }

    /**
     * A default exception handler that deals with all others exceptions that don't have specific handlers
     * <p>
     * HttpStatus.INTERNAL_SERVER_ERROR = 500
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception Exception
     * @param model     Model
     * @return String
     */
    @ExceptionHandler({Exception.class})
    public String handleAll(Exception exception, Model model)
    {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "error occurred");

        model.addAttribute("httpStatus", apiError.getStatus().toString());
        model.addAttribute("errorMessage", apiError.getMessage());
        model.addAttribute("exceptionCause", apiError.getErrors());

        return "error";
    }

    private String getExceptionCause(Throwable cause)
    {
        String exceptionCause = "Cause is null";

        if (cause != null)
        {
            exceptionCause = cause.toString();
        }
        return exceptionCause;
    }
}
