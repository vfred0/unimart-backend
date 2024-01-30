package ec.edu.unemi.unimart.services.jwt;

import ec.edu.unemi.unimart.exceptions.MessageException;
import ec.edu.unemi.unimart.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JwtAccessTokenService {

    private final JwtEncoder jwtEncoder;

    public JwtAccessTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateIdToken(Authentication authentication) {
        UserDetails userDetails = Optional
                .of(authentication.getPrincipal())
                .filter(UserDetails.class::isInstance)
                .map(UserDetails.class::cast)
                .orElseThrow(() -> NotFoundException.throwBecauseOf(MessageException.USER_NOT_FOUND));

        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        log.info("Roles: {}", roles);

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(1, ChronoUnit.DAYS);

        JwtClaimsSet claimsSet = JwtClaimsSet
                .builder()
                .claim("scope", roles)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(userDetails.getUsername())
                .build();


        return this.jwtEncoder
                .encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }
}
