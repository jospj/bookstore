package com.online.bookstore.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.online.bookstore.model.Book;
import com.online.bookstore.schema.BookServiceResponse;
import com.online.bookstore.service.BookService;
import com.online.bookstore.util.BookReponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/books")
public class BookStoreController {

	private static final Logger logger = LoggerFactory.getLogger(BookStoreController.class);

	@Autowired
	private BookService bookService;

	@GetMapping
	@Operation(summary = "Get all books")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found all books", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Book.class))) }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public Flux<Book> getAllBooks() {
		logger.info("Inside getAllBooks()");
		return bookService.getAllBooks();
	}

	@GetMapping("{isbn}")
	@Operation(summary = "Get a book by its ISBN")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the book", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookServiceResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ISBN supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
	public Mono<ResponseEntity<BookServiceResponse>> getBook(@PathVariable("isbn") final String isbn) {
		logger.info("Inside getBook()");
		Mono<Book> book = bookService.getBook(isbn);
		return book.switchIfEmpty(Mono.error(new com.online.bookstore.exception.BookNotFoundException(""))).map((b) -> {
			return ResponseEntity.ok()
					.body(BookReponseUtil.buildSuccessResponse("Succesfully retrieved requested Book details", b));
		});
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Create a book")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Book created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookServiceResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Failed to create book", content = @Content) })
	public Mono<ResponseEntity<BookServiceResponse>> createBook(@RequestBody @Valid Book book) {
		logger.info("Inside createBook()");
		Mono<Book> newBook = bookService.createBook(book);
		return newBook.map((b) -> {
			return ResponseEntity.ok().body(BookReponseUtil.buildSuccessResponse("Succesfully created new Book", b));
		});
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Update book by its ISBN")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Book details updated", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookServiceResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Failed to update book details", content = @Content) })
	public Mono<ResponseEntity<BookServiceResponse>> updateBook(@RequestBody @Valid Book book) {
		logger.info("Inside updateBook()");
		Mono<Book> updatedBook = bookService.updateBook(book);
		return updatedBook.map((b) -> {
			return ResponseEntity.ok()
					.body(BookReponseUtil.buildSuccessResponse("Succesfully updated Book details", b));
		});

	}

	@DeleteMapping("/{isbn}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete book by its ISBN")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Book deleted", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Failed to delete book", content = @Content) })
	public Mono<Void> deleteBook(@PathVariable("isbn") String isbn) {
		logger.info("Inside deleteBook()");
		return bookService.deleteBook(isbn);
	}

	@GetMapping("/checkout/{listOfISBN}/{promocode}")
	@ResponseStatus(code = HttpStatus.OK)
	@Operation(summary = "Check out and calculate price of books with discout applied")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Check out price calculated", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server erro while checking out", content = @Content) })
	public Mono<Double> checkOutBooksWithPromo(@PathVariable("listOfISBN") String[] listOfISBN,
			@PathVariable("promocode") String promocode) {
		logger.info("Inside checkOutBooksWithPromo()");
		return bookService.checkOutBooksWithPromo(listOfISBN, promocode);
	}
}
