package org.example.repositories;

import org.example.model.Authority;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    @Query("select distinct * from authority where name = :name")
    Optional<Authority> findByName(String name);
    @Query("select distinct * from authority where id = :id")
    Optional<Authority> findById(Long id);
}
