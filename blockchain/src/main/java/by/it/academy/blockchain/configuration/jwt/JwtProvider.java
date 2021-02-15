package by.it.academy.blockchain.configuration.jwt;


import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Log
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expirationDateInMs}")
    private long jwtExpirationInMs;
    @Value("${jwt.refreshExpirationDateInMs}")
    private long refreshExpirationDateInMs;

    @PostConstruct
    protected void init () {
        Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    // возвращает токен в виде строки
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        // в зависимости от роли кладем в токен
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("isAdmin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            claims.put("isUser", true);
        }

        return doGenerateToken(claims, userDetails.getUsername());
    }

    //билдим стандартный токен
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

    }

    //билдим рефреш токен в случае окончания срока действия основного
    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

    }

    // валидирует токен
    public boolean validateToken(String token) {
        try {
            log.info("Validating standart token..........");
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.severe("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.severe("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.severe("Malformed jwt");
        } catch (SignatureException sEx) {
            log.severe("Invalid signature");
        } catch (Exception e) {
            log.severe("Invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        List<SimpleGrantedAuthority> roles = null;

        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
        Boolean isUser = claims.get("isUser", Boolean.class);

        if (isAdmin != null && isAdmin) {
            roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        if (isUser != null && isAdmin) {
            roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return roles;

    }
}
