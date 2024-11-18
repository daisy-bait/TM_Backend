package co.edu.usco.TM.persistence.entity.user;

import co.edu.usco.TM.persistence.entity.shared.ImageEntity;
import co.edu.usco.TM.persistence.entity.veterinary.Appointment;
import co.edu.usco.TM.persistence.entity.veterinary.Contact;
import co.edu.usco.TM.persistence.entity.veterinary.Pet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements UserDetails, ImageEntity {

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

    @Column(name = "usr_address")
    private String address;

    @Column(name = "usr_zip_code")
    private String zipCode;

    @Column(name = "usr_phone")
    private String phone;

    @Column(name = "usr_image_url")
    private String imgURL;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> userAppointments = new ArrayList<>();

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
