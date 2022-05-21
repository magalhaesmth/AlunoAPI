package com.dev.crud.exception;

public class ResourceAlreadyExistsException extends Exception {
	
	public ResourceAlreadyExistsException() {
	}
	public ResourceAlreadyExistsException(String msg) {
		super(msg);
	}
}
