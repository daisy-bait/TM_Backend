package co.edu.usco.TM.persistence.entity.administration;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @NotNull
    @Column(name = "usr_username", unique = true)
    private String username;

    @NotNull
    @Column(name = "usr_name")
    private String name;

    @NotNull
    @Column(name = "usr_email")
    private String email;

    @NotNull
    @Column(name = "usr_password")
    private String password;

    @Column(name = "usr_img")
    private String imgURL;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "usr_enabled")
    private boolean enabled;

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName())))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
