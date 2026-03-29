package com.delyassss.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Book> getbyAuthorAndTitleAndIsBorrow(Iterable<Author> authors, String title, Boolean isBorrowed, Pageable pg)
    {
        Page<Book> res = bookRepository.findByAuthorsIgnoreCaseAndTitleIgnoreCaseAndIsBorrowed(authors, title, isBorrowed, pg);
        if (res.isEmpty())
            throw new TaskNotFoundExeption();
        return res;
    }

    public Page<Book> getbyAuthors(Iterable<Author> authors, Pageable pg)
    {
        Page<Book> res = bookRepository.findByAuthorsIgnoreCase(authors, pg);
        if (res.isEmpty())
            throw new TaskNotFoundExeption();
        return res;
    }
    public Page<Book> getbyTitle(String Title, Pageable pg)
    {
        Page<Book> res = bookRepository.findByTitleIgnoreCase(Title, pg);
        if (res.isEmpty())
            throw new TaskNotFoundExeption();
        return res;
    }

    public Page<Book> getbyBorrowed(Boolean isBorrowed, Pageable pg)
    {
        Page<Book> res = bookRepository.findByIsBorrowed(isBorrowed, pg);
        if (res.isEmpty())
            throw new TaskNotFoundExeption();
        return res;
    }

    public Page<Book> getbyALL (Pageable pg)
    {
        Page<Book> res = bookRepository.findAll(pg);
        if (res.isEmpty())
            throw new TaskNotFoundExeption();
        return res;
    }

    BookBodyRequest ConvertToBookBodyRequest(Book book)
    {
        BookBodyRequest bookBodyRequest = new BookBodyRequest();
        bookBodyRequest.setTitle(book.getTitle());
        bookBodyRequest.setAuthors(book.getAuthors());
        bookBodyRequest.setIsBorrowed(book.getIsBorrowed());
        return bookBodyRequest;
    }

    public BookBodyRequest UpdateBOOK(BookBodyRequest book, Long id)
    {
        Book bk = getBookById(id);
        bk.setTitle(book.getTitle());
        bk.setIsBorrowed(book.getIsBorrowed());
        bk.setAuthors(book.getAuthors());
        bookRepository.save(bk);
        return ConvertToBookBodyRequest(bk);
    }





}
