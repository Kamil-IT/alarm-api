package com.clock.clockapi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

@Component
public class JwtKeyReader {

    @Value("${jwt.public-key.location:src/main/resources/jwt-key/jwt-public-key.txt}")
    private String publicKeyFilename;

    @Value("${jwt.secret-key.location:src/main/resources/jwt-key/jwt-private-key.txt}")
    private String privateKeyFilename;

    public Key getPrivateKey() throws Exception {
        List<String> keyLines = Files.readAllLines(Paths.get(privateKeyFilename));

        StringBuilder keyString = new StringBuilder();
        keyLines.forEach((line) -> keyString.append(line).append("\n"));

        String key = keyString.toString().replace("-----BEGIN RSA PRIVATE KEY-----", "");
        key = key.replace("-----END RSA PRIVATE KEY-----", "");
        key = key.replaceAll("\\s+","");

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(apiKeySecretBytes);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public Key getPublicKey() throws Exception {

        List<String> keyLines = Files.readAllLines(Paths.get(publicKeyFilename));

        StringBuilder keyString = new StringBuilder();
        keyLines.forEach((line) -> keyString.append(line).append("\n"));

        String key = keyString.toString().replace("-----BEGIN PUBLIC KEY-----", "");
        key = key.replace("-----END PUBLIC KEY-----", "");
        key = key.replaceAll("\\s+","");

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        X509EncodedKeySpec KeySpec = new X509EncodedKeySpec(apiKeySecretBytes);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(KeySpec);
    }
}
