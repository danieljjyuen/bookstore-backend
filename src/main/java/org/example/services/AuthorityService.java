package org.example.services;

import org.example.model.Authority;
import org.example.repositories.AuthorRepository;
import org.example.repositories.AuthorityRepository;

import java.util.Optional;

public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }
    public String getAuthorityName(Long id) {
        Optional<Authority> authorityOptional = authorityRepository.findById(id);
        try {
            if(authorityOptional.isPresent()){
                Authority authority = authorityOptional.get();
                return authority.getName();
            }else {
                throw new RuntimeException("authority does not exist");
            }

        }catch(Exception e) {
            throw new RuntimeException("authority does not exist");
        }
    }
}
