package com.delyassss.demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController
{
    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @PostMapping("/books")
            public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO book)
    {
        BookDTO bk =  bookService.serviceCreate(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(bk);
    }

    @GetMapping("/books")
            public ResponseEntity<Page<BookDTO>> getBooks(@RequestParam(required = false) List<String> authors,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) Boolean isBorrowed,
                                         @RequestParam(required = false) Boolean deleted,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "5") Integer size)
    {
        // If the list is empty, make it null so the @Query 'IS NULL' trick works!
        if (authors != null && !authors.iterator().hasNext())
        {
            authors = null;
        }
        Pageable pg = PageRequest.of(page, size);
        Page<BookDTO> result ;
//        if (authors != null && title != null && isBorrowed != null)
//            result = bookService.getbyAuthorAndTitleAndIsBorrow(authors, title, isBorrowed, pg);
//        else if (title != null)
//            result = bookService.getbyTitle(title, pg);
//        else if (isBorrowed != null)
//            result = bookService.getbyBorrowed(isBorrowed, pg);
//        else if (authors != null)
//            result = bookService.getbyAuthors(authors, pg);
//        else
//            result = bookService.getbyALL(pg);
        if (deleted != null)
            result = bookService.findbydeleted(deleted , pg);
        else
            result = bookService.getDynamically(authors, title, isBorrowed, pg);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/books/{id}")
            public ResponseEntity<BookDTO> getbookbyId(@PathVariable Long id)
    {
        Book bk = bookService.getBookById(id);
        return ResponseEntity.ok(bookService.ConvertToBookBodyRequest(bk));
    }

    @PutMapping("/books/{id}")
            public ResponseEntity<BookDTO> UpdateBook(@Valid @RequestBody BookDTO book, @PathVariable Long id)
    {
        BookDTO bookRequest = bookService.UpdateBOOK(book,id);
        return ResponseEntity.status(HttpStatus.OK).body(bookRequest);
    }

    @DeleteMapping("books/{id}")
        public ResponseEntity<Void> DeleteBook(@PathVariable Long id)
    {
        if (bookService.Deletebook(id) == 0)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        return ResponseEntity.status(HttpStatus.OK).build();
    }







}
