package pl.sda.intermediate;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDAO {
    private List<User> userList;

    public boolean checkIfUserExists(String email) {
        return userList.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public void saveUser(User user) {
        userList.add(user);
    }
}
