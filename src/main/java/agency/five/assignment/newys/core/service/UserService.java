package agency.five.assignment.newys.core.service;

import agency.five.assignment.newys.core.exceptions.UserExistsException;
import agency.five.assignment.newys.core.exceptions.UsernameTakenException;
import agency.five.assignment.newys.core.model.UserAccount;
import agency.five.assignment.newys.core.repository.RoleRepository;
import agency.five.assignment.newys.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static agency.five.assignment.newys.core.config.GuestProperties.GUEST_PASSWORD;
import static agency.five.assignment.newys.core.config.GuestProperties.GUEST_USERNAME;
import static agency.five.assignment.newys.core.model.enums.AuthorStatusEnum.*;
import static agency.five.assignment.newys.core.model.enums.StatusEnum.ACTIVE;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UserAccount registerNewUserAccount(UserAccount userAccount) throws UserExistsException, UsernameTakenException {
        if (emailExists(userAccount.getEmail())) {
            throw new UserExistsException(userAccount.getEmail());
        }

        if (userRepository.existsByUsername(userAccount.getUsername())) {
            throw new UsernameTakenException(userAccount.getUsername());
        }

        UserAccount newUser = new UserAccount();
        newUser.setEmail(userAccount.getEmail());
        newUser.setUsername(userAccount.getUsername());
        newUser.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        newUser.setStatus(ACTIVE);
        newUser.setAuthorStatus(NONE);
        newUser.setRoles(Collections.singleton(roleRepository.findReaderRole()));

        return userRepository.save(newUser);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void manuallyLoginUser(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void loginAsGuest() {
        manuallyLoginUser(GUEST_USERNAME, GUEST_PASSWORD);
    }

    @Transactional
    public void userRequestsAuthor(UserAccount user) {
        if (user.getAuthorStatus() != NONE) {
            logger.error("User {} has invalid author status {}", user, user.getAuthorStatus());
            throw new RuntimeException("User has invalid author status!");
        }

        user.setAuthorStatus(REQUESTED);
        userRepository.save(user);
    }

    @Transactional
    public void userAcceptsAuthor(UserAccount user) {
        if (user.getAuthorStatus() != REQUESTED) {
            logger.error("User {} has invalid author status {}", user, user.getAuthorStatus());
            throw new RuntimeException("User has invalid author status!");
        }

        user.setAuthorStatus(ACCEPTED);
        user.getRoles().add(roleRepository.findAuthorRole());
        userRepository.save(user);
    }

    @Transactional
    public void userDeclinesAuthor(UserAccount user) {
        if (user.getAuthorStatus() != REQUESTED) {
            logger.error("User {} has invalid author status {}", user, user.getAuthorStatus());
            throw new RuntimeException("User has invalid author status!");
        }

        user.setAuthorStatus(DECLINED);
        userRepository.save(user);
    }

    public List<UserAccount> findUsersWithRequestedAuthorStatus() {
        return userRepository.findByAuthorStatus(REQUESTED);
    }
}
