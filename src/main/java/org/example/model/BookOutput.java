package org.example.model;

import java.math.BigDecimal;

public class BookOutput {
    private long pk;

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherDate() {
        return publisherDate;
    }

    public void setPublisherDate(String publisherDate) {
        this.publisherDate = publisherDate;
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

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public BigDecimal getListPriceAmount() {
        return listPriceAmount;
    }

    public void setListPriceAmount(BigDecimal listPriceAmount) {
        this.listPriceAmount = listPriceAmount;
    }

    public String getListPriceCurrency() {
        return listPriceCurrency;
    }

    public void setListPriceCurrency(String listPriceCurrency) {
        this.listPriceCurrency = listPriceCurrency;
    }

    public BigDecimal getRetailPriceAmount() {
        return retailPriceAmount;
    }

    public void setRetailPriceAmount(BigDecimal retailPriceAmount) {
        this.retailPriceAmount = retailPriceAmount;
    }

    public String getRetailPriceCurrency() {
        return retailPriceCurrency;
    }

    public void setRetailPriceCurrency(String retailPriceCurrency) {
        this.retailPriceCurrency = retailPriceCurrency;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    private String id;
    private String title;
    private String subtitle;

    public BookOutput() {
    }

    public BookOutput(long pk, String id, String title, String subtitle, String publisher, String publisherDate, String description, int pageCount, String smallThumbnail, String thumbnail, BigDecimal listPriceAmount, String listPriceCurrency, BigDecimal retailPriceAmount, String retailPriceCurrency, String buyLink, int averageRating, int ratingsCount, String language, String kind, String authors, String categories) {
        this.pk = pk;
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.publisherDate = publisherDate;
        this.description = description;
        this.pageCount = pageCount;
        this.smallThumbnail = smallThumbnail;
        this.thumbnail = thumbnail;
        this.listPriceAmount = listPriceAmount;
        this.listPriceCurrency = listPriceCurrency;
        this.retailPriceAmount = retailPriceAmount;
        this.retailPriceCurrency = retailPriceCurrency;
        this.buyLink = buyLink;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.language = language;
        this.kind = kind;
        this.authors = authors;
        this.categories = categories;
    }

    private String publisher;
    private String publisherDate;
    private String description;
    private int pageCount;
    private String smallThumbnail;
    private String thumbnail;
    private BigDecimal listPriceAmount;
    private String listPriceCurrency;
    private BigDecimal retailPriceAmount;
    private String retailPriceCurrency;
    private String buyLink;
    private int averageRating;
    private int ratingsCount;
    private String language;
    private String kind;
    private String authors;
    private String categories;

}
