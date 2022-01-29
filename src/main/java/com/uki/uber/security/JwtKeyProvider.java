package com.uki.uber.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;


@Component
public class JwtKeyProvider {

    @Value("${jks.password}")
    public String JKS_PASSWORD;
    private final String PK_ALIAS = "jwtpk";



    public Key getPrivateKey() throws Exception {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(new FileInputStream("jwt.jks"),JKS_PASSWORD.toCharArray());
        return ks.getKey(PK_ALIAS, JKS_PASSWORD.toCharArray());
    }

    public PublicKey getPublicKey() throws  Exception{
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate certificate = cf.generateCertificate(new FileInputStream("jwt.cer"));
        return certificate.getPublicKey();
    }




}
