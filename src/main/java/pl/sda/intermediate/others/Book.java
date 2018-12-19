package pl.sda.intermediate.others;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private String title;
    private String author;
    private int count;

    public Book(String author, String title) {
        this.title = title;
        this.author = author;
        this.count = 1;
    }
}
