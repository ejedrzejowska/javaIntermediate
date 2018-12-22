package pl.sda.intermediate.products;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Product implements Serializable {
    private static final Long serialVersionUID = 42L;
    private String id;
    private String author;
    private String title;
    private String genre;
    private double price;
    private String publishDate;
    private String description;

    @Override
    public String toString() {
        return "Book: " + title + " author: " + author + " id: " + id;
    }
}
