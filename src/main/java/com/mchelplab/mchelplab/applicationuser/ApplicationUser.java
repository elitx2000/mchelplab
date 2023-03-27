package com.mchelplab.mchelplab.applicationuser;
//@Author: Elijah Araya

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

//Data Access Layer for users of the application

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table
public class ApplicationUser implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence")
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole applicationUserRole;
    private Boolean locked;
    private Boolean enabled;

    //Constructor with no ID input(Automatically generated):
    public ApplicationUser(String firstname,
                           String lastname,
                           String email,
                           String password,
                           ApplicationUserRole applicationUserRole) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.applicationUserRole = applicationUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ApplicationUserRole.name());
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(applicationUserRole.name());
        return Collections.singletonList(authority);
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
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
