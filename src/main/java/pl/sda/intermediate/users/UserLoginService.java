package pl.sda.intermediate.users;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    @Autowired
    private UserDAO userDAO;

    public boolean isLoggedIn(UserLoginDTO userLoginDTO){
        return userDAO.getUserList().stream()       // nie powinnismy dawac gettera na liste
                .filter(u -> u.getEmail().equals(userLoginDTO.getLogin()))
                .findFirst()
                .map(u -> u.getPasswordHashed().equals(DigestUtils.sha512Hex(userLoginDTO.getPassword())))
                .orElse(false);
    }

}
