package com.delyassss.demo;

import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/books")
            public ResponseEntity<Page<Book>> getBooks(@RequestParam(required = false) Iterable<Author> authors,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) Boolean isBorrowed,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "5") Integer size)
    {
        Pageable pg = PageRequest.of(page, size);
        Page<Book> result ;
        if (authors != null && title != null && isBorrowed != null)
            result = bookService.getbyAuthorAndTitleAndIsBorrow(authors, title, isBorrowed, pg);
        else if (title != null)
            result = bookService.getbyTitle(title, pg);
        else if (isBorrowed != null)
            result = bookService.getbyBorrowed(isBorrowed, pg);
        else if (authors != null)
            result = bookService.getbyAuthors(authors, pg);
        else
            result = bookService.getbyALL(pg);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/books/{id}")
            public ResponseEntity<Book> getbookbyId(@PathVariable Long id)
    {
        Book bk = bookService.getBookById(id);
        return ResponseEntity.ok(bk);
    }

    @PutMapping("/books/{id}")
            public ResponseEntity<BookBodyRequest> UpdateBook(@Valid @RequestBody BookBodyRequest book, @PathVariable Long id)
    {
        BookBodyRequest bookRequest = bookService.UpdateBOOK(book,id);
        return ResponseEntity.status(HttpStatus.OK).body(bookRequest);
    }






}
