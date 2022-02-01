package com.uki.uber.security;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;



@ExtendWith(MockitoExtension.class)
class CustomAuthFilterFactoryTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private LoginAttemptService loginAttemptService;


    @InjectMocks
    private CustomAuthFilterFactory filterFactory;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Test
    @DisplayName("Should return instance of CustomAuthenticationFilter")
    public void shouldReturnInstanceOfCustomAuthenticationFilter(){
        Assertions.assertThat(
                filterFactory.getAuthenticationFilter(authenticationManager))
                .isInstanceOf(CustomAuthenticationFilter.class);
    }

    @Test
    @DisplayName("Should return Instance of CustomAuthorizationFilter")
    public void shouldReturnInstanceOfCustomAuthorizationFilter(){
        Assertions.assertThat(
                filterFactory.getAuthorizationFilter())
                .isInstanceOf(CustomAuthorizationFilter.class);
    }

}