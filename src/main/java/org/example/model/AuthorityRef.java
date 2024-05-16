package org.example.model;

import org.springframework.data.relational.core.mapping.Table;

@Table("authorities")
public class AuthorityRef {
    private long authority;

    public AuthorityRef(long authority) {
        this.authority = authority;
    }

    public long getAuthority() {
        return authority;
    }

    public void setAuthority(long authority) {
        this.authority = authority;
    }
}
