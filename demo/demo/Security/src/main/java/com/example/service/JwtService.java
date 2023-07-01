package com.example.service;


import com.example.Service.UserService;
import com.example.entity.User;
import com.example.repo.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    private final TokenRepository tokenRepository;
    private final UserService userService;


//TODO interogate Db and clear tokens
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

    private Key getSignInKey() {
        //byte[] keyBytes = Decoders.BASE64.decode(secretKey);
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
}

