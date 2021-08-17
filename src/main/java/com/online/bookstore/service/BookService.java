package com.online.bookstore.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.bookstore.config.Promos;
import com.online.bookstore.config.PromoCodesConfig;
import com.online.bookstore.model.Book;
import com.online.bookstore.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional
public class BookService {
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private PromoCodesConfig promoCodesConfig;

	@Autowired
	private BookRepository repository;

	public Mono<Book> createBook(Book book) {
		logger.info("Inside createBook()");
		return repository.save(book);
	}

	public Flux<Book> getAllBooks() {
		logger.info("Inside getAllBooks111()");
		return repository.findAll();
	}

	public Mono<Book> getBook(String isbn) {
		logger.info("Inside getBook()");
		return repository.findByIsbn(isbn);
	}

	public Mono<Book> updateBook(Book book) {
		logger.info("Inside updateBook()");
		return repository.save(book);
	}

	public Mono<Void> deleteBook(String isbn) {
		logger.info("Inside deleteBook()");
		return repository.deleteByIsbn(isbn);
	}

	public Mono<Double> checkOutBooksWithPromo(String[] listOfISBN, String promocode) {
		logger.info("Inside checkOutBooksWithPromo()");
		logger.info("Input Promo Code :: "+promocode);
		logger.info("Available Promo Codes :: "+promoCodesConfig.getPromos().size());
		Promos promoDetails = promoCodesConfig.getPromos().stream().filter(p -> p.getCode().equalsIgnoreCase(promocode)).findFirst().get();
		logger.info("Promo Code :: "+promoDetails.getCode() + " Discount :: " +promoDetails.getDiscount() );
		Flux<Book> books = repository.findByIsbns(Arrays.asList(listOfISBN));
		return books.map(x -> x.getPrice()).collect(Collectors.summingDouble(Double::doubleValue));
	}

}
