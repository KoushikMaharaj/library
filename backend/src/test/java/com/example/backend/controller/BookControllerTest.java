package com.example.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.backend.pojos.Books;
import com.example.backend.repos.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepo repo;

    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void getAllBooks() throws Exception {
        String uri = "/book";
        List<Books> books = List.of(new Books(1L, "John", "Java"));
        when(repo.findAll()).thenReturn(books);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        List<Books> productlist = mapFromJson(content, ArrayList.class);
        assertFalse(productlist.isEmpty());
    }

    @Test
    void getBook() throws Exception {
        String uri = "/book/{id}";
        Books book = Books.builder().author("koushik").name("Java").build();
        when(repo.findById(1L)).thenReturn(Optional.ofNullable(book));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, 1L)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Books returnedBook = mapFromJson(content, Books.class);
        assertNotNull(returnedBook);
        assertEquals(returnedBook.getName(), book.getName());
    }

    @Test
    void addBook() throws Exception {
        String uri = "/book";
        Books book = Books.builder().author("koushik").name("Java").build();
        String msg = "Book added successfully";
        when(repo.save(book)).thenReturn(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(book))
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, msg);
    }

    @Test
    void deleteBook() throws Exception {
        String uri = "/book/{id}";
        String msg = "Book deleted successfully";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, 1L)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, msg);

    }
}