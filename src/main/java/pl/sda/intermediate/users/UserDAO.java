package pl.sda.intermediate.users;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDAO {
    private List<User> userList = new ArrayList<>();
    public static final String USERS_DATA_TXT = "c:/projects/users_data.txt";
    File file = new File(USERS_DATA_TXT);


    {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            userList = (List<User>)ois.readObject();
            User standardUser = new User();
            standardUser.builder().email("user@user.pl").passwordHashed(DigestUtils.sha512Hex("user")).build();
            userList.add(standardUser);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfUserExists(String email) {
        return userList.stream().anyMatch(u -> u != null && u.getEmail().equals(email));
    }

    public void saveUser(User user) {
        userList.add(user);

        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUserList() {
        return userList;
    }
}
