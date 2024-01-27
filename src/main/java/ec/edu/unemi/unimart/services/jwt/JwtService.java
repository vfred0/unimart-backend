package ec.edu.unemi.unimart.services.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ec.edu.unemi.unimart.api.dtos.TokenDto;
import ec.edu.unemi.unimart.api.dtos.login.LoginResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtService {
    @Value(value = "${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public LoginResponseDto generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("roles", roles)
                .withExpiresAt(new Date(new Date(System.currentTimeMillis()).getTime() + expiration))
                .sign(Algorithm.HMAC256(secret));
        return new LoginResponseDto(token);
    }

    public String getUsernameFromToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public boolean verifyToken(String token) {
        try {
            return Optional.ofNullable(token)
                    .map(t -> JWT.require(Algorithm.HMAC256(secret)).build().verify(t))
                    .map(DecodedJWT::getSubject)
                    .isPresent();
        } catch (JWTVerificationException e) {
            log.error("Error in verify token : {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Token empty");
        }

        return false;
    }

    public String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ") && 3 == header.split("\\.").length) {
            return header.substring("Bearer ".length());
        }
        return "";
    }
}
