package com.uki.uber.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {

    String generateJwtToken(User user, String issuer, TokenType type);
    DecodedJWT verifyJwtToken(String token);
    RefreshToken generateRefreshTokenAndSave(User user, String issuer);
    String refreshAccessToken(String token, HttpServletRequest request);
}
