package org.example.model;

import org.springframework.data.annotation.Id;

public class Authority {

    @Id
    private long id;

    public Authority(String name) {
        this.name = name;
    }
    public Authority(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Authority() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String name;

}
