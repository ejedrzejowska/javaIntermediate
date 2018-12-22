package pl.sda.intermediate.users;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.intermediate.Countries;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDAO {
    @Autowired
    private UserContextHolder userContextHolder;
    private static DataSource dataSource;

    public boolean checkIfUserExists(String email) {
        Connection connection = null;
        String query = "select email from users where email like ?";
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(User user) {
        Connection connection = null;
        int userId = 0;
        String querySaveUser = "insert into users (firstName, lastName, email, password, birthDate, pesel, phone, prefersEmail)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?)";
        String querySaveAddress = "insert into addresses (city, country, zipCode, street, userId) " +
                "values (?, ?, ?, ?, ?)";
        try {
            connection = getConnection();
            PreparedStatement insertUser = connection.prepareStatement(querySaveUser, Statement.RETURN_GENERATED_KEYS);
            insertUser.setString(1, user.getFirstName());
            insertUser.setString(2, user.getLastName());
            insertUser.setString(3, user.getEmail());
            insertUser.setString(4, user.getPasswordHashed());
            insertUser.setString(5, user.getBirthDate());
            insertUser.setString(6, user.getPesel());
            insertUser.setString(7, user.getPhone());
            insertUser.setBoolean(8, user.isPreferEmails());
            insertUser.executeUpdate();

            ResultSet keys = insertUser.getGeneratedKeys();
            keys.next();
            userId = keys.getInt(1);
            UserAddress userAddress = user.getUserAddress();
            PreparedStatement insertAddress = connection.prepareStatement(querySaveAddress);
            insertAddress.setString(1, userAddress.getCity());
            insertAddress.setString(2, userAddress.getCountry());
            insertAddress.setString(3, userAddress.getZipCode());
            insertAddress.setString(4, userAddress.getStreet());
            insertAddress.setInt(5, userId);
            insertAddress.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public User findUserByEmail(UserLoginDTO userLoginDTO) {
        return getUserList().stream()
                .filter(u -> u.getEmail().equals(userLoginDTO.getLogin()))
                .findFirst().orElseThrow(() -> new UserDoesNotExistsException());
    }

    public boolean verifyPassword(UserLoginDTO userLoginDTO) {
        try {
            return findUserByEmail(userLoginDTO).getPasswordHashed().equals(DigestUtils.sha512Hex(userLoginDTO.getPassword()));
        } catch (UserDoesNotExistsException e) {
            return false;
        }
    }

    public User findUserByContext() {
        return User.builder().userAddress(UserAddress.builder().city("Lodz").country("PL").build()).build();
//        return getUserList().stream()
//                .filter(u -> u.getEmail().equals(userContextHolder.getUserLoggedIn()))
//                .findFirst().orElse(User.builder().userAddress(UserAddress.builder().city("Lodz").country("PL").build()).build());
    }

    public String getCity() {
        return findUserByContext().getUserAddress().getCity();
    }

    public String getUnits() {
        String country = findUserByContext().getUserAddress().getCountry();
        return Arrays.stream(Countries.values())
                .filter(c -> c.getSymbol().equals(country))
                .map(e -> e.getUnits())
                .findFirst()
                .orElse("metric");
    }

    public String getLang() {
        String country = findUserByContext().getUserAddress().getCountry();
        return Arrays.stream(Countries.values())
                .filter(c -> c.getSymbol().equals(country))
                .map(e -> e.getLanguage())
                .findFirst()
                .orElse("en");
    }

    private Connection getConnection() throws SQLException {

        if (dataSource == null) {
            String connectionString = "jdbc:mysql://127.0.0.1:3306/intermediate12?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String userName = "intermediate-app-user";
            String password = "sda";

            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(connectionString);
            basicDataSource.setUsername(userName);
            basicDataSource.setPassword(password);
            dataSource = basicDataSource;
        }
        return dataSource.getConnection();
    }

    private List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        String selectUsers = "select * from users";
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(selectUsers);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setPasswordHashed(resultSet.getString("password"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }
}
