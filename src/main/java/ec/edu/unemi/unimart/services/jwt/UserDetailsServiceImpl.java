package ec.edu.unemi.unimart.services.jwt;

import ec.edu.unemi.unimart.data.daos.IUserRepository;
import ec.edu.unemi.unimart.data.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userAccountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userAccountService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username."));
        org.springframework.security.core.userdetails.User.UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(username);
        return builder.password(user.getPassword()).roles(user.getRoles().toString()).build();
    }
}