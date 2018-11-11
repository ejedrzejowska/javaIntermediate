package pl.sda.intermediate;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDAO {
    private List<User> userList = new ArrayList<>();

    public boolean checkIfUserExists(String email) {
            return userList.stream().anyMatch(u -> u != null && u.getEmail().equals(email));
    }

    public void saveUser(User user) {
        userList.add(user);
    }
}
