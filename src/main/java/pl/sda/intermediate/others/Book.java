package pl.sda.intermediate.others;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Book implements Comparable<Book> {
    private String title;
    private String author;
    private int count;

    public Book(String author, String title) {
        this.title = title;
        this.author = author;
        this.count = 1;
    }

    public boolean equals(Book book) {
        return this.author.equalsIgnoreCase(book.author) && this.title.equalsIgnoreCase(book.title);
    }

    @Override
    public String toString() {
        return "Książka: " + title +
                " autor: " + author +
                " ilość = " + count;
    }


    @Override
    public int compareTo(Book book) {
        int result = this.author.compareTo(book.author);
        if (result == 0) {
            result = this.title.compareTo(book.title);
        }
        return result;
    }
}
