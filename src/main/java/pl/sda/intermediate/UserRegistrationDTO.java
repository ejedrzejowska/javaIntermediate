package pl.sda.intermediate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String country;
    private String zipCode;
    private String street;
    private String birthDate;
    private String pesel;
    private String phone;
}
