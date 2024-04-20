package com.nexeum.authms.security;


import java.security.SignatureException;
import java.util.*;

import com.nexeum.authms.entity.Role;
import com.nexeum.authms.entity.User;
import com.nexeum.authms.model.UserPrincipal;
import com.nexeum.authms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration_in_ms}")
    private long expirationTime;

    @Value("${app.jwt.header_string}")
    private String headerString;

    @Autowired
    private UserRepository userRepository;

    // Generate the token
    public String generateToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date date = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(date.getTime() + expirationTime);
        User user = userRepository.findById(userPrincipal.getId()).orElse(null);
        Set<Role> roles = null;
        if (user != null) {
            roles = user.getRoles();
        }
        List<String> authList = new ArrayList<>();
        assert roles != null;
        for (Role role : roles) {
            authList.add(role.getName());
        }

        Long userId = userPrincipal.getId();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", userPrincipal.getUsername());
        claims.put("email", userPrincipal.getEmail());
        claims.put("authList", authList);

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date()).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

    }

    // validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }

        return false;
    }

    //Get username from the token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String username = (String) claims.get("userName");
        return username;
    }

}