package com.delyassss.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class BookService
{
    public AuthorRepository authorRepository;
    public BookRepository bookRepository;
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository)
    {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book serviceCreate(Book book)
    {
        Book book1 = bookRepository.save(book);
        return book1;
    }

    public Book getBookById(Long id)
    {
        return bookRepository.findById(id).orElseThrow(()->new TaskNotFoundExeption(id));
    }

}
