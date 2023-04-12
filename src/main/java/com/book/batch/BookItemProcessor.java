package com.book.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.book.model.Book;

public class BookItemProcessor implements ItemProcessor<Book, Book> {
	private static final Logger log = LoggerFactory.getLogger(BookItemProcessor.class);

	@Override
	public Book process(final Book bookDetails)  {
		final long isbnId = bookDetails.getIsbnId();
		final String bookName = bookDetails.getBookName();
		final String authorName = bookDetails.getAuthorName();
		final String publicationName = bookDetails.getPublicationName();
		final Integer yearPublished = bookDetails.getYearPublished();

		final Book book = new Book(isbnId, bookName, authorName, publicationName,
				yearPublished);

		log.info("Converting (" + bookDetails + ") into (" + book + ")");
		return book;
	}
}