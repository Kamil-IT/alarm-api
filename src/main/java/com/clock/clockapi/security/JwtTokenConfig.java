package com.clock.clockapi.security;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.token.SecureRandomFactoryBean;

import java.security.*;
import java.util.Base64;
import java.util.concurrent.*;

@Slf4j
@Configuration
public class JwtTokenConfig {

    public final int EXPIRATION_MIN = 30;
    public static final SignatureAlgorithm ALGORITHM_TO_HASH_TOKEN = SignatureAlgorithm.RS256;

    private Key secretKey;
    private Key publicKey;

    @Value("${jwt.server-location.latitude:50.11552}")
    public final String SERVER_LATITUDE = "50.11552";

    @Value("${jwt.server-location.latitude:8.68417}")
    public final String SERVER_LONGITUDE = "8.68417";

    @Value("${jwt.random-vector:random_vector}")
    private String randomVector;

    public String randomString = "";

    public JwtTokenConfig(JwtKeyReader jwtKeyReader){
//        Set SECRET_KEY and PUBLIC_KEY
        try {
            this.secretKey = jwtKeyReader.getPrivateKey();
            this.publicKey = jwtKeyReader.getPublicKey();
        } catch (Exception e) {
            log.warn("Keys for jwt token cannot be read, use random. Exception: " + e.getMessage());
            KeyPairGenerator keyGenerator = null;
            try {
                keyGenerator = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException ignore) {
                e.printStackTrace();
            }
            keyGenerator.initialize(1024);
            KeyPair kp = keyGenerator.genKeyPair();
            this.secretKey = kp.getPrivate();
            this.publicKey = kp.getPublic();
        }
    }

    private void generateRandomStringInThread(SecureRandom secureRandomJwt) {
        Thread thread = new Thread(() ->
                randomString = Base64.getEncoder().encodeToString(secureRandomJwt.generateSeed(8))
        );
        thread.start();
    }

    @Bean
    public SecureRandom secureRandomJwt() {
        SecureRandomFactoryBean factoryBean = new SecureRandomFactoryBean();
        factoryBean.setAlgorithm("SHA1PRNG");
        SecureRandom secureRandom = null;
        try {
            secureRandom = factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("secureRandomJwt cannot be init");
        }
        generateRandomStringInThread(secureRandom);
        return secureRandom;
    }

    public String getRandomString() {
        int timeout_generator = 200;
        String randomStringGenerated;

        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<Object> task = () -> secureRandomJwt().generateSeed(8);
        Future<Object> future = executor.submit(task);
        try {
            randomStringGenerated = Base64.getEncoder().encodeToString((byte[]) future.get(timeout_generator, TimeUnit.MILLISECONDS));
        } catch (Exception ignored) {
            generateRandomStringInThread(secureRandomJwt());
            randomStringGenerated = randomString;
        } finally {
            future.cancel(true);
        }

        return randomVector + randomStringGenerated;
    }

    public Key getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Key secretKey) {
        this.secretKey = secretKey;
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(Key publicKey) {
        this.publicKey = publicKey;
    }
}
