package pl.sda.intermediate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.sda.intermediate.categories.CategoryDTO;
import pl.sda.intermediate.categories.CategoryService;
import pl.sda.intermediate.users.*;
import pl.sda.intermediate.weather.services.WeatherService;

import java.util.*;

@Controller
public class OnlyOneController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserValidationService userValidationService;
    @Autowired
    private UserContextHolder userContextHolder;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping(value="/categories")
    public String categories(@RequestParam(required = false) String searchText, Model model){
        List<CategoryDTO> categoryDTOS = categoryService.filterCategories(searchText);
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
                model.addAttribute("userExistsExceptionMessage",e.getMessage());
                return "registerForm";
            }
        } else {
            model.addAllAttributes(errorMap);
            return "registerForm";
        }
        return "registerEffect";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model){
        model.addAttribute("form", new UserLoginDTO());
        return "login";         //moze byc loginForm, ale trzeba zmienic nazwe htmla - nie ma zwiazku z value w requestMapping
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginEffect(UserLoginDTO userLoginDTO, Model model){
        model.addAttribute("form", userLoginDTO);
        if(userLoginService.isLoggedIn(userLoginDTO)){
            userContextHolder.logUser(userLoginDTO.getLogin());
            return "index";
        } else {
            model.addAttribute("form", new UserLoginDTO());
            model.addAttribute("error", "Błąd logowania");
            return "login";
        }
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutEffect(Model model){
        model.addAttribute("form", new UserLoginDTO());
        userContextHolder.logoutUser();
        return "login";
    }

    @RequestMapping(value="/weather", method = RequestMethod.GET)
    @ResponseBody //wysyła a nie szuka htmla
    public ResponseEntity<String> weather(){
        return ResponseEntity.ok(weatherService.getWeather());
    }
}
