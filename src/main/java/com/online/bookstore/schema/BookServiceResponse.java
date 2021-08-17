package com.online.bookstore.schema;

import java.io.Serializable;

import com.online.bookstore.model.Book;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookServiceResponse<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private Boolean success;
	private String successMsg;
	private T content;
	private ErrorResponse error;
	
	public BookServiceResponse(String errorMsg, int errorCode) {
		this.success = false;
		this.error = new ErrorResponse(errorMsg,errorCode);
	}
	
	public BookServiceResponse(String successMsg) {
		this.success = true;
		this.successMsg = successMsg;
		this.error = null;
	}
	
	public BookServiceResponse(String successMsg, Book book) {
		this.success = true;
		this.content = (T) book;
		this.successMsg = successMsg;
		this.error = null;
	}
}
