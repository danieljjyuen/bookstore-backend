package org.example.repositories;

import org.example.model.Book;
import org.example.model.BookOutput;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("SELECT b.*, GROUP_CONCAT(DISTINCT a.name) AS authors, " +
            "GROUP_CONCAT(DISTINCT c.name) AS categories " +
            "FROM book b " +
            "JOIN book_author ba ON b.pk = ba.book " +
            "JOIN author a ON ba.author = a.id " +
            "JOIN book_category bc ON b.pk = bc.book " +
            "JOIN category c ON bc.category = c.id " +
            "WHERE b.title " +
            "LIKE concat('%',:keyword,'%') " +
            "group by b.pk")
    Set<BookOutput> findByTitleContaining(String keyword);

    //@Query("SELECT distinct b.* FROM book b JOIN book_author ba ON b.pk = ba.book JOIN author a ON ba.author = a.id WHERE a.name LIKE concat('%',:name,'%') ")
    @Query("SELECT b.*, GROUP_CONCAT(DISTINCT a.name) AS authors, " +
            "GROUP_CONCAT(DISTINCT c.name) AS categories " +
            "FROM book b " +
            "JOIN book_author ba ON b.pk = ba.book " +
            "JOIN author a ON ba.author = a.id " +
            "JOIN book_category bc ON b.pk = bc.book " +
            "JOIN category c ON bc.category = c.id " +
            "WHERE a.name " +
            "LIKE concat('%',:name,'%')" +
            "group by b.pk")
    Set<BookOutput> findByAuthorsName(String name);

    //@Query("SELECT distinct b.*  FROM book b JOIN book_author ba ON b.pk = ba.book JOIN author a ON ba.author = a.id WHERE b.title LIKE concat('%',:title,'%') AND a.name LIKE concat('%',:name,'%') ")

    @Query("SELECT b.*, GROUP_CONCAT(DISTINCT a.name) AS authors, " +
            "GROUP_CONCAT(DISTINCT c.name) AS categories " +
            "FROM book b " +
            "JOIN book_author ba ON b.pk = ba.book " +
            "JOIN author a ON ba.author = a.id " +
            "JOIN book_category bc ON b.pk = bc.book " +
            "JOIN category c ON bc.category = c.id " +
            "WHERE b.title LIKE concat('%',:title,'%') " +
            "AND a.name LIKE concat('%',:name,'%') " +
            "group by b.pk")
    Set<BookOutput> findByTitleContainingAndAuthorsName(String title, String name);

    @Query("SELECT distinct * FROM book WHERE id =:id")
    Optional<Book> findById(String id);

    Optional<Book> findByPk(long id);

    //@Query("select distinct b.*  from book b join customer_book cb on b.pk = cb.book where cb.customer = :id ")
    @Query("SELECT b.*, GROUP_CONCAT(DISTINCT a.name) AS authors, " +
            "GROUP_CONCAT(DISTINCT c.name) AS categories " +
            "FROM book b " +
            "JOIN book_author ba ON b.pk = ba.book " +
            "JOIN author a ON ba.author = a.id " +
            "JOIN book_category bc ON b.pk = bc.book " +
            "JOIN category c ON bc.category = c.id " +
            "JOIN customer_book cb ON b.pk = cb.book " +
            "where cb.customer = :id " +
            "group by b.pk")
    Set<BookOutput> findByCustomerId(Long id);

}
