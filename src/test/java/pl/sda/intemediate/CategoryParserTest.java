package pl.sda.intemediate;

import org.junit.jupiter.api.Test;
import pl.sda.intermediate.Category;

public class CategoryParserTest {
    @Test
    public void checkParseOK() {
        Category novel1 = Category.builder()
                .depth(1)
                .id(2)
                .parentId(1)
                .name("Powieści")
                .build();
        Category novel2 = Category.builder()
                .depth(1)
                .id(8)
                .parentId(7)
                .name("Powieści")
                .build();
        Category classTwo = Category.builder()
                .depth(2)
                .id(6)
                .parentId(4)
                .name("Klasa Druga")
                .build();

       // InMemoryCategoryDAO()
    }

}
