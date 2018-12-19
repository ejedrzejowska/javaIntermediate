package pl.sda.intermediate.others;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private List<Book> bookList = new ArrayList<>();

    public void initializeBooks(){
        String PATH = "c:/temp/biblioteka.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH))){
            String textLine = bufferedReader.readLine();
            while (textLine != null) {
                parseBook(textLine);
                textLine = bufferedReader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseBook(String fileLine){
        String[] bookInfo = fileLine.split(";");
        addBook(bookInfo[0].trim(), bookInfo[1].trim());
    }

    public void addBook(String author, String title){
        Book book = new Book(author, title);
        if (checkIfBookExists(book)){
            Book book1 = bookList.stream().filter(b -> b.getTitle().equalsIgnoreCase(book.getTitle()) && b.getAuthor().equalsIgnoreCase(book.getAuthor()))
                    .findAny().orElse(null);
            book1.setCount(book1.getCount() + 1);

        } else {
            bookList.add(book);
        }
    }

    public boolean checkIfBookExists(Book book){
        return bookList.stream()
                .anyMatch(b -> (b.getTitle().equalsIgnoreCase(book.getTitle()) && b.getAuthor().equalsIgnoreCase(book.getAuthor())));
    }

    public void printBooks(){
        for (Book book : bookList) {
            System.out.println("Książka: " + book.getTitle() + " autor: " + book.getAuthor() + " ilość = " + book.getCount());
        }
    }
}
