package org.example.repositories;

import org.example.model.Book;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Set<Book> findByTitleContaining(String keyword);

    @Query("SELECT b.* FROM book b JOIN book_author ba ON b.pk = ba.book JOIN author a ON ba.author = a.id WHERE a.name LIKE concat('%',:name,'%')")
    Set<Book> findByAuthorsName(String name);

    @Query("SELECT b.* FROM book b JOIN book_author ba ON b.pk = ba.book JOIN author a ON ba.author = a.id WHERE b.title LIKE concat('%',:title,'%') AND a.name LIKE concat('%',:name,'%')")
    Set<Book> findByTitleContainingAndAuthorsName(String title, String name);

    Optional<Book> findById(String id);
    Optional<Book> findByPk(long id);

    //still need to be tested
    @Query("select b.* from book b join customer_book cb on b.pk = cb.book where cb.customer = :id")
    Set<Book> findByCustomerId(Long id);

}
