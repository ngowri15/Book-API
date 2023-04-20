package com.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.book.model.Book;
import com.book.repository.BookRepository;

@RestController
public class BookController {

	@Autowired
	BookRepository bookRepository;

	//To create book details
	@PostMapping("/CreateBook")
	public ResponseEntity<String> createBook(@RequestBody Book book) {
		try {
			bookRepository.save(new Book(book.getIsbnId(), book.getBookName(),
					book.getAuthorName(), book.getPublicationName(), book.getYearPublished()));
			return new ResponseEntity<>("Book details created successfully.", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//To read book details
	@GetMapping("/ReadBook/{isbn_id}")
	public ResponseEntity<Book> readBookByID(@PathVariable("isbn_id") long isbn_id) {
		Book book = bookRepository.findById(isbn_id);
		if (book != null) {
			return new ResponseEntity<>(book, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//To update book details
	@PutMapping("/UpdateBook/{isbn_id}")
	public ResponseEntity<String> updateBookByID(@PathVariable("isbn_id") long isbn_id,
													@RequestBody Book book) {
		Book uBook=new Book();
		if (book != null) {
			uBook.setIsbnId(isbn_id);
			uBook.setBookName(book.getBookName());
			uBook.setAuthorName(book.getAuthorName());
			uBook.setPublicationName(book.getPublicationName());
			uBook.setYearPublished(book.getYearPublished());
			bookRepository.update(uBook);
			return new ResponseEntity<>("Book details updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Enter book details to update", HttpStatus.NOT_FOUND);
		}
	}

	//To delete book details
	@DeleteMapping("/DeleteBook/{isbn_id}")
	public ResponseEntity<String> deleteBookByID(@PathVariable("isbn_id") long isbn_id) {
		try {
			int result = bookRepository.deleteById(isbn_id);
			if (result == 0) {
				return new ResponseEntity<>("Cannot find book details with id=" + isbn_id, HttpStatus.OK);
			}
			return new ResponseEntity<>("Book details deleted successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("No such book available. Cannot delete book details.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//To read all the books' details
	@GetMapping("/ReadAllBooks")
	public ResponseEntity<List<Book>> readAllBooks() {
		try {
			List<Book> bookDetails = new ArrayList<>(bookRepository.findAll());
			if (bookDetails.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(bookDetails, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//To delete all the books' details
	@DeleteMapping("/DeleteAllBooks")
	public ResponseEntity<String> deleteAllBooks() {
		try {
			int numRows = bookRepository.deleteAll();
			return new ResponseEntity<>("Deleted " + numRows + " books' details successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete book details.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}