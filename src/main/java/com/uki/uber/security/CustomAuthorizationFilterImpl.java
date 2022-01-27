package com.uki.uber.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.uki.uber.config.SecurityConfig.LOGIN;
import static com.uki.uber.config.SecurityConfig.SIGNUP;

@AllArgsConstructor
public class CustomAuthorizationFilterImpl extends  CustomAuthorizationFilter {

    private final JwtService jwtService;
    public static final String BEARER = "Bearer ";



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if( request.getServletPath().equals(LOGIN) ||
            request.getServletPath().equals(SIGNUP)){
              filterChain.doFilter(request,response);
            return;
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER)
            ){
            String token = authorizationHeader.substring(BEARER.length());
            DecodedJWT jwt = jwtService.verifyJwtToken(token);
            String username = jwt.getSubject();
            String[] roles = jwt.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);

        }else response.sendError(HttpStatus.FORBIDDEN.value());

    }


}
