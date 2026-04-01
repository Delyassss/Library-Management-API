package com.delyassss.demo;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest
{
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void BookServiceTest()
    {
        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(1L);
        author.setFullName("Delyassss");
        authors.add(author);

        Book bk = new Book();
        Long bookId = 1L;
        bk.setId(bookId);
        bk.setTitle("Book Title");
        bk.setIsBorrowed(false);
        bk.setAuthors(authors);

        Book bk2 = new Book();
        //bk2.setId(bookId + 1L);
        bk2.setTitle("Book Title2");
        bk2.setIsBorrowed(false);
        bk2.setAuthors(authors);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bk));

        when(bookRepository.save(bk2)).thenReturn(bk2);

        Book book = bookService.getBookById(bookId);
        BookDTO book2 = bookService.serviceCreate(bookService.ConvertToBookBodyRequest(bk2));

        assertNotNull(book);
        assertEquals(bk2.getTitle(), book2.getTitle());
        assertEquals("Book Title" , book.getTitle());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(bk2);

    }


}
