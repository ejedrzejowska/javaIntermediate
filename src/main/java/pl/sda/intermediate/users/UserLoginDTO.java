package pl.sda.intermediate.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    private String login;
    private String password;
}
