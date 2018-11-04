package pl.sda.intermediate;

import org.apache.commons.codec.digest.DigestUtils;

public class UserRegistrationService {
    private UserDAO userDAO = new UserDAO();

    public void registerUser(UserRegistrationDTO userDTO) {
        if (userDAO.checkIfUserExists(userDTO.getEmail())) {
            throw new UserExistsException("User already exists!");
        }
        userDAO.saveUser(rewriteDTOtoUser(userDTO));


    }

    private User rewriteDTOtoUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(DigestUtils.sha512Hex(userRegistrationDTO.getPassword()));
        user.setUserAddress(new UserAddress().builder().city(userRegistrationDTO.getCity()).country(userRegistrationDTO.getCountry())
                .street(userRegistrationDTO.getStreet()).zipCode(userRegistrationDTO.getZipCode()).build());
        //fixme
        return user;
    }
}
