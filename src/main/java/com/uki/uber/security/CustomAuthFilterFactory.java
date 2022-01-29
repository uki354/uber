package com.uki.uber.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthFilterFactory {

    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;


     public CustomAuthenticationFilter getAuthenticationFilter(AuthenticationManager manager){
         return new CustomAuthenticationFilterImpl(manager, jwtService, loginAttemptService);
     }

     public CustomAuthorizationFilter getAuthorizationFilter( ){
         return new CustomAuthorizationFilterImpl(jwtService);
     }

}
