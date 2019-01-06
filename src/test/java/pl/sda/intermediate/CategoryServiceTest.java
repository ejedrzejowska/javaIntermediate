package pl.sda.intermediate;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.sda.intermediate.categories.Category;
import pl.sda.intermediate.categories.CategoryDAO;
import pl.sda.intermediate.categories.CategoryDTO;
import pl.sda.intermediate.categories.CategoryService;
import pl.sda.intermediate.users.UserRegistrationDTO;
import pl.sda.intermediate.users.UserValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {
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
                    .map(m -> m.getState().isOpen())
                    .orElse(null);
            assertFalse(isOpened);
        }

        for (Integer openCategoryID : openCategories) {
            Boolean isOpened = categoryDTOS.stream()
                    .filter(f -> f.getId().equals(openCategoryID))
                    .findFirst()
                    .map(m -> m.getState().isOpen())
                    .orElse(null);
            assertTrue(isOpened);
        }
    }
    @Test
    public void shouldReturnProperErrorMsg(){
        UserValidationService uvs = new UserValidationService();
        UserRegistrationDTO tempUser = new UserRegistrationDTO().builder()
                .birthDate("2001-12-01")
                .city("Lodz")
                .country("POLAND")
                .email("ktos@domena.com")
                .firstName("Jas")
                .lastName("Kowalski")
                .password("Jakies1LepszeHaslo!")
                .pesel("12345678900")
                .phone("123456789")
                .preferEmails(true)
                .street("Sezamkowa")
                .zipCode("12-345")
                .build();
        Map<String, String> errorMap = uvs.validateUser(tempUser);
//        System.out.println(errorMap.get("firstNameValRes"));
//        System.out.println(errorMap.get("lastNameValRes"));
//        System.out.println(errorMap.get("zipCodeValRes"));
//        System.out.println(errorMap.get("streetValRes"));
//        System.out.println(errorMap.get("countryValRes"));
//        System.out.println(errorMap.get("emailValRes"));
//        System.out.println(errorMap.get("phoneValRes"));
//        System.out.println(errorMap.get("birthDateValRes"));
//        System.out.println(errorMap.get("cityValRes"));
//        System.out.println(errorMap.get("streetValRes"));
//        System.out.println(errorMap.get("passwordValRes"));
//        System.out.println(errorMap.get("peselValRes"));
        Assertions.assertEquals(0, errorMap.size());

    }
    @Test
    public void categoryTest(){
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        List<Category> categoryList = new ArrayList<>();
        categoryList = categoryDAO.getCategoryList();
        System.out.println(categoryList.get(30).getName());

    }

}