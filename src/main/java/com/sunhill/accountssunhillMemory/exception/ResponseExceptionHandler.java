package com.sunhill.accountssunhillMemory.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	Environment env;	
    
    @ExceptionHandler(value={MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ResponseError> methodArgumentExceptionHandler(MethodArgumentTypeMismatchException ex) {
    	return new ResponseEntity<>(new ResponseError(new GenericException(env.getProperty("app.message.error.argument.exception"))), HttpStatus.NOT_FOUND);
    }  
    
    @ExceptionHandler(value={GenericException.class})
    protected ResponseEntity<ResponseError> methodGenericExceptionHandler(GenericException ex) {
    	if(ex.getStatus()!=null)
    		return new ResponseEntity<>(new ResponseError(ex), ex.getStatus());
    	else
    		return new ResponseEntity<>(new ResponseError(ex), HttpStatus.NOT_FOUND);
    }
    
    
    @ExceptionHandler(value={DuplicateKeyException.class})
    protected ResponseEntity<ResponseError> methodGenericExceptionHandler(DuplicateKeyException ex) {
    	return new ResponseEntity<>(new ResponseError(new GenericException(ex.getLocalizedMessage())), HttpStatus.BAD_REQUEST);
    }    
    
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,  HttpHeaders headers, HttpStatus status, WebRequest request) {      
    	return new ResponseEntity<>(new ResponseError(new GenericException(ex.getLocalizedMessage())), HttpStatus.BAD_REQUEST);
    }   
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(   MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	return new ResponseEntity<>(new ResponseError(new GenericException(ex.getMessage()+". "+env.getProperty("app.message.error.argument.exception"))), HttpStatus.NOT_FOUND);        
    }  
    
    
    
    
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(   HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	return new ResponseEntity<>(new ResponseError(new GenericException(ex.getMessage()+". "+env.getProperty("app.message.error.argument.exception"))), HttpStatus.BAD_REQUEST);        
    } 
    
    @ExceptionHandler(value={Exception.class})
    protected ResponseEntity<ResponseError> methodExceptionHandler(Exception ex) {
    	return new ResponseEntity<>(new ResponseError(new GenericException(env.getProperty("app.message.error.general"))), HttpStatus.NOT_FOUND);
    }   
   
}

