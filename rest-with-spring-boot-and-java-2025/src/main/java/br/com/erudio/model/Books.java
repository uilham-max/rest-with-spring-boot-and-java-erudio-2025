package br.com.erudio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @JsonFormat()
    @Column(name = "launch_date", nullable = false)
    private Timestamp launchDate;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Books() {}

    public Books(Long id, String title, String author, Timestamp launchDate, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.launchDate = launchDate;
        this.price = price;
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

    public Timestamp getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Timestamp launchDate) {
        this.launchDate = launchDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return Objects.equals(id, books.id) && Objects.equals(title, books.title) && Objects.equals(author, books.author) && Objects.equals(launchDate, books.launchDate) && Objects.equals(price, books.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, launchDate, price);
    }
}
