package com.book.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.book.model.Book;

@Configuration
@EnableBatchProcessing
public class BookBatch {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	//To read all books' details from a JSON file
	@Bean
	public FlatFileItemReader<Book> reader() {
		FlatFileItemReader<Book> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("Books-Details.csv"));
		reader.setLineMapper(new DefaultLineMapper<>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("isbnId", "bookName", "authorName", "publicationName",
								"yearPublished");
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
					{
						setTargetType(Book.class);
					}
				});
			}
		});
		return reader;
	}

	//To write all books' details into MySQL database
	@Bean
	public JdbcBatchItemWriter<Book> writer() {
		JdbcBatchItemWriter<Book> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.setSql(
				"INSERT INTO test.BOOK_DETAILS (ISBN_ID, BOOK_NAME, AUTHOR_NAME, PUBLICATION_NAME, YEAR_PUBLISHED) VALUES (:isbnId, :bookName, :authorName, :publicationName, :yearPublished)");
		writer.setDataSource(dataSource);
		return writer;
	}

	@Bean
	public BookItemProcessor processor() {
		return new BookItemProcessor();
	}

	@Bean
	public Job importBookDetailsJob(BookJobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("importBookDetailsJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step()).end().build();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("step1").<Book, Book>chunk(10).reader(reader())
				.processor(processor()).writer(writer()).build();
	}
}