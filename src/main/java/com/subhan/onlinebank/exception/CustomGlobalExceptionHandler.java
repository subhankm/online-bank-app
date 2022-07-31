package com.subhan.onlinebank.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	 DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
	// Global Exception Handler
	  @ExceptionHandler(Exception.class)
	    public ResponseEntity<Object> handleExceptions( Exception exception, WebRequest webRequest) {
	        ErrorResponse response = new ErrorResponse();
	        response.setDateTime(LocalDateTime.now().format(format));
	        response.setMessage(exception.getMessage());
	        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	        return entity;
	    }
	
	
    // Error handle for bean validation @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().format(format));
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }
    // specific exception 
    @ExceptionHandler(InvalidAccountNumException.class)
    public ResponseEntity<Object> InvalidAccountNumException( InvalidAccountNumException exception, WebRequest webRequest) {
        ErrorResponse response = new ErrorResponse();
        response.setDateTime(LocalDateTime.now().format(format));
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        return entity;
    }
    
    // specific exception 
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> insufficientBalanceException(InsufficientBalanceException exception, WebRequest webRequest) {
        ErrorResponse response = new ErrorResponse();
        response.setDateTime(LocalDateTime.now().format(format));
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        return entity;
    }
    

    // specific exception 
    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<Object> duplicateDataException(DuplicateDataException exception, WebRequest webRequest) {
        ErrorResponse response = new ErrorResponse();
        response.setDateTime(LocalDateTime.now().format(format));
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        return entity;
    }

}
