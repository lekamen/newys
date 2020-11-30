package agency.five.assignment.newys.core.security;

import agency.five.assignment.newys.core.model.Action;
import agency.five.assignment.newys.core.model.Role;
import agency.five.assignment.newys.core.model.UserAccount;
import agency.five.assignment.newys.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }

        return new CustomUserDetails(user, getGrantedAuthorities(user.getRoles()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {

        Set<Action> collection = new HashSet<>();
        roles.forEach(role -> collection.addAll(role.getActions()));

        return collection.stream().map(Action::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

}
