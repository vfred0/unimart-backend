package ec.edu.unemi.unimart.services.auth;

import ec.edu.unemi.unimart.data.daos.ISessionRepository;
import ec.edu.unemi.unimart.data.entities.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final ISessionRepository sessionRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        try {
            String token = getToken(request);

            Optional<Session> checkSession = sessionRepository.findByTokenValue(token);
            if (checkSession.isPresent()) {
                Session session = checkSession.get();
                session.setExpired(true);
                session.setRevoked(true);
                sessionRepository.save(session);
                SecurityContextHolder.clearContext();
                log.info("Logout success");
            }
            log.info("Logout error");
        } catch (Exception e) {
            log.error("Fail in the method logout: {}", e.getMessage());
        }

    }

    private String getToken(HttpServletRequest request) throws Exception {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        throw new Exception("No found token");
    }
}
