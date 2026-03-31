package com.delyassss.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public BookDTO serviceCreate(BookDTO book)
    {
        Book book1 = new  Book();
        book1.setTitle(book.getTitle());
        book1.setAuthors(book.getAuthors());
        book1.setIsBorrowed(book.getIsBorrowed());
        bookRepository.save(book1);

        return ConvertToBookBodyRequest(book1);
    }

    public Page<Book>   getDynamically(List<String> authors, String title , Boolean isBorrowed , Pageable pageable)
    {
        Page<Book> result = bookRepository.BooksDynamically(authors, title, isBorrowed, pageable);
        if (result == null || result.isEmpty())
            throw new TaskNotFoundExeption();
        return result;
    }

    public Book getBookById(Long id)
    {
        return bookRepository.findById(id).orElseThrow(()->new TaskNotFoundExeption(id));
    }

    public Page<Book> getbyAuthorAndTitleAndIsBorrow(Iterable<String> authors, String title, Boolean isBorrowed, Pageable pg)
    {
        Page<Book> res = bookRepository.findByAuthorsIgnoreCaseAndTitleIgnoreCaseAndIsBorrowed(authors, title, isBorrowed, pg);
        if (res.isEmpty())
            throw new TaskNotFoundExeption();
        return res;
    }

    public Page<Book> getbyAuthors(Iterable<String> authors, Pageable pg)
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

    BookDTO ConvertToBookBodyRequest(Book book)
    {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthors(book.getAuthors());
        bookDTO.setIsBorrowed(book.getIsBorrowed());
        return bookDTO;
    }

    Page<BookDTO>  BookChunkTODTO(Page<Book> page)
    {
        Page<BookDTO> pg;
        pg = page.map(book -> ConvertToBookBodyRequest(book));
        return pg;
    }

    public BookDTO UpdateBOOK(BookDTO book, Long id)
    {
        Book bk = getBookById(id);
        bk.setTitle(book.getTitle());
        bk.setIsBorrowed(book.getIsBorrowed());
        bk.setAuthors(book.getAuthors());
//        modelMapper.map(book, bk);
        bookRepository.save(bk);
        return ConvertToBookBodyRequest(bk);
    }

    public void Deletebook(Long id)
    {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }





}
