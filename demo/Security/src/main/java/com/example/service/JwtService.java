package com.example.service;

import com.example.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(User userDetails,Set<String> authorities) {
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities",authorities)
                .setId(userDetails.getIdUser())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey())
                .compact();
        return token;
    }


    public String extractId(String jwt) {
        Claims body = getJWTBody(jwt);
        return  body.getId();
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public List<String> getAuthorities(String jwt) {

            Claims body = getJWTBody(jwt);
            String username = body.getSubject();

            List<String> authorities = (List<String>) body.get("authorities");
            log.info("userul {} has this roles {}",username,authorities);
            return authorities;

    }

    private Claims getJWTBody(String jwt) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(jwt);

        return claimsJws.getBody();
    }

    public boolean validateTokenAndCheckUserRole(String token) {
            String role_user = "ROLE_USER";
            boolean res = true;
            List<String> roles = getAuthorities(token);
            for(String rol : roles)
                if(!role_user.equals(rol))
                {
                    res =  false;
                    break;
                }
            return res;
    }
}

