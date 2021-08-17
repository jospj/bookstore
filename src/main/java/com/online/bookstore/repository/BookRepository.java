package com.online.bookstore.repository;

import java.util.List;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.online.bookstore.model.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Integer>{
	Mono<Book> findByIsbn(String isbn);
	Mono<Void> deleteByIsbn(String isbn);
    @Query("SELECT * FROM Book b WHERE b.isbn IN (:isbns)")  
    Flux<Book> findByIsbns(@Param("isbns")List<String> isbns);

}
