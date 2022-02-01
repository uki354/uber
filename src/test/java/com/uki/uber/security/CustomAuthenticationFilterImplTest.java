package com.uki.uber.security;


import org.assertj.core.api.Assertions;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
class CustomAuthenticationFilterImplTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private LoginAttemptService loginAttemptService;
    @Mock
    private AuthenticationManager authenticationManager;



    @InjectMocks
    private CustomAuthenticationFilterImpl authenticationFilter;
    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @Captor
    ArgumentCaptor<UsernamePasswordAuthenticationToken> tokenArgumentCaptor;

    @BeforeEach
    public void init(){
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("Should throw Runtime exception when request remote address  is blocked")
    public void shouldAttemptAuthentication(){
        Mockito.when(loginAttemptService.isBlocked(Mockito.anyString())).thenReturn(true);
        Assertions.assertThatThrownBy(()->
                authenticationFilter.attemptAuthentication(request,response)).
                isInstanceOf(RuntimeException.class);

    }

    @Test
    public void shouldMakeTokenFromRequestBody(){
        Mockito.when(loginAttemptService.isBlocked(Mockito.anyString())).thenReturn(false);
        String json = "{\"username\":\"test\",\"password\":\"123\"}";
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        request.setMethod("POST");
        request.setContent(json.getBytes(StandardCharsets.UTF_8));

        Map<String, String> testCredentials = new HashMap<>();
        testCredentials.put("username", "test");
        testCredentials.put("password", "123");

        UsernamePasswordAuthenticationToken testToken = new UsernamePasswordAuthenticationToken(
                testCredentials.get("username"),
                testCredentials.get("password")
        );
        authenticationFilter.attemptAuthentication(request,response);


        Mockito.verify(authenticationManager, Mockito.atMostOnce()).authenticate(tokenArgumentCaptor.capture());
        Assertions.assertThat(tokenArgumentCaptor.getValue().getPrincipal()).isEqualTo(testToken.getPrincipal());
        Assertions.assertThat(tokenArgumentCaptor.getValue().getCredentials()).isEqualTo(testToken.getCredentials());


    }

    @Test
    @DisplayName("Throws exception due  to invalid json")
    public void shouldThrowErrorDueToInvalidJson(){
        Mockito.when(loginAttemptService.isBlocked(Mockito.anyString())).thenReturn(false);
        String jsonWithMissingBracket = "{\"username\":\"test\"," +
                       "\"password\":\"123\"";
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        request.setMethod("POST");
        request.setContent(jsonWithMissingBracket.getBytes(StandardCharsets.UTF_8));

        Assertions.assertThatThrownBy(() -> authenticationFilter.attemptAuthentication(request,response)).isInstanceOf(Exception.class);


    }




}