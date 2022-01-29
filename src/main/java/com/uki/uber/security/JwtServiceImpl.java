package com.uki.uber.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtKeyProvider jwtKeyProvider;
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @PostConstruct
    public void initializeKeys() throws Exception{
        privateKey = (RSAPrivateKey) jwtKeyProvider.getPrivateKey();
        publicKey  =  (RSAPublicKey) jwtKeyProvider.getPublicKey();
    }


    public String generateJwtToken(User user, String issuer){
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 2592000000L))
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }


    public DecodedJWT verifyJwtToken(String token){
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    private Algorithm getAlgorithm(){
        return  Algorithm.RSA256(publicKey,privateKey);
    }






}
