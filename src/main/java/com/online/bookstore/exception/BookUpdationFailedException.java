package com.online.bookstore.exception;

public class BookUpdationFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookUpdationFailedException(String msg) {
		super(msg);
	}
}