package com.uki.uber.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class CustomAuthenticationFilterImpl extends CustomAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (loginAttemptService.isBlocked(request.getRemoteAddr())){
            throw new RuntimeException("blocked");
        }
        String username,password;
        try{
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            username = credentials.get("username");
            password = credentials.get("password");

        }catch (IOException e){
            throw new RuntimeException("Error while getting credentials");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

       return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException{

        User user = (User) authResult.getPrincipal();
        String access_token = jwtService.generateJwtToken(user,request.getRequestURL().toString(),TokenType.ACCESS);
        String refresh_token = jwtService.generateRefreshTokenAndSave(user,request.getRequestURL().toString()).getToken();
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        loginAttemptService.loginSucceeded(request.getRemoteAddr());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        loginAttemptService.loginFailed(request.getRemoteAddr());
    }
}
