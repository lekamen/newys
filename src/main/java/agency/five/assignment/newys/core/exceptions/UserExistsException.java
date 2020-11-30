package agency.five.assignment.newys.core.exceptions;

public class UserExistsException extends Exception {

    private String email;

    public UserExistsException(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
