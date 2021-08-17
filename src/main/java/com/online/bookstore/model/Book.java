package com.online.bookstore.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("Book")
public class Book{
    @Id
    private Integer id;
    @NotNull
    private String isbn;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String author;
    @NotNull
    private String book_type;
    @NotNull
    @Min(value = 0, message = "Price should be positive value.")
    private double price;
}
