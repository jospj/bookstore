package com.online.bookstore.util;

import org.springframework.http.HttpStatus;

import com.online.bookstore.model.Book;
import com.online.bookstore.schema.BookServiceResponse;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookReponseUtil {

	public static BookServiceResponse<?> buildSuccessResponse(String successMsg, Book b) {
		if(b==null) {
			return new BookServiceResponse<Object>(successMsg);
		} else {
			return new BookServiceResponse<Object>(successMsg, b);
		} 
	}
	
	public static BookServiceResponse<?> buildFailureResponse(String errorMsg, int errorCode) {
		return new BookServiceResponse<Object>(errorMsg, errorCode); 
	}
}
