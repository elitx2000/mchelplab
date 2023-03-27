package com.mchelplab.mchelplab.applicationuser;
//@Author: Elijah Araya
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//This Service class is used to find users when they log in.
@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE =
            "user with email %s not found";

    private final ApplicationUserRepository applicationUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpUser(ApplicationUser applicationUser) {
        boolean userExists = applicationUserRepository.findByEmail(applicationUser.getEmail()).isPresent();
        if(userExists) {
            throw new IllegalStateException("Email already taken.");
        }
        //If user is unique, encrypt password and store the user into the DB
        String encodedPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());

        applicationUser.setPassword(encodedPassword);
        applicationUserRepository.save(applicationUser); //Put user in the Database
        //TODO: Send Confirmation Token
        return "it works";
    }
}
