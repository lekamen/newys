package agency.five.assignment.newys.core.exceptions;

public class UsernameTakenException extends Exception {

    private String username;

    public UsernameTakenException(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
