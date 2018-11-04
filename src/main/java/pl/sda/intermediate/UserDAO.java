package pl.sda.intermediate;

import java.util.List;

public class UserDAO {
    private List<User> userList;

    public boolean checkIfUserExists(String email) {
        return userList.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public void saveUser(User user) {
        userList.add(user);
    }
}
