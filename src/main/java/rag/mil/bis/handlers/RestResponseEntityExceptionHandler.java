package rag.mil.bis.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rag.mil.bis.exceptions.EventNotFoundException;
import rag.mil.bis.exceptions.IdInconsistencyException;
import rag.mil.bis.exceptions.PdfGenerationException;
import rag.mil.bis.exceptions.security.BadSecurityHeaderException;
import rag.mil.bis.exceptions.security.MissingSecurityHeaderException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ex.getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage() + "\n")
                .collect(Collectors.joining("\n")), headers, status, request);
    }

    @ExceptionHandler(value = {IdInconsistencyException.class, MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class, MissingSecurityHeaderException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleBadRequest(RuntimeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {BadSecurityHeaderException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected String handleUnauthorized(BadSecurityHeaderException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {EventNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected String handleNotFound(EventNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {PdfGenerationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected String handleServiceUnavailable(PdfGenerationException ex) {
        return ex.getMessage();
    }
}

