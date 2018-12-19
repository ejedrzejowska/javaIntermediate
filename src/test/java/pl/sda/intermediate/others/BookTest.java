package pl.sda.intermediate.others;

import org.junit.Test;


public class BookTest {
    BookDAO bookDAO = new BookDAO();

    @Test
    public void printBooks(){
        bookDAO.initializeBooks();
        bookDAO.printBooks();
    }
}
