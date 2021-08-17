package com.online.bookstore.exception;

public class BookCreationFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookCreationFailedException(String msg) {
		super(msg);
	}
}