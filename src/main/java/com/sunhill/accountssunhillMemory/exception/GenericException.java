package com.sunhill.accountssunhillMemory.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GenericException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericException.class);
	
	private  String  code ="error";	
	private  String type="";	
	private  String message ="";		
	
	
	public GenericException() { super(); }

	public GenericException(String message) { 
		super(message); 
		manageException( message,  new Exception());
	}

	public GenericException(String message, Throwable cause) {
		super(message, cause); 
		manageException( message,  cause);		
	}
	
	public GenericException(Throwable cause) {
		super(cause); 
		manageException( cause.getMessage(),  cause);		
	}
	
	private void manageException( String message, Throwable cause){			
		this.message=message;			
		this.type=cause.getClass().getName();
		LOGGER.error("GenericException message: {} typeException: {} Exception: {} ",this.message,this.type, cause);		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
	
}
