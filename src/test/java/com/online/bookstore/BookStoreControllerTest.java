package com.online.bookstore;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.online.bookstore.model.Book;
import com.online.bookstore.repository.BookRepository;
import com.online.bookstore.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
public class BookStoreControllerTest {

	private String isbn = "978-0-111-94786-4";
	private String listOfISBN = "978-0-111-94786-4,978-0-222-94786-4";
	private String promocode = "OFFERFSCI01";
	@MockBean
	BookRepository repository;

	@MockBean
	private BookService bookService;

	@Autowired
	WebTestClient client;

	@BeforeEach
	void resetRepo() {
		reset(repository);
		reset(bookService);
	}

	@Test
	void getAllBooksAsEmpty() throws Exception {

		when(repository.findAll()).thenReturn(Flux.empty());

		WebTestClient.ResponseSpec response = client.get().uri("/books").exchange();

		response.expectStatus().is2xxSuccessful().expectBodyList(Book.class).isEqualTo(List.of());
	}

	@Test
	void getAllBooks() throws Exception {

		Flux<Book> books = Flux.just(new Book(1, "978-0-321-94786-4", "Mobile App Development",
				"This book can help you in becoming master of mobile app development", "JACOB", "TECHNOLOGY", 250.5),
				new Book(2, "978-0-444-94786-4", "Mobile App Development23",
						"This book can help you in becoming master of mobile app development", "JACOB323", "TECHNOLOGY",
						150.5),
				new Book(3, "978-0-321-6567-4", "Mobile  Development",
						"This book can help you in becoming master of mobile app development", "34JACOB", "TECHNOLOGY",
						50.5));
		when(repository.findAll()).thenReturn(books);

		WebTestClient.ResponseSpec response = client.get().uri("/books").exchange();
		response.expectStatus().isOk();
	}

	@Test
	void getBook() throws Exception {
		when(bookService.getBook(isbn)).thenReturn(Mono.just(new Book(1, isbn, "Mobile App Development",
				"This book can help you in becoming master of mobile app development", "JACOB", "TECHNOLOGY", 250.5)));

		WebTestClient.ResponseSpec response = client.get().uri("/books/" + isbn).exchange();

		response.expectStatus().isOk().expectBody().jsonPath("$.content.id").isEqualTo(1).jsonPath("$.content.isbn")
				.isEqualTo(isbn);

	}

	@Test
	void getBookNotFound() throws Exception {
		when(bookService.getBook(isbn)).thenReturn(Mono.empty());

		WebTestClient.ResponseSpec response = client.get().uri("/books/" + isbn).exchange();

		response.expectStatus().isNotFound();
	}

	@Test
	void createBook() throws Exception {
		Book newBook = new Book(1, isbn, "Mobile App Development",
				"This book can help you in becoming master of mobile app development", "JACOB", "TECHNOLOGY", 250.5);

		when(bookService.createBook(newBook)).thenReturn(Mono.just(newBook));

		Book newBook1 = new Book(1, isbn, "Mobile App Development",
				"This book can help you in becoming master of mobile app development", "JACOB", "TECHNOLOGY", 250.5);

		WebTestClient.ResponseSpec response = client.post().uri("/books").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(newBook1)).exchange();

		response.expectStatus().isOk().expectBody().jsonPath("$.content.id").isEqualTo(1).jsonPath("$.content.isbn")
				.isEqualTo(isbn);

	}

	@Test
	void updateBook() throws Exception {
		Book newBook = new Book(1, isbn, "Mobile App Development",
				"This book can help you in becoming master of mobile app development", "JACOB", "TECHNOLOGY", 250.5);

		when(bookService.updateBook(newBook)).thenReturn(Mono.just(newBook));

		Book newBook1 = new Book(1, isbn, "Mobile App Development",
				"This book can help you in becoming master of mobile app development", "JACOB", "TECHNOLOGY", 250.5);

		WebTestClient.ResponseSpec response = client.put().uri("/books").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(newBook1)).exchange();

		response.expectStatus().isOk().expectBody().jsonPath("$.content.id").isEqualTo(1).jsonPath("$.content.isbn")
				.isEqualTo(isbn);

	}

	@Test
	void checkout() throws Exception {

		when(bookService.checkOutBooksWithPromo(listOfISBN.split(","), listOfISBN))
				.thenReturn(Mono.just(new Double("475.5")));

		WebTestClient.ResponseSpec response = client.get().uri("/books/checkout/" + listOfISBN + "/" + promocode)
				.exchange();

		response.expectStatus().isOk();

	}
}
