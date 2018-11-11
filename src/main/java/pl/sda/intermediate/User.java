package pl.sda.intermediate;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserAddress userAddress;
    private String birthDate;
    private String pesel;
    private String phone;
    private boolean preferEmails;
}
