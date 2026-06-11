package br.com.erudio.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class BookDTO extends RepresentationModel<BookDTO> {

    private Long id;
    private String title;
    private String author;
    private BigDecimal price;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp launchDate;

    public BookDTO() {}

    public BookDTO(Long id, String title, String author, BigDecimal price, Timestamp launchDate) {
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
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(id, bookDTO.id) && Objects.equals(title, bookDTO.title) && Objects.equals(author, bookDTO.author) && Objects.equals(price, bookDTO.price) && Objects.equals(launchDate, bookDTO.launchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, price, launchDate);
    }
}
