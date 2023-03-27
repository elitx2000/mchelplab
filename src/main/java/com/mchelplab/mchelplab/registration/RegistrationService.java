package com.mchelplab.mchelplab.registration;

import com.mchelplab.mchelplab.applicationuser.ApplicationUser;
import com.mchelplab.mchelplab.applicationuser.ApplicationUserRole;
import com.mchelplab.mchelplab.applicationuser.ApplicationUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

//Service Layer for Registration Functionality
@Service
@AllArgsConstructor
public class RegistrationService {

    private final ApplicationUserService applicationUserService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request) {
        //Check if the email is valid

        boolean isValid = emailValidator.test(request.getEmail());
        if(!isValid) {
            throw new IllegalStateException("email not valid");
        }
        //Forward the email to ApplicationUserService
        return applicationUserService.signUpUser(
            new ApplicationUser(request.getFirstname(),
                                request.getLastname(),
                                request.getEmail(),
                                request.getPassword(),
                                ApplicationUserRole.USER
                                ));
    }
}
