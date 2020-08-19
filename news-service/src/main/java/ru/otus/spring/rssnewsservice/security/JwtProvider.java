package ru.otus.spring.rssnewsservice.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.rssnewsservice.config.JwtPropertiesConfig;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    private JwtPropertiesConfig jwtPropertiesConfig;

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtPropertiesConfig.getSecret())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtPropertiesConfig.getSecret()).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            LOGGER.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            LOGGER.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            LOGGER.error("Malformed jwt");
        } catch (SignatureException sEx) {
            LOGGER.error("Invalid signature");
        } catch (Exception e) {
            LOGGER.error("Token is invalid", e);
        }
        return false;
    }

    public String retrieveLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtPropertiesConfig.getSecret()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}