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

    public User findUserByEmail(String email) {
        Connection connection = null;
        String queryUser = "select * from users where email like ?";
        String queryAddress = "select * from addresses where userId=?";
        try {
            connection = getConnection();
            PreparedStatement userStatement = connection.prepareStatement(queryUser);
            userStatement.setString(1, email);
            ResultSet userSet = userStatement.executeQuery();

            if (userSet.next()){
                PreparedStatement addressStatement = connection.prepareStatement(queryAddress);
                addressStatement.setInt(1, userSet.getInt("id"));
                ResultSet addressSet = addressStatement.executeQuery();
                User user = new User();
                user.setFirstName(userSet.getString("firstName"));
                user.setLastName(userSet.getString("lastName"));
                user.setBirthDate(userSet.getString("birthDate"));
                user.setEmail(userSet.getString("email"));
                user.setPasswordHashed(userSet.getString("password"));
                user.setPesel(userSet.getString("pesel"));
                user.setPhone(userSet.getString("phone"));
                user.setPreferEmails(userSet.getBoolean("prefersEmail"));
                if(addressSet.next()) {
                    user.setUserAddress(UserAddress.builder()
                            .city(addressSet.getString("city"))
                            .country(addressSet.getString("country"))
                            .zipCode(addressSet.getString("zipCode"))
                            .street(addressSet.getString("street")).build());
                }
                return user;
//            } else {
//                throw new UserDoesNotExistsException();
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
        return null; // TODO do sprawdzenia
    }

    public boolean verifyPassword(UserLoginDTO userLoginDTO) {
        try {
            return findUserByEmail(userLoginDTO.getLogin()).getPasswordHashed().equals(DigestUtils.sha512Hex(userLoginDTO.getPassword()));
        } catch (UserDoesNotExistsException e) {
            return false;
        }
    }

    public User findUserByContext() {
        return findUserByEmail(userContextHolder.getUserLoggedIn());
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
    
}
