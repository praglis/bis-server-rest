package rag.mil.bis.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EventNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected String handleConflict(EventNotFoundException ex) {
        return ex.getMessage();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ex.getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage() + "\n")
                .collect(Collectors.joining("\n")), headers, status, request);
    }

    @ExceptionHandler(value = {IdInconsistencyException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleBadRequest(IdInconsistencyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {PdfGenerationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected String handleBadRequest(PdfGenerationException ex) {
        return ex.getMessage();
    }
}
