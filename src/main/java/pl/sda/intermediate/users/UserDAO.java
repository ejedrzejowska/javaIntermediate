package pl.sda.intermediate.users;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.intermediate.Countries;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDAO {
    @Autowired
    private UserContextHolder userContextHolder;
    private List<User> userList = new ArrayList<>();
    public static final String USERS_DATA_TXT = "c:/projects/users_data.txt";
    public static final String USERS_JSON_TXT = "c:/projects/users_json.txt";
    File file = new File(USERS_DATA_TXT);

    {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            userList = (List<User>) ois.readObject();
//            User standardUser = User.builder().email("user@user.pl").passwordHashed(DigestUtils.sha512Hex("user")).build();
//            userList.add(standardUser);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfUserExists(String email) {
        return userList.stream().anyMatch(u -> u != null && u.getEmail().equals(email));
    }

    public void saveUser(User user) {
        userList.add(user);
        Gson gson = new Gson();

        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(USERS_JSON_TXT);
            gson.toJson(userList, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findUserByEmail(UserLoginDTO userLoginDTO) {
        return userList.stream()
                .filter(u -> u.getEmail().equals(userLoginDTO.getLogin()))
                .findFirst().orElseThrow(() -> new UserDoesNotExistsException());

    }

    public boolean verifyPassword(UserLoginDTO userLoginDTO) {
        try {
            return findUserByEmail(userLoginDTO).getPasswordHashed().equals(DigestUtils.sha512Hex(userLoginDTO.getPassword()));
        } catch (UserDoesNotExistsException e){
            return false;
        }
    }

    public User findUserByContext() {
        return userList.stream()
                .filter(u -> u.getEmail().equals(userContextHolder.getUserLoggedIn()))
                .findFirst().orElseThrow(() -> new UserDoesNotExistsException());

    }

    public String getCity(){
        return findUserByContext().getUserAddress().getCity();
    }

    public String getUnits(){
        String country = findUserByContext().getUserAddress().getCountry();
        return Countries.valueOf(country).getUnits();
    }

    public String getLang(){
        String country = findUserByContext().getUserAddress().getCountry();
        return Countries.valueOf(country).getLanguage();
    }

//    public List<User> getUserList() {
//        return userList;
//    }
}
