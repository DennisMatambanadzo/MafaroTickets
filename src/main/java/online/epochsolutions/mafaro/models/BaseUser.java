package online.epochsolutions.mafaro.models;

import jakarta.validation.constraints.Email;
import lombok.Data;
import online.epochsolutions.mafaro.authentication.VerificationToken;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class BaseUser implements UserDetails {
    private String firstName;
    private String lastName;
    @Email
    @UniqueElements
    private String email;
    private String password;


    private Role role;

    private Boolean emailVerified = false;

    public Boolean isEmailVerified() {
        return emailVerified;
    }
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
