package br.com.erudio.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class BooksDTO {

    private Long id;
    private String title;
    private String author;
    private BigDecimal price;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp launchDate;

    public BooksDTO() {}

    public BooksDTO(Long id, String title, String author, BigDecimal price, Timestamp launchDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.launchDate = launchDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Timestamp launchDate) {
        this.launchDate = launchDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BooksDTO booksDTO = (BooksDTO) o;
        return Objects.equals(id, booksDTO.id) && Objects.equals(title, booksDTO.title) && Objects.equals(author, booksDTO.author) && Objects.equals(price, booksDTO.price) && Objects.equals(launchDate, booksDTO.launchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, price, launchDate);
    }
}
