package ec.edu.unemi.unimart.services.auth;

import ec.edu.unemi.unimart.exceptions.MessageException;
import ec.edu.unemi.unimart.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class JwtAccessTokenService {

    private final JwtEncoder jwtEncoder;

    private static UserDetails getUserDetails(Authentication authentication) {
        return Optional
                .of(authentication.getPrincipal())
                .filter(UserDetails.class::isInstance)
                .map(UserDetails.class::cast)
                .orElseThrow(() -> NotFoundException.throwBecauseOf(MessageException.USER_NOT_FOUND));
    }

    private static JwtClaimsSet getClaims(UserDetails userDetails) {
        int AMOUNT_TO_ADD = 1;
        String SCOPE = "scope";
        return JwtClaimsSet
                .builder()
                .claim(SCOPE, getRoles(userDetails))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(AMOUNT_TO_ADD, ChronoUnit.DAYS))
                .subject(userDetails.getUsername())
                .build();
    }

    private static List<String> getRoles(UserDetails userDetails) {
        return userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public String generateIdToken(Authentication authentication) {
        UserDetails userDetails = getUserDetails(authentication);
        JwtClaimsSet claimsSet = getClaims(userDetails);
        return this.jwtEncoder
                .encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }
}
