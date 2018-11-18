package pl.sda.intermediate.users;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    private static final Long serialVersionUID = 42L;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHashed;
    private UserAddress userAddress;
    private String birthDate;
    private String pesel;
    private String phone;
    private boolean preferEmails;
}
