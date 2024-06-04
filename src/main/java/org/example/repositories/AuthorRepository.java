package org.example.repositories;

import org.example.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByName(String name);

    Optional<Author> findById(Long id);

}
