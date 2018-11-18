package pl.sda.intermediate;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    private String city;
    private String country;
    private String zipCode;
    private String street;
}
