package com.delyassss.demo;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController
{
    private final BookService bookService;

    public BookController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book)
    {
        bookService.serviceCreate(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getbookbyId(@PathVariable Long id)
    {
        Book bk = bookService.getBookById(id);
        return ResponseEntity.ok(bk);
    }






}
