package com.example.backend.repos;

import com.example.backend.pojos.Books;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepoTest {
    @Autowired
    private BookRepo bookRepo;
    static Books book;
    static List<Books> books;

    @BeforeAll
    protected static void setUp() {
        book = Books.builder().author("Koushik").name("Java").build();
        books = List.of(
                Books.builder().author("Koushik").name("Java").build(),
                Books.builder().author("Koushik").name("Docker").build(),
                Books.builder().author("Koushik").name("K8s").build()
        );
    }

    @AfterAll
    protected static void tearDown() {
        book = new Books();
        books = List.of();
    }

    @Test
    protected void save() {
        Books save = bookRepo.save(book);
        assertNotNull(save);
        assertEquals(save.getId(), book.getId());
    }

}