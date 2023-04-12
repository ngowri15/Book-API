package com.book.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.book.model.Book;

@Component
public class BookJobCompletionNotificationListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(BookJobCompletionNotificationListener.class);
	private final JdbcTemplate jdbcTemplate;

	public BookJobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED !! It's time to verify the results!!");

			List<Book> results = jdbcTemplate.query(
					"SELECT ISBN_ID, BOOK_NAME, AUTHOR_NAME, PUBLICATION_NAME, YEAR_PUBLISHED FROM test.BOOK_DETAILS",
					(rs, row) -> new Book(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getInt(5)));

			for (Book book : results) {
				log.info("Found <" + book + "> in the database.");
			}
		}
	}

}