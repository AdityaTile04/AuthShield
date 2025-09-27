package com.project.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private static final String SECRET_KEY = "7z9ndi1SJ241eJGvFslb1t84vpbCSGC5";

    public String extractUsername(String token) {
        return null;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey( getSignInKey() )
                .build()
                .parseClaimsJwt( token )
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode( SECRET_KEY );
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
