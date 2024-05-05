package org.example.repositories;

import org.example.model.Book;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitleContaining(String keyword);


    @Query("SELECT DISTINCT b FROM Book b JOIN book_author ba ON b.id = ba.book_id JOIN author a ON ba.author_id = a.id WHERE a.name = :name")
    List<Book> findByAuthorsName(String name);


    //@Query("SELECT * FROM book b JOIN author a ON b.id = a.book_id WHERE a.name = ':name'")
//    @Query("SELECT b.* FROM book b JOIN book_author ba ON b.id = ba.book_id JOIN author a ON ba.author_id = a.id WHERE a.name = :name")
//    List<Book> findByAuthorsName(String name);

    @Query("SELECT b FROM Book b JOIN book_author ba ON b.id = ba.book_id JOIN author a ON ba.author_id = a.id WHERE b.title LIKE %:title% AND a.name = :name")
    List<Book> findByTitleContainingAndAuthorsName(String title, String name);

//    @Query("SELECT b FROM Book b WHERE b.title LIKE %':title'% AND b.authors.name = ':name'")
//    List<Book> findByTitleContainingAndAuthorsName(String title, String name);

    Optional<Book> findById(String id);

}
