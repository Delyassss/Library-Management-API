package com.delyassss.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface BookRepository extends JpaRepository<Book, Long>
{
    Page<Book> findByAuthorsIgnoreCaseAndTitleIgnoreCaseAndIsBorrowed(Iterable<Author> authors, String title, Boolean isBorrowed, Pageable pg);
    Page<Book> findByAuthorsIgnoreCase(Iterable<Author> authors, Pageable pg);
    Page<Book> findByTitleIgnoreCase(String Title, Pageable pg);
    Page <Book> findByIsBorrowed(Boolean isBorrowed, Pageable pg);
}
