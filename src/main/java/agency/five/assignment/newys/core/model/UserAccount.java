package agency.five.assignment.newys.core.model;

import agency.five.assignment.newys.core.model.enums.AuthorStatusEnum;
import agency.five.assignment.newys.core.model.enums.StatusEnum;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_account")
public class UserAccount {

    private Long userAccountId;
    private String email;
    private String username;
    private String password;
    private StatusEnum status;
    private AuthorStatusEnum authorStatus;

    private Set<Role> roles = new HashSet<>();

    @Id
    @SequenceGenerator(name = "UserGenerator", sequenceName = "user_account_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserGenerator")
    @Column(name = "user_account_id", nullable = false, unique = true)
    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Column(name = "email", nullable = false, unique = true, length = 254)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "username", nullable = false, unique = true, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "status", nullable = false)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Column(name = "author_status", nullable = false)
    public AuthorStatusEnum getAuthorStatus() {
        return authorStatus;
    }

    public void setAuthorStatus(AuthorStatusEnum authorStatus) {
        this.authorStatus = authorStatus;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_account_role",
            joinColumns = @JoinColumn(name = "user_account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public boolean hasAction(String action) {
        return roles.stream().flatMap(role -> role.getActions().stream()).anyMatch(a -> a.getName().equals(action));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount user = (UserAccount) o;
        return userAccountId.equals(user.userAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAccountId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userAccountId +
                '}';
    }
}
