package org.example.dto;

import java.util.List;

public class VolumeInfoDTO {
    private String title;
    private List<String> authors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ImageLinksDTO getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinksDTO imageLinks) {
        this.imageLinks = imageLinks;
    }

    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private List<String> categories;
    private int averageRating;
    private int ratingsCount;
    private String language;
    private ImageLinksDTO imageLinks;

    @Override
    public String toString() {
        return "\n title : " + title +
                "\n author: " + authors.toString() +
                "\n publisher: " + publisher +
                "\n publishedDate: " + publishedDate +
                "\n description: " + description +
                "\n pageCount: " + pageCount;
    }
}
