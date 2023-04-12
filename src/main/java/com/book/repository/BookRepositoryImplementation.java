package com.book.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.book.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImplementation implements BookRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//To create a book's details
	@Override
	public int save(Book bookDetails) {

		return jdbcTemplate.update(
				"INSERT INTO BOOK_DETAILS (ISBN_ID, BOOK_NAME, AUTHOR_NAME, PUBLICATION_NAME, YEAR_PUBLISHED) VALUES(?,?,?,?,?)",
				bookDetails.getIsbnId(), bookDetails.getBookName(), bookDetails.getAuthorName(), bookDetails.getPublicationName(), bookDetails.getYearPublished());
	}

	//To read a book's details
	@Override
	public Book findById(Long isbn_id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM BOOK_DETAILS WHERE ISBN_ID=?",
					BeanPropertyRowMapper.newInstance(Book.class), isbn_id);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	//To update a book's details
	@Override
	public int update(Book bookDetails) {

		//jdbcTemplate.update() method returns an integer value indicating number of records
		//updated in the database when the query is executed
		int result=jdbcTemplate.update(
				"UPDATE BOOK_DETAILS SET BOOK_NAME=?, AUTHOR_NAME=?, PUBLICATION_NAME=?, YEAR_PUBLISHED=? WHERE ISBN_ID=?",
				bookDetails.getBookName(), bookDetails.getAuthorName(), bookDetails.getPublicationName(),
				bookDetails.getYearPublished(), bookDetails.getIsbnId());
		System.out.println(result);

		return result;

	}

	//To delete a book's details
	@Override
	public int deleteById(Long isbn_id) {

		return jdbcTemplate.update("DELETE FROM BOOK_DETAILS WHERE ISBN_ID=?", isbn_id);
	}

	//To read all the books' details
	@Override
	public List<Book> findAll() {

		return jdbcTemplate.query("SELECT * from BOOK_DETAILS", BeanPropertyRowMapper.newInstance(Book.class));
	}

	//To delete all the books' details
	@Override
	public int deleteAll() {

		return jdbcTemplate.update("DELETE from BOOK_DETAILS");
	}

}