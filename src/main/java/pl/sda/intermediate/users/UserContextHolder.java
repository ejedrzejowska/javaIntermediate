package pl.sda.intermediate.users;

import org.springframework.stereotype.Service;

@Service
public class UserContextHolder {
    private UserLoggedDTO userLoggedDTO;

    public void logUser(String login){
        userLoggedDTO = new UserLoggedDTO(login);
    }

    public void logoutUser(){
        userLoggedDTO = null;
    }

    public String getUserLoggedIn(){
        return userLoggedDTO == null ? null : userLoggedDTO.getLogin();
    }
}
