package pl.sda.intermediate.others;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDAO {
    private List<Book> bookList = new ArrayList<>();
    private static final String PATH = "c:/temp/biblioteka.txt";


    public void initializeBooks(){
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
            Book book1 = bookList.stream().filter(b -> b.equals(book))
                    .findAny().orElse(null);
            book1.setCount(book1.getCount() + 1);
        } else {
            bookList.add(book);
        }
    }

    public boolean checkIfBookExists(Book book){
        return bookList.stream()
                .anyMatch(b -> b.equals(book));
    }

    public void printBooks(){
        Collections.sort(bookList);
        for (Book book : bookList) {
            System.out.println(book.toString());
//            System.out.println("Książka: " + book.getTitle() + " autor: " + book.getAuthor() + " ilość = " + book.getCount());
        }
    }
}
