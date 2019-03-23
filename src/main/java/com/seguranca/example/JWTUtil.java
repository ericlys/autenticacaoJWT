package com.seguranca.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;

public class JWTUtil {

    private static String key = "SECRET_TOKEN";

    public static final String TOKEN_HEADER = "Authorization";

    public static String create(String subject) {

        return Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static Jws<Claims> decode(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

}
