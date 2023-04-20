package com.book.controller;

import com.book.model.Book;
import com.book.repository.BookRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(BookController.class )
class BookControllerTest {

    // To perform request and verify response
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookRepository bookRepository;
    @Test
    void createBook() throws Exception {{

       // Arrange - precondition or setup
        Book book=Book.builder()
                .isbnId(101L)
                .bookName("Tale of Peter Rabbit")
                .authorName("Beatrix Potter")
                .publicationName("Warne")
                .yearPublished(2022)
                .build();
        Mockito.when(bookRepository.save(book)).thenReturn(1);

        // Act - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/CreateBook")
                .content(asJsonString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert - verify the output
        response.andExpect(status().isCreated())
                .andDo(print());
    }
    }

    @Test
    void readBookByID() throws Exception {

        // Arrange - precondition or setup
        long isbnId=101L;
        Book book=Book.builder()
                .isbnId(101L)
                .bookName("Tale of Peter Rabbit")
                .authorName("Beatrix Potter")
                .publicationName("Warne")
                .yearPublished(2002)
                .build();
        Mockito.when(bookRepository.findById(book.getIsbnId())).thenReturn(book);

        // Act - action or behaviour to be tested
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/ReadBook/{isbn_id}",isbnId));

        // Assert - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName", Matchers.is(book.getBookName())))
                .andExpect(jsonPath("$.authorName", Matchers.is(book.getAuthorName())))
                .andExpect(jsonPath("$.publicationName", Matchers.is(book.getPublicationName())))
                .andExpect(jsonPath("$.yearPublished", Matchers.is(book.getYearPublished())))
                .andDo(print());
    }

    @Test
    void updateBookByID() throws Exception {

        // Arrange - precondition or setup
        long isbnId=101L;
        int expectedRowsAffected=1;
        Book book=Book.builder()
                .isbnId(101L)
                .bookName("Tale of Peter Rabbit")
                .authorName("Beatrix Potter")
                .publicationName("Warne")
                .yearPublished(2002)
                .build();
        Mockito.when(bookRepository.update(book)).thenReturn(expectedRowsAffected);

        // Act - action or behaviour to be tested
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/UpdateBook/{isbn_id}",isbnId)
                .content(asJsonString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteBookByID() throws Exception {

        // Arrange - precondition or setup
        long isbnId=101L;
        Book book=Book.builder()
                .isbnId(101L)
                .bookName("Tale of Peter Rabbit")
                .authorName("Beatrix Potter")
                .publicationName("Warne")
                .yearPublished(2002)
                .build();
        Mockito.when(bookRepository.deleteById(book.getIsbnId())).thenReturn(1);

        // Act - action or behaviour to be tested
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/DeleteBook/{isbn_id}",isbnId));

        // Assert - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void readAllBooks() throws Exception {

        // Arrange - precondition or setup
        List<Book> bookList =new ArrayList<>();
        bookList.add(new Book(101,"Tale of Cotton Tail","Beatrix Potter","Warne",2012));
        bookList.add(new Book(102,"Tale of Nutkin","Beatrix Potter","Warne",2022));
        bookList.add(Book.builder().isbnId(103).bookName("Tale of Jemima Puddle-Duck").authorName("Beatrix Potter").publicationName("Warne").yearPublished(2022).build());
        bookList.add(Book.builder().isbnId(104).bookName("Tale of Tiggy-Winkle").authorName("Beatrix Potter").publicationName("Warne").yearPublished(2022).build());
        Mockito.when(bookRepository.findAll()).thenReturn(bookList);

        // Act - action or behaviour to be tested
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/ReadAllBooks"));

        // Assert - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(bookList.size())))
                .andDo(print());
    }

    @Test
    void deleteAllBooks() throws Exception {

        // Arrange - precondition or setup
        Mockito.when(bookRepository.deleteAll()).thenReturn(4);

        // Act - action or behaviour to be tested
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/DeleteAllBooks"));

        // Assert - verify the output
        response.andExpect(status().isOk()).andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }}
}