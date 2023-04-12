package com.book.repository;

import java.util.List;

import com.book.model.Book;

public interface BookRepository {

	int save(Book book);

	int update(Book book);

	Book findById(Long id);

	int deleteById(Long id);

	List<Book> findAll();

	int deleteAll();

}