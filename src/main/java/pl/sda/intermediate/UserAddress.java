package pl.sda.intermediate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Getter
@Setter
@NoArgsConstructor
public class UserAddress {
    private String city;
    private String country;
    private String zipCode;
    private String street;
}
