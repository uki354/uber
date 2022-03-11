package com.uki.uber.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collectors;
import static com.uki.uber.security.TokenType.ACCESS;




@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtKeyProvider jwtKeyProvider;
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long accessTokenDuration = 600000; // 10min
    private final long refreshTokenDuration = 15778440000L; // 6months

    @PostConstruct
    public void initializeKeys() throws Exception{
        privateKey =  (RSAPrivateKey) jwtKeyProvider.getPrivateKey();
        publicKey  =  (RSAPublicKey) jwtKeyProvider.getPublicKey();
    }


    public String generateJwtToken(User user, String issuer, TokenType type){
        long duration;
        if (type.equals(ACCESS)){
            duration = accessTokenDuration;
        }else{
            duration = refreshTokenDuration;
        }
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + duration))
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }


    public DecodedJWT verifyJwtToken(String token){
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    public RefreshToken generateRefreshTokenAndSave(User user, String issuer){
        String token = generateJwtToken(user, issuer, TokenType.REFRESH);
        RefreshToken refreshToken = RefreshToken.builder().token(token).isValid(true).checksumToken(hashToken(token)).build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public String refreshAccessToken(String token, HttpServletRequest request){
        String tokenChecksum = hashToken(token);
        RefreshToken refreshToken = refreshTokenRepository.findRefreshToken(tokenChecksum);
        try{
            DecodedJWT decodedJWT = verifyJwtToken(refreshToken.getToken());
            Claim roles = decodedJWT.getClaim("roles");
            SimpleGrantedAuthority[] simpleGrantedAuthorities = roles.asArray(SimpleGrantedAuthority.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>(Arrays.asList(simpleGrantedAuthorities));
            User user = new User(decodedJWT.getSubject(),"", authorities);
            return generateJwtToken(user,request.getRequestURL().toString(),ACCESS);
        }catch (TokenExpiredException e){
            refreshTokenRepository.invalidateRefreshToken(tokenChecksum);
            throw new RuntimeException("Token has been expired");
        }
    }

    private String hashToken(String token){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(token.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));

            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }


            return hashText.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Failed to hash refresh token");
        }
    }


    private Algorithm getAlgorithm(){
        return  Algorithm.RSA256(publicKey,privateKey);
    }






}
