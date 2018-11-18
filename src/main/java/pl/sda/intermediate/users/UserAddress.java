package pl.sda.intermediate.users;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress implements Serializable {
    private static final Long serialVersionUID = 42L;
    private String city;
    private String country;
    private String zipCode;
    private String street;
}
