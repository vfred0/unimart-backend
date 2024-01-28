package ec.edu.unemi.unimart.services.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ec.edu.unemi.unimart.api.dtos.auth.RegisterRequestDto;
import ec.edu.unemi.unimart.data.entities.User;
import ec.edu.unemi.unimart.data.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtService {
    private static final String USER_CLAIM = "user";
    private static final String NAME_CLAIM = "names";
    private static final String ROLE_CLAIM = "role";
    @Value(value = "${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String createToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return getTokenWith(userDetails.getUsername(), userDetails.getUsername(), userDetails.getUsername(), roles);
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
            log.error("Session empty");
        }

        return false;
    }

    public String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ") && 3 == header.split("\\.").length) {
            return header.substring("Bearer ".length());
        }
        return "";
    }

    private String getTokenWith(String userName, String names, String password, List<String> roles) {
        return JWT.create()
                .withSubject(userName)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim(USER_CLAIM, names)
                .withClaim(NAME_CLAIM, password)
                .withClaim(ROLE_CLAIM, roles)
                .withExpiresAt(new Date(new Date(System.currentTimeMillis()).getTime() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public String createRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(new Date(System.currentTimeMillis()).getTime() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public String createToken(RegisterRequestDto registerRequestDto) {
        List<String> roles = new ArrayList<>();
        for (Role role : registerRequestDto.roles()) {
            roles.add(role.getName());
        }
        return getTokenWith(registerRequestDto.username(), registerRequestDto.names(), registerRequestDto.password(), roles);
    }
}
