package com.delyassss.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService
{
    @Autowired
    public AuthorRepository authorRepository;
    @Autowired
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
        List<String> incomingAuthor = book.getAuthors(); // authors request as List<Sting>
        for(String names : incomingAuthor)
        {
            Author p = new Author();
            p.setFullName(names);
            p.setBook(book1);
            book1.getAuthors().add(p);
        }
        book1.setIsBorrowed(book.getIsBorrowed());
        bookRepository.save(book1);

        return ConvertToBookBodyRequest(book1);
    }

    public Page<BookDTO>   getDynamically(List<String> authors, String title , Boolean isBorrowed , Pageable pageable)
    {
        Page<Book> result = bookRepository.BooksDynamically(authors, title, isBorrowed, pageable);
        if (result == null || result.isEmpty())
            throw new TaskNotFoundExeption();
        return BookChunkTODTO(result);
    }

    public Book getBookById(Long id)
    {
        return bookRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundExeption(id));
    }

    public Page<BookDTO> findbydeleted(Boolean deleted , Pageable pg)
    {
        Page<Book> books = bookRepository.findAllByDeleted(deleted , pg);
        if (books == null || books.isEmpty())
            throw new TaskNotFoundExeption();
        return BookChunkTODTO(books);
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
        List<Author> authors = book.getAuthors();
        for (Author author : authors)
        {
            bookDTO.getAuthors().add(author.getFullName());
        }
//        bookDTO.setAuthors(book.getAuthors());
        bookDTO.setIsBorrowed(book.getIsBorrowed());
        bookDTO.setDeleted(book.getDeleted());
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
        Book bk;

        bk = bookRepository.findDeletedById(id);
        if (bk == null)
            bk = getBookById(id);

        bk.setTitle(book.getTitle());
        bk.setIsBorrowed(book.getIsBorrowed());
        bk.getAuthors().clear();
        List<String> newAuthors = book.getAuthors(); // so we have Book object in author class so we can set each one to the book object
        for (String names : newAuthors)
        {
            Author  p = new Author();
            p.setFullName(names);
            p.setBook(bk);
            bk.getAuthors().add(p);
        }

        bk.setDeleted(book.getDeleted());
//        modelMapper.map(book, bk);
        bookRepository.save(bk);
        return ConvertToBookBodyRequest(bk);
    }

    public int Deletebook(Long id)
    {
        Book book = getBookById(id);
        if (book.getIsBorrowed() == true)
            return  0;
        bookRepository.delete(book);
        return 1;
    }





}
