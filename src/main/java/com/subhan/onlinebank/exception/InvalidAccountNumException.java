package com.subhan.onlinebank.exception;

public class InvalidAccountNumException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	public InvalidAccountNumException(String errorMessage) {
		  super(errorMessage);
	     
	     
	}
	

}
