package pl.sda.intermediate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
@Controller
public class OnlyOneController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserValidationService userValidationService;

    @RequestMapping(value="/categories")
    public String categories(@RequestParam(required = false) String searchedText, Model model){
        List<CategoryDTO> categoryDTOS = categoryService.filterCategories(searchedText);
        model.addAttribute("catsdata", categoryDTOS); //to zostanie wyslane na front
        return "catspage"; //takiego htmla bedzie szukac nasza aplikacja
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
