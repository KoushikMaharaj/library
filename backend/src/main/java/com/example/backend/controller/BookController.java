package com.example.backend.controller;

import com.example.backend.pojos.Books;
import com.example.backend.repos.BookRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    private final BookRepo bookRepo;

    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<Books> books = bookRepo.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable(name = "id") Long id) {
        try {
            Books book = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Books book) {
        bookRepo.save(book);
        return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(name = "id") Long id) {
        bookRepo.deleteById(id);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }
}
