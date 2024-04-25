package org.example.dto;

import java.util.List;

public class BookSearchResponseDTO {
    //books/magazine/etc
    private String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    private int totalItems;
    private List<ItemDTO> items;

    @Override
    public String toString() {
        return "kind: " + kind +
               "\n totalItems: " + totalItems +
               "\n items: " + items.toString();
    }
}
