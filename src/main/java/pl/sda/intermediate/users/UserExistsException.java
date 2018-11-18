package pl.sda.intermediate;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}
