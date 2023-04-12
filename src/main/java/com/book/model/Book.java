package com.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

	private long isbnId;
	private String bookName;
	private String authorName;
	private String publicationName;
	private Integer yearPublished;
}