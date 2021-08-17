package com.online.bookstore.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.online.bookstore.schema.BookServiceResponse;
import com.online.bookstore.util.BookReponseUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestControllerAdvice
public class BookServicesExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(BookServicesExceptionHandler.class);
	
	@ExceptionHandler(value = { BookNotFoundException.class })
	public ResponseEntity<BookServiceResponse> BookNotFoundException(BookNotFoundException ex) {
		logger.info("Inside BookNotFoundException");
		return new ResponseEntity<BookServiceResponse>(
				BookReponseUtil.buildFailureResponse("Requested book is not found", HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { BookUpdationFailedException.class })
	public ResponseEntity<BookServiceResponse> BookUpdationFailedException(BookUpdationFailedException ex) {
		logger.info("Inside BookUpdationFailedException");
		BookServiceResponse bookServiceResponse = BookReponseUtil
				.buildFailureResponse("Error while updating the book details", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<BookServiceResponse>(bookServiceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { BookCreationFailedException.class })
	public ResponseEntity<BookServiceResponse> BookCreationFailedException(BookCreationFailedException ex) {
		logger.info("Inside BookCreationFailedException");
		return new ResponseEntity<BookServiceResponse>(BookReponseUtil.buildFailureResponse(
				"Error while updating the book details", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<BookServiceResponse> ExceptionHandler(Exception ex) {

		logger.info("Inside ExceptionHandler");
		return new ResponseEntity<BookServiceResponse>(
				BookReponseUtil.buildFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
