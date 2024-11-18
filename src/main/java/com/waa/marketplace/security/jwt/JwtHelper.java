package com.waa.marketplace.security.jwt;

import com.waa.marketplace.utils.Constants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtHelper {

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Constants.ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
                .claim("sub", email)
                .claim("iat", now)
                .claim("exp", expiryDate)
                .claim("type", "access")
                .signWith(Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes()))
                .compact();
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Constants.REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
                .claim("sub", email)
                .claim("iat", now)
                .claim("exp", expiryDate)
                .claim("type", "refresh")
                .signWith(Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes()))
                .compact();
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if (!"access".equals(claims.get("type"))) {
                throw new IllegalArgumentException("Invalid token type");
            }
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                throw new ExpiredJwtException(null, claims, "Token has expired");
            }
            String email = claims.getSubject();
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Invalid token: no email found");
            }
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }


    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Constants.REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        String result = null;
        try {
            result = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
