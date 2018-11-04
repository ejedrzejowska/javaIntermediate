package pl.sda.intermediate;

import java.util.Map;

public class OnlyOneController {
    private CategoryService categoryService = new CategoryService();
    private UserRegistrationService userRegistrationService = new UserRegistrationService();
    private UserValidationService userValidationService = new UserValidationService();

    public String categories(String searchedText){
        categoryService.filterCategories(searchedText);
        return "categories";
    }

    public String registerEffect(UserRegistrationDTO userRegistrationDTO){
        Map<String, String> errorMap = userValidationService.validateUser(userRegistrationDTO);
        if(errorMap.isEmpty()){
            try {
                userRegistrationService.registerUser(userRegistrationDTO);
            } catch (UserExistsException e) {
                //fixme;
                return "registerForm";
            }
        } else {
            //Fixme
            return "registerForm";
        }
        return "registerEffect";

    }
}
