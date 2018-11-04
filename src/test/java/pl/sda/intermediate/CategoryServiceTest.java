package pl.sda.intermediate;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {
    @Test
    public void shouldOpenParentsOfSearchedCategory(){
        //given - przygotować dane do testowania
        List<Integer> openCategories = Lists.newArrayList(1, 4, 6);
        List<Integer> closedCategories = Lists.newArrayList(2, 3, 7, 8);
        //when
        CategoryService categoryService = new CategoryService();
        List<CategoryDTO> categoryDTOS = categoryService.filterCategories(" Klasa druga ");
        //assertion
        for (Integer closedCategoryID : closedCategories) {
            Boolean isOpened = categoryDTOS.stream()
                    .filter(f -> f.getId().equals(closedCategoryID))
                    .findFirst()
                    .map(m -> m.getCategoryState().isOpen())
                    .orElse(null);
            assertFalse(isOpened);
        }

        for (Integer openCategoryID : openCategories) {
            Boolean isOpened = categoryDTOS.stream()
                    .filter(f -> f.getId().equals(openCategoryID))
                    .findFirst()
                    .map(m -> m.getCategoryState().isOpen())
                    .orElse(null);
            assertTrue(isOpened);
        }
    }

}