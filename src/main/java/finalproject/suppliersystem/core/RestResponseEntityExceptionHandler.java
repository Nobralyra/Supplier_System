package finalproject.suppliersystem.core;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
     * https://www.baeldung.com/exception-handling-for-rest-with-spring
     * https://medium.com/@jovannypcg/understanding-springs-controlleradvice-cd96a364033f
     */

    /*
     * Probably some exceptions that can be added to the exceptionhandler
     * https://stackoverflow.com/questions/55610797/what-exceptions-can-be-thrown-by-exchange-on-webclient
     */

    /**
     * HttpStatus.BAD_REQUEST = 400
     * Where the exception handler comes from
     * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
     * <p>
     * MethodArgumentNotValidException: This exception is thrown when argument annotated with @Valid failed validation
     *
     * @param exception MethodArgumentNotValidException
     * @param headers
     * @param status
     * @param request
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

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), errors);
        return handleExceptionInternal(exception, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * HttpStatus.BAD_REQUEST = 400
     *
     * HttpMessageNotReadableException: This exception is thrown when request body is invalid
     *
     * @param exception HttpMessageNotReadableException
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity<Object>
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        String exceptionCause = "Cause is null";

        if (exception.getCause() != null)
        {
            exceptionCause = exception.getCause().toString();
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), exceptionCause);
        return handleExceptionInternal(exception, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * HttpStatus.METHOD_NOT_ALLOWED = 405
     * <p>
     * HttpRequestMethodNotSupportedException: This exception is thrown when you send a requested with an unsupported HTTP method
     *
     * @param exception HttpRequestMethodNotSupportedException
     * @param headers
     * @param status
     * @param request
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
     * By default, 4xx or 5xx responses result in an WebClientResponseException, including sub-classes for specific HTTP status codes.
     * To catch those exceptions the method is annotated with @ExceptionHandler that listens after if WebClientResponseException is thrown
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     * <p>
     * https://github.com/spring-projects/spring-framework/blob/master/src/docs/asciidoc/web/webflux-webclient.adoc#retrieve
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClientResponseException.html
     *
     * @param exception WebClientResponseException
     * @param request   WebRequest
     * @return ResponseEntity<Object>
     */
//    @ExceptionHandler({WebClientResponseException.class})
//    public ResponseEntity<Object> handleWebClientResponseException(WebClientResponseException exception, WebRequest request)
//    {
//        ApiError apiError = new ApiError(exception.getStatusCode(), exception.getLocalizedMessage(), exception.getResponseBodyAsString());
//
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

    /**
     * Handle if certname is empty
     * <p>
     * https://www.baeldung.com/spring-response-status-exception
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception Exception
     * @param request   WebRequest
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException exception, WebRequest request)
    {
        ApiError apiError = new ApiError(exception.getStatus(), exception.getLocalizedMessage(), exception.getReason());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception DataIntegrityViolationException
     * @param request
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request)
    {
        String exceptionCause = "Cause is null";

        if (exception.getCause() != null)
        {
            exceptionCause = exception.getCause().toString();
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception InvalidDataAccessResourceUsageException
     * @param request
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({InvalidDataAccessResourceUsageException.class})
    public ResponseEntity<Object> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException exception, WebRequest request)
    {
        String exceptionCause = "Cause is null";

        if (exception.getCause() != null)
        {
            exceptionCause = exception.getCause().toString();
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLGrammarException
     * @param request
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({SQLGrammarException.class})
    public ResponseEntity<Object> handleSQLGrammarException(SQLGrammarException exception, WebRequest request)
    {
        String exceptionCause = "Cause is null";

        if (exception.getCause() != null)
        {
            exceptionCause = exception.getCause().toString();
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLGrammarException
     * @param request
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({ConnectException.class})
    public ResponseEntity<Object> handleConnectException(ConnectException exception, WebRequest request)
    {
        String exceptionCause = "Cause is null";

        if (exception.getCause() != null)
        {
            exceptionCause = exception.getCause().toString();
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLNonTransientConnectionException
     * @param request
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({SQLNonTransientConnectionException.class})
    public ResponseEntity<Object> handleSQLNonTransientConnectionException(SQLNonTransientConnectionException exception, WebRequest request)
    {
        String exceptionCause = "Cause is null";

        if (exception.getCause() != null)
        {
            exceptionCause = exception.getCause().toString();
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLException
     * @param request
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> handleSQLException(SQLException exception, WebRequest request)
    {
        String exceptionCause = "Cause is null";

        if (exception.getCause() != null)
        {
            exceptionCause = exception.getCause().toString();
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * HttpStatus.CONFLICT = 409
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception SQLException
     * @param request
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({PersistenceException.class})
    public ResponseEntity<Object> handlePersistenceException(PersistenceException exception, WebRequest request)
    {
        String exceptionCause = "Cause is null";

        if (exception.getCause() != null)
        {
            exceptionCause = exception.getCause().toString();
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(), exceptionCause);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    /**
     * HttpStatus.UNSUPPORTED_MEDIA_TYPE = 415
     * <p>
     * HttpMediaTypeNotSupportedException – which occurs when the client send a request with unsupported media type
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception HttpMediaTypeNotSupportedException
     * @param headers
     * @param status
     * @param request
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
     * A default exception handler that deals with all others exceptions that don't have specific handlers
     * <p>
     * HttpStatus.INTERNAL_SERVER_ERROR = 500
     * <p>
     * Very IMPORTANT that the exception declared with @ExceptionHandler matches the exceptions uses as a argument of the method
     *
     * @param exception Exception
     * @param request   WebRequest
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception exception, WebRequest request)
    {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
