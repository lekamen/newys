package agency.five.assignment.newys.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GuestProperties {

    public static String GUEST_USERNAME;
    public static String GUEST_PASSWORD;

    public GuestProperties(
        @Value("${user.guest.username}") String guestUsername,
        @Value("${user.guest.password}") String guestPassword
    ) {
        GUEST_USERNAME = guestUsername;
        GUEST_PASSWORD = guestPassword;
    }
}
