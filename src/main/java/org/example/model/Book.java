package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Table("book")
public class Book {
    @Id
    private long pk;

    private String id;
    private String title;
    private String subtitle;
    private String publisher;
    @Column("publisher_date")
    private String publisherDate;
    private String description;
    private int pageCount;
    @Column("small_thumbnail")
    private String smallThumbnail;
    private String thumbnail;
    @Column("list_price_amount")
    private BigDecimal listPriceAmount;
    @Column("list_price_currency")
    private String listPriceCurrency;
    @Column("retail_price_amount")
    private BigDecimal retailPriceAmount;
    @Column("retail_price_currency")
    private String retailPriceCurrency;
    @Column("buy_link")
    private String buyLink;
    @Column("average_rating")
    private int averageRating;
    @Column("ratings_count")
    private int ratingsCount;
    private String language;
    private String kind;

    public void setPk(long pk) {
        this.pk = pk;
    }

    public long getPk() {
        return pk;
    }

    private Set<AuthorRef> authors = new HashSet<>();

    public Set<CategoryRef> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryRef> categories) {
        this.categories = categories;
    }

    private Set<CategoryRef> categories = new HashSet<>();

    public void addAuthor(Author author) {
        this.authors.add(new AuthorRef(author.getId()));
    }

    public void addCategory(Category category) {
        this.categories.add(new CategoryRef(category.getId()));
    }

    @Override
    public String toString() {
        return "Book{" +
                "pk=" + pk +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publisherDate='" + publisherDate + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", smallThumbnail='" + smallThumbnail + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", listPriceAmount=" + listPriceAmount +
                ", listPriceCurrency='" + listPriceCurrency + '\'' +
                ", retailPriceAmount=" + retailPriceAmount +
                ", retailPriceCurrency='" + retailPriceCurrency + '\'' +
                ", buyLink='" + buyLink + '\'' +
                ", averageRating=" + averageRating +
                ", ratingsCount=" + ratingsCount +
                ", language='" + language + '\'' +
                ", kind='" + kind + '\'' +
                ", authors=" + authors +
                '}';
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
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

    public Book(String kind, String id, String title, String subtitle, String publisher, String publisherDate, String description, int pageCount, String smallThumbnail, String thumbnail, BigDecimal listPriceAmount, String listPriceCurrency, BigDecimal retailPriceAmount, String retailPriceCurrency, String buyLink, int averageRating, int ratingsCount, String language) {
        this.kind = kind;
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
    }
    public Book() {

    }
}
