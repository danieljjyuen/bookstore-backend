package org.example.security;

import org.example.model.Authority;
import org.example.model.Customer;
import org.example.services.AuthorityService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SecurityCustomer implements UserDetails {
    private final Customer customer;
    private final AuthorityService authorityService;


    public SecurityCustomer(Customer customer, AuthorityService authorityService) {
        this.customer = customer;
        this.authorityService = authorityService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = customer.getAuthorities();
        List<GrantedAuthority> authorityList = new ArrayList<>();

        for(var authority : authorities) {
            Long id = authority.getAuthority();
            String authorityName = authorityService.getAuthorityName(id);
            authorityList.add(new SimpleGrantedAuthority(authorityName));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
