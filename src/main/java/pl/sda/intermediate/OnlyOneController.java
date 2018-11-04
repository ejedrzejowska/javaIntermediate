package pl.sda.intermediate;

public class OnlyOneController {
    private CategoryService categoryService = new CategoryService();
    public String categories(String searchedText){
        categoryService.filterCategories(searchedText);
        return "categories";
    }
}
