package com.delyassss.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface BookRepository extends JpaRepository<Book, Long>
{
    @Query("SELECT DISTINCT b FROM Book b"
            + " LEFT JOIN b.authors a WHERE"
            + "(:title IS NULL OR b.title = :title) AND "
            + "(:isBorrowed IS NULL OR b.isBorrowed = :isBorrowed) AND "
            + "(:authors IS NULL OR a.fullName IN :authors)")
    Page<Book>  BooksDynamically(@Param("authors") List<String> authors, @Param("title") String title, @Param("isBorrowed")Boolean isBorrowed, Pageable pg);
    Page<Book> findByAuthorsIgnoreCaseAndTitleIgnoreCaseAndIsBorrowed(Iterable<String> authors, String title, Boolean isBorrowed, Pageable pg);
    Page<Book> findByAuthorsIgnoreCase(Iterable<String> authors, Pageable pg);
    Page<Book> findByTitleIgnoreCase(String Title, Pageable pg);
    Page <Book> findByIsBorrowed(Boolean isBorrowed, Pageable pg);
    @Query(value = "SELECT * FROM Book WHERE deleted = :deleted", nativeQuery = true) // with nativeQuery we talking to the database not to hibernate
    Page<Book>  findAllByDeleted(@Param("deleted") Boolean deleted , Pageable pg);

    @Query(value = "SELECT * FROM Book WHERE id = :book_id AND deleted = true", nativeQuery = true)
    Book findDeletedById(@Param("book_id") Long book_id);
}
