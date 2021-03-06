package pl.sda.intermediate.users;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserValidationService {

    public Map<String, String> validateUser(UserRegistrationDTO dto) {
        Map<String, String> errorMap = new HashMap<>();

        if (StringUtils.isBlank(dto.getFirstName()) || !dto.getFirstName().trim().matches("^[A-Z][a-z]{2,}$")) {
            errorMap.put("firstNameValRes", "Wymagane przynajmniej 3 znaki (pierwsza duża)");
        }
        if (StringUtils.isBlank(dto.getLastName()) || !dto.getLastName().trim().matches("^[A-Z][a-z]{2,}(-[A-Z][a-z]{2,})?$")) {
            errorMap.put("lastNameValRes", "Wymagane przynajmniej 3 znaki (pierwsza duża");
        }
        if (!StringUtils.defaultIfBlank(dto.getZipCode(), "").matches("^[0-9]{2}-[0-9]{3}$")) {
//        if (!Pattern.compile("^[0-9]{2}-[0-9]{3}$").matcher(dto.getZipCode()).matches()) {
            errorMap.put("zipCodeValRes", "Prawidłowy format kodu 12-345");
        }
        if (StringUtils.isBlank(dto.getCountry())) {
            errorMap.put("countryValRes", "Nazwa kraju jest wymagana");
        }
        if (StringUtils.isBlank(dto.getCity())) {
            errorMap.put("cityValRes", "Nazwa miasta jest wymagana");
        }
        if (StringUtils.isBlank(dto.getStreet())) {
            errorMap.put("streetValRes", "Nazwa ulicy jest wymagana");
        }
        if (!StringUtils.defaultIfBlank(dto.getBirthDate(), "")
                .matches("^(19|20)[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[0-1])$")) {
//        if (!Pattern.compile("^(19|20)[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[0-1])$").matcher(dto.getBirthDate()).matches()){
            errorMap.put("birthDateValRes", "Zły format. Data urodzin powinna być podana w formacie RRRR-MM-DD");
        }
        if (!StringUtils.defaultIfBlank(dto.getPesel(), "").matches("^\\d{11}$")) {
//        if (!Pattern.compile("^\\d{11}$").matcher(dto.getPesel()).matches()) {
            errorMap.put("peselValRes", "Zły format. Numer PESEL powinien składać się z 11 cyfr");
        }
        if (!StringUtils.defaultIfBlank(dto.getEmail(), "").matches("^[\\w\\.]+@[\\w]+\\.[\\w]+(\\.[a-z]{2,3})?$")) {
//        if (!Pattern.compile("^[\\w\\.]+@[\\w]+\\.[\\w]+(\\.[a-z]{2,3})?$").matcher(dto.getEmail()).matches()) {
            errorMap.put("emailValRes", "Zły format adresu email");
        }
        if (!StringUtils.defaultIfBlank(dto.getPassword(), "").matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{10,20}$")) {
//        if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{10,20}$").matcher(dto.getPasswordHashed()).matches()) {
            errorMap.put("passwordValRes", "Hasło jest wymagane. Musi zawierać od 10 do 20 znaków (minimum jedna duża, jedna mała litera i cyfra)");
        }
        if (!StringUtils.defaultIfBlank(dto.getPhone(), "").matches("^(\\+\\d{1,3} )?(\\d{3}-?){2}\\d{3}$")) {
//        if (!Pattern.compile("^(\\+\\d{1,3} )?(\\d{3}-?){2}\\d{3}$").matcher(dto.getPhone()).matches()) {
            errorMap.put("phoneValRes", "Zły format. Numer telefonu powinien składać się z 9 cyfr.");
        }
        return errorMap;

    }
}
