package pl.sda.intermediate;

import org.junit.jupiter.api.Test;
import pl.sda.intermediate.products.Product;
import pl.sda.intermediate.products.ProductDAO;

import java.util.List;

public class ProductDAOTest {
     @Test
    public void checkParsing(){
         ProductDAO productDAO = ProductDAO.getInstance();
         List<Product> list = productDAO.getProductList();
         System.out.println(list.get(0));
         System.out.println(list.get(1));
         System.out.println(list.get(2));
         System.out.println(list.get(3));
         System.out.println(list.get(4));
         System.out.println(list.size());
     }
}
