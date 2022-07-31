package com.subhan.onlinebank.exception;

public class DuplicateDataException extends RuntimeException {	
	private static final long serialVersionUID = 1L;
	String message;
	public DuplicateDataException(String message) {
		super(message);
		this.message = message;
	}

	

}
