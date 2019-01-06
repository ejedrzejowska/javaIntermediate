package pl.sda.intermediate.users;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.intermediate.Countries;
import pl.sda.intermediate.DataSourceProvider;

import java.sql.*;
import java.util.Arrays;

@Service
public class UserDAO {
    @Autowired
    private UserContextHolder userContextHolder;

    public boolean checkIfUserExists(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "select email from users where email like ?";
        try {
            connection = DataSourceProvider.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("");
        } finally {
            DataSourceProvider.closeRequest(connection, resultSet, statement);
        }
    }

    private int saveUser(Connection connection, User user) throws SQLException {
        String querySaveUser = "insert into users (firstName, lastName, email, password, birthDate, pesel, phone, prefersEmail)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?)";

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

        return keys.getInt(1);
    }

    private void saveUserAddress(Connection connection, UserAddress userAddress, int userId) throws SQLException {
        String querySaveAddress = "insert into addresses (city, country, zipCode, street, userId) " +
                "values (?, ?, ?, ?, ?)";

        PreparedStatement insertAddress = connection.prepareStatement(querySaveAddress);
        insertAddress.setString(1, userAddress.getCity());
        insertAddress.setString(2, userAddress.getCountry());
        insertAddress.setString(3, userAddress.getZipCode());
        insertAddress.setString(4, userAddress.getStreet());
        insertAddress.setInt(5, userId);
        insertAddress.executeUpdate();

    }

    public void saveUser(User user) {
        Connection connection = null;

        try {
            connection = DataSourceProvider.getConnection();
            int index = saveUser(connection, user);
            saveUserAddress(connection, user.getUserAddress(), index);

//            PreparedStatement insertUser = connection.prepareStatement(querySaveUser, Statement.RETURN_GENERATED_KEYS);
//            insertUser.setString(1, user.getFirstName());
//            insertUser.setString(2, user.getLastName());
//            insertUser.setString(3, user.getEmail());
//            insertUser.setString(4, user.getPasswordHashed());
//            insertUser.setString(5, user.getBirthDate());
//            insertUser.setString(6, user.getPesel());
//            insertUser.setString(7, user.getPhone());
//            insertUser.setBoolean(8, user.isPreferEmails());
//            insertUser.executeUpdate();

//            ResultSet keys = insertUser.getGeneratedKeys();
//            if(keys.next()) {
//                int userId = keys.getInt(1);
//                UserAddress userAddress = user.getUserAddress();
//                PreparedStatement insertAddress = connection.prepareStatement(querySaveAddress);
//                insertAddress.setString(1, userAddress.getCity());
//                insertAddress.setString(2, userAddress.getCountry());
//                insertAddress.setString(3, userAddress.getZipCode());
//                insertAddress.setString(4, userAddress.getStreet());
//                insertAddress.setInt(5, userId);
//                insertAddress.executeUpdate();
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceProvider.closeRequest(connection);
        }

    }

    public User findUserByEmail(String email) {
        Connection connection = null;
        String queryUser = "select * from users where email like ?";
        String queryAddress = "select * from addresses where userId=?";
        try {
            connection = DataSourceProvider.getConnection();
            PreparedStatement userStatement = connection.prepareStatement(queryUser);
            userStatement.setString(1, email);
            ResultSet userSet = userStatement.executeQuery();

            if (userSet.next()) {
                User user = new User();
                user.setFirstName(userSet.getString("firstName"));
                user.setLastName(userSet.getString("lastName"));
                user.setBirthDate(userSet.getString("birthDate"));
                user.setEmail(userSet.getString("email"));
                user.setPasswordHashed(userSet.getString("password"));
                user.setPesel(userSet.getString("pesel"));
                user.setPhone(userSet.getString("phone"));
                user.setPreferEmails(userSet.getBoolean("prefersEmail"));

                PreparedStatement addressStatement = connection.prepareStatement(queryAddress);
                addressStatement.setInt(1, userSet.getInt("id"));
                ResultSet addressSet = addressStatement.executeQuery();
                if (addressSet.next()) {
                    user.setUserAddress(UserAddress.builder()
                            .city(addressSet.getString("city"))
                            .country(addressSet.getString("country"))
                            .zipCode(addressSet.getString("zipCode"))
                            .street(addressSet.getString("street")).build());
                }
                return user;
            } else {
                throw new UserDoesNotExistsException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceProvider.closeRequest(connection);
        }
        return null;
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
    } //TODO do weatherservice? + npe!

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

}
