package org.example.model;

import org.springframework.data.relational.core.mapping.Table;

@Table("book_category")
public class CategoryRef {
    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public CategoryRef(long category) {
        this.category = category;
    }

    private long category;
}
