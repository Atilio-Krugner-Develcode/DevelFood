package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.role.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "recovery_token_timestamp")
    private String recoveryTokenTimestamp;

    @Column(name = "recovery_token")
    private String recoveryToken;

    @Column(name = "usuarioId")
    private Integer usuarioId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "role", nullable = false)
    private UserRole role;


        public User(String login, String password, String userEmail, UserRole role){
            this.login = login;
            this.password = password;
            this.userEmail = userEmail;
            this.role = role;
        }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));

        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public String getUsername() {
        return login;
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
        return true;
    }

    public String getEmail() {
        return this.userEmail;
    }


}
