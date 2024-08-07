package com.example.Test_task.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("{security.jwt.secret}")
    private String secret;

    public String generateToken(String email){
        Date expertionDate = Date.from(ZonedDateTime.now().plusHours(3).toInstant());

        return JWT.create()
                .withSubject("user details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("test task")
                .withExpiresAt(expertionDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("user details")
                .withIssuer("test task")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

}
