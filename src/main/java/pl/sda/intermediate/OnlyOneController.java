package pl.sda.intermediate;

public class OnlyOneController {
    private CategoryService categoryService = new CategoryService();
    private UserRegistrationService userRegistrationService = new UserRegistrationService();
    private UserValidationService userValidationService = new UserValidationService();

    public String categories(String searchedText){
        categoryService.filterCategories(searchedText);
        return "categories";
    }

    public String registerEffect(UserRegistrationDTO userRegistrationDTO){
        return "register_effect_nazwa_strony";

    }
}
