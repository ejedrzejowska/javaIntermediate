package pl.sda.intermediate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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

    @RequestMapping(value = "/")
    public String home (){
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model) {
        model.addAttribute("form", new UserRegistrationDTO());
        model.addAttribute("countries", Countries.values());
        return "registerForm";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String registerEffect(UserRegistrationDTO userRegistrationDTO, Model model){
        Map<String, String> errorMap = userValidationService.validateUser(userRegistrationDTO);
        model.addAttribute("form", userRegistrationDTO);
        model.addAttribute("countries", Countries.values());
        if(errorMap.isEmpty()){
            try {
                userRegistrationService.registerUser(userRegistrationDTO);
            } catch (UserExistsException e) {
                model.addAttribute("userExistsExceptionMessage","Użytkownik już istnieje");
                return "registerForm";
            }
        } else {
            model.addAllAttributes(errorMap);
            return "registerForm";
        }
        return "registerEffect";

    }
}
